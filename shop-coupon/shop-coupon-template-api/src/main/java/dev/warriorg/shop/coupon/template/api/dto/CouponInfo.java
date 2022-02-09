package dev.warriorg.shop.coupon.template.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装优惠券信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponInfo {

    private String uid;

    private String templateId;

    private String userId;

    private String shopId;

    private Integer status;

    private CouponTemplateInfo template;

}
