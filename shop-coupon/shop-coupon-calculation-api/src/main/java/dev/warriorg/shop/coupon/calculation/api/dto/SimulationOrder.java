package dev.warriorg.shop.coupon.calculation.api.dto;

import dev.warriorg.shop.coupon.template.api.dto.CouponInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulationOrder {
    @NotEmpty
    private List<Product> products;

    @NotEmpty
    private List<String> couponIDs;

    private List<CouponInfo> couponInfos;

    @NotEmpty
    private String userId;
}
