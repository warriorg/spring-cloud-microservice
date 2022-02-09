package dev.warriorg.shop.coupon.template.api.dto;

import dev.warriorg.shop.coupon.template.api.dto.rules.TemplateRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 创建优惠券模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponTemplateInfo {

    private String uid;

    @NotNull
    private String name;

    // 优惠券描述
    @NotNull
    private String desc;

    // 优惠券类型
    @NotNull
    private String type;

    // 适用门店 - 若无则为全店通用券
    private String shopId;

    /** 优惠券规则 */
    @NotNull
    private TemplateRule rule;

    private Boolean available;

}
