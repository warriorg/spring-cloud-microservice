package dev.warriorg.shop.coupon.customer.service;

import dev.warriorg.shop.coupon.calculation.api.dto.ShoppingCart;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationOrder;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationResponse;
import dev.warriorg.shop.coupon.customer.api.dto.RequestCoupon;
import dev.warriorg.shop.coupon.customer.api.dto.SearchCoupon;
import dev.warriorg.shop.coupon.customer.entity.Coupon;
import dev.warriorg.shop.coupon.template.api.dto.CouponInfo;

import java.util.List;

// 用户对接服务
public interface CouponCustomerService {

    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 优惠券金额试算
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    void deleteCoupon(String userId, String couponId);

    // 查询用户优惠券
    List<CouponInfo> findCoupon(SearchCoupon request);
}
