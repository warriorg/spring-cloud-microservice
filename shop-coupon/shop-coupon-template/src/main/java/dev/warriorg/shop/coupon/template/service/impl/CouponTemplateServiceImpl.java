package dev.warriorg.shop.coupon.template.service.impl;

import dev.warriorg.shop.coupon.template.api.dto.CouponTemplateInfo;
import dev.warriorg.shop.coupon.template.api.dto.PagedCouponTemplateInfo;
import dev.warriorg.shop.coupon.template.api.dto.TemplateSearchParams;
import dev.warriorg.shop.coupon.template.api.enums.CouponType;
import dev.warriorg.shop.coupon.template.converter.CouponTemplateConverter;
import dev.warriorg.shop.coupon.template.entity.CouponTemplate;
import dev.warriorg.shop.coupon.template.repository.CouponTemplateRepository;
import dev.warriorg.shop.coupon.template.service.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 优惠券模板类相关操作
 */
@Slf4j
@Service
public class CouponTemplateServiceImpl implements CouponTemplateService {

    @Autowired
    private CouponTemplateRepository couponTemplateRepository;

    // 克隆优惠券
    @Override
    public CouponTemplateInfo cloneTemplate(String templateId) {
        log.info("cloning template id {}", templateId);
        CouponTemplate source = couponTemplateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("invalid template ID"));

        CouponTemplate target = new CouponTemplate();
        BeanUtils.copyProperties(source, target);

        target.setAvailable(true);
        target.setUid(null);

        couponTemplateRepository.save(target);
        return CouponTemplateConverter.convertToTemplateInfo(target);
    }

    /**
     * 创建优惠券模板
     */
    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        // 单个门店最多可以创建100张优惠券模板
        if (request.getShopId() != null) {
            Integer count = couponTemplateRepository.countByShopIdAndAvailable(request.getShopId(), true);
            if (count >= 100) {
                log.error("the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException("exceeded the maximum of coupon templates that you can create");
            }
        }

        // 创建优惠券
        CouponTemplate template = CouponTemplate.builder()
                .name(request.getName())
                .description(request.getDesc())
                .category(CouponType.convert(request.getType()))
                .available(true)
                .shopId(request.getShopId())
                .rule(request.getRule())
                .build();
        template = couponTemplateRepository.save(template);

        return CouponTemplateConverter.convertToTemplateInfo(template);
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();

        Pageable page = PageRequest.of(request.getPage(), request.getPageSize());
        couponTemplateRepository.findAll(Example.of(example), page);

        Page<CouponTemplate> result = couponTemplateRepository.findAll(Example.of(example), page);
        List<CouponTemplateInfo> couponTemplateInfos = result.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());

        PagedCouponTemplateInfo response = PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(request.getPage())
                .total(result.getTotalElements())
                .build();

        return response;
    }

    public List<CouponTemplateInfo> searchTemplate(CouponTemplateInfo request) {
        CouponTemplate example = CouponTemplate.builder()
                .shopId(request.getShopId())
                .category(CouponType.convert(request.getType()))
                .available(request.getAvailable())
                .name(request.getName())
                .build();
        // 可以用下面的方式做分页查询
//        Pageable page = PageRequest.of(0, 100);
//        couponTemplateRepository.findAll(Example.of(example), page);
        List<CouponTemplate> result = couponTemplateRepository.findAll(Example.of(example));
        return result.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toList());
    }

    /**
     * 通过ID查询优惠券模板
     */
    @Override
    public CouponTemplateInfo loadTemplateInfo(String id) {
        Optional<CouponTemplate> template = couponTemplateRepository.findById(id);
        return template.isPresent() ? CouponTemplateConverter.convertToTemplateInfo(template.get()) : null;
    }

    // 将券无效化
    @Override
    @Transactional
    public void deleteTemplate(String id) {
        int rows = couponTemplateRepository.makeCouponUnavailable(id);
        if (rows == 0) {
            throw new IllegalArgumentException("Template Not Found: " + id);
        }
    }

    /**
     * 批量读取模板
     */
    @Override
    public Map<String, CouponTemplateInfo> getTemplateInfoMap(Collection<String> ids) {

        List<CouponTemplate> templates = couponTemplateRepository.findAllById(ids);

        return templates.stream()
                .map(CouponTemplateConverter::convertToTemplateInfo)
                .collect(Collectors.toMap(CouponTemplateInfo::getUid, Function.identity()));
    }

}
