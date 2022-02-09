package dev.warriorg.shop.coupon.calculation.service;

import dev.warriorg.shop.coupon.calculation.api.dto.ShoppingCart;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationOrder;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface CouponCalculationService {
    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}
