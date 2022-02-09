package dev.warriorg.shop.coupon.template.converter;

import dev.warriorg.shop.coupon.template.api.dto.CouponTemplateInfo;
import dev.warriorg.shop.coupon.template.entity.CouponTemplate;

public class CouponTemplateConverter {
    public static CouponTemplateInfo convertToTemplateInfo(CouponTemplate template) {

        return CouponTemplateInfo.builder()
                .uid(template.getUid())
                .name(template.getName())
                .desc(template.getDescription())
                .type(template.getCategory().getCode())
                .shopId(template.getShopId())
                .available(template.getAvailable())
                .rule(template.getRule())
                .build();
    }
}
