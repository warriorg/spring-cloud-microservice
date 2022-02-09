package dev.warriorg.shop.coupon.customer.service.impl;

import com.google.common.collect.Lists;
import dev.warriorg.shop.coupon.calculation.api.dto.ShoppingCart;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationOrder;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationResponse;
import dev.warriorg.shop.coupon.customer.api.dto.RequestCoupon;
import dev.warriorg.shop.coupon.customer.api.dto.SearchCoupon;
import dev.warriorg.shop.coupon.customer.api.enums.CouponStatus;
import dev.warriorg.shop.coupon.customer.convertor.CouponConverter;
import dev.warriorg.shop.coupon.customer.entity.Coupon;
import dev.warriorg.shop.coupon.customer.repository.CouponRepository;
import dev.warriorg.shop.coupon.customer.service.CouponCustomerService;
import dev.warriorg.shop.coupon.template.api.dto.CouponInfo;
import dev.warriorg.shop.coupon.template.api.dto.CouponTemplateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CouponCustomerServiceImpl implements CouponCustomerService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTemplateService templateService;

    @Autowired
    private CouponCalculationService calculationService;


    @Override
    public SimulationResponse simulateOrderPrice(SimulationOrder order) {
        List<CouponInfo> couponInfos = Lists.newArrayList();
        // 挨个循环，把优惠券信息加载出来
        // 高并发场景下不能这么一个个循环，更好的做法是批量查询
        // 而且券模板一旦创建不会改内容，所以在创建端做数据异构放到缓存里，使用端从缓存捞template信息
        for (String couponId : order.getCouponIDs()) {
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .status(CouponStatus.AVAILABLE)
                    .build();
            example.setUid(couponId);
            Optional<Coupon> couponOptional = couponRepository.findAll(Example.of(example))
                    .stream()
                    .findFirst();
            // 加载优惠券模板信息
            if (couponOptional.isPresent()) {
                Coupon coupon = couponOptional.get();
                CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
                couponInfo.setTemplate(templateService.loadTemplateInfo(coupon.getTemplateId()));
                couponInfos.add(couponInfo);
            }
        }
        order.setCouponInfos(couponInfos);

        // 调用接口试算服务
        return calculationService.simulateOrder(order);
    }

    /**
     * 用户查询优惠券的接口
     */
    @Override
    public List<CouponInfo> findCoupon(SearchCoupon request) {
        // 在真正的生产环境，这个接口需要做分页查询，并且查询条件要封装成一个类
        Coupon example = Coupon.builder()
                .userId(request.getUserId())
                .status(CouponStatus.convert(request.getCouponStatus()))
                .shopId(request.getShopId())
                .build();

        // 这里你可以尝试实现分页查询
        List<Coupon> coupons = couponRepository.findAll(Example.of(example));
        if (coupons.isEmpty()) {
            return Lists.newArrayList();
        }

        List<String> templateIds = coupons.stream()
                .map(Coupon::getTemplateId)
                .collect(Collectors.toList());
        Map<String, CouponTemplateInfo> templateMap = templateService.getTemplateInfoMap(templateIds);
        coupons.stream().forEach(e -> e.setTemplateInfo(templateMap.get(e.getTemplateId())));

        return coupons.stream()
                .map(CouponConverter::convertToCoupon)
                .collect(Collectors.toList());
    }

    /**
     * 用户领取优惠券
     */
    @Override
    public Coupon requestCoupon(RequestCoupon request) {
        CouponTemplateInfo templateInfo = templateService.loadTemplateInfo(request.getCouponTemplateId());

        // 模板不存在则报错
        if (templateInfo == null) {
            log.error("invalid template id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("Invalid template id");
        }

        // 模板不能过期
        long now = Calendar.getInstance().getTimeInMillis();
        Long expTime = templateInfo.getRule().getDeadline();
        if (expTime != null && now >= expTime || BooleanUtils.isFalse(templateInfo.getAvailable())) {
            log.error("template is not available id={}", request.getCouponTemplateId());
            throw new IllegalArgumentException("template is unavailable");
        }

        // 用户领券数量超过上限
        Long count = couponRepository.countByUserIdAndTemplateId(request.getUserId(), request.getCouponTemplateId());
        if (count >= templateInfo.getRule().getLimitation()) {
            log.error("exceeds maximum number");
            throw new IllegalArgumentException("exceeds maximum number");
        }

        Coupon coupon = Coupon.builder()
                .templateId(request.getCouponTemplateId())
                .userId(request.getUserId())
                .shopId(templateInfo.getShopId())
                .status(CouponStatus.AVAILABLE)
                .build();
        couponRepository.save(coupon);
        return coupon;
    }

    @Override
    @Transactional
    public ShoppingCart placeOrder(ShoppingCart order) {
        if (CollectionUtils.isEmpty(order.getProducts())) {
            log.error("invalid check out request, order={}", order);
            throw new IllegalArgumentException("cart if empty");
        }

        Coupon coupon = null;
        if (order.getCouponId() != null) {
            // 如果有优惠券，验证是否可用，并且是当前客户的
            Coupon example = Coupon.builder()
                    .userId(order.getUserId())
                    .status(CouponStatus.AVAILABLE)
                    .build();
            example.setUid(order.getCouponId());
            coupon = couponRepository.findAll(Example.of(example))
                    .stream()
                    .findFirst()
                    // 如果找不到券，就抛出异常
                    .orElseThrow(() -> new RuntimeException("Coupon not found"));

            CouponInfo couponInfo = CouponConverter.convertToCoupon(coupon);
            couponInfo.setTemplate(templateService.loadTemplateInfo(coupon.getTemplateId()));
            order.setCouponInfos(Lists.newArrayList(couponInfo));
        }

        // order清算
        ShoppingCart checkoutInfo = calculationService.calculateOrderPrice(order);

        if (coupon != null) {
            // 如果优惠券没有被结算掉，而用户传递了优惠券，报错提示该订单满足不了优惠条件
            if (CollectionUtils.isEmpty(checkoutInfo.getCouponInfos())) {
                log.error("cannot apply coupon to order, couponId={}", coupon.getUid());
                throw new IllegalArgumentException("coupon is not applicable to this order");
            }

            log.info("update coupon status to used, couponId={}", coupon.getUid());
            coupon.setStatus(CouponStatus.USED);
            couponRepository.save(coupon);
        }

        return checkoutInfo;
    }

    // 逻辑删除优惠券
    @Override
    public void deleteCoupon(String userId, String couponId) {
        Coupon example = Coupon.builder()
                .userId(userId)
                .status(CouponStatus.AVAILABLE)
                .build();
        example.setUid(couponId);
        Coupon coupon = couponRepository.findAll(Example.of(example))
                .stream()
                .findFirst()
                // 如果找不到券，就抛出异常
                .orElseThrow(() -> new RuntimeException("Could not find available coupon"));

        coupon.setStatus(CouponStatus.INACTIVE);
        couponRepository.save(coupon);
    }

}