package dev.warriorg.shop.coupon.customer.convertor;

import dev.warriorg.shop.coupon.customer.entity.Coupon;
import dev.warriorg.shop.coupon.template.api.dto.CouponInfo;

public class CouponConverter {

    public static CouponInfo convertToCoupon(Coupon coupon) {

        return CouponInfo.builder()
                .uid(coupon.getUid())
                .status(coupon.getStatus().getCode())
                .templateId(coupon.getTemplateId())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .template(coupon.getTemplateInfo())
                .build();
    }
}