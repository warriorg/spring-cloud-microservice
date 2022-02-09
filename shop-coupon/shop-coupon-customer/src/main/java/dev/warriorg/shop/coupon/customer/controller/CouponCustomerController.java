package dev.warriorg.shop.coupon.customer.controller;

import dev.warriorg.shop.coupon.calculation.api.dto.ShoppingCart;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationOrder;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationResponse;
import dev.warriorg.shop.coupon.customer.api.dto.RequestCoupon;
import dev.warriorg.shop.coupon.customer.api.dto.SearchCoupon;
import dev.warriorg.shop.coupon.customer.entity.Coupon;
import dev.warriorg.shop.coupon.customer.service.CouponCustomerService;
import dev.warriorg.shop.coupon.template.api.dto.CouponInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("coupon-customer")
public class CouponCustomerController {
    @Autowired
    private CouponCustomerService customerService;

    @PostMapping("requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        return customerService.requestCoupon(request);
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") String userId,
                             @RequestParam("couponId") String couponId) {
        customerService.deleteCoupon(userId, couponId);
    }

    // 用户模拟计算每个优惠券的优惠价格
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    // ResponseEntity - 指定返回状态码 - 可以作为一个课后思考题
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


    // 实现的时候最好封装一个search object类
    @PostMapping("findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }
}
