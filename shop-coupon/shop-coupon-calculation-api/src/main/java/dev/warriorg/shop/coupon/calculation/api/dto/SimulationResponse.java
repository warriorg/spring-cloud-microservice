package dev.warriorg.shop.coupon.calculation.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class SimulationResponse {
    // 最省钱的coupon
    private String bestCouponId;

    // 每一个coupon对应的order价格
    private Map<String, Long> couponToOrderPrice = new HashMap<>();
}
