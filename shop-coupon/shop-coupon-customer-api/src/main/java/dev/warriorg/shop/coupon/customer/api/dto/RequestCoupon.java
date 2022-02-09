package dev.warriorg.shop.coupon.customer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCoupon {

    // 用户领券
    @NotNull
    private String userId;

    // 券模板ID
    @NotNull
    private String couponTemplateId;
}
