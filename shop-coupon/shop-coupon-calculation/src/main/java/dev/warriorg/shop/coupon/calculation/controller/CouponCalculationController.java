package dev.warriorg.shop.coupon.calculation.controller;

import dev.warriorg.shop.coupon.calculation.api.dto.ShoppingCart;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationOrder;
import dev.warriorg.shop.coupon.calculation.api.dto.SimulationResponse;
import dev.warriorg.shop.coupon.calculation.service.CouponCalculationService;
import dev.warriorg.shop.infrastructure.jackson.JSONMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("calculator")
public class CouponCalculationController {
    @Autowired
    private CouponCalculationService calculationService;

    // 优惠券结算
    @PostMapping("/checkout")
    @ResponseBody
    public ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart settlement) {
        log.info("do calculation: {}", JSONMapper.toJSONString(settlement));
        return calculationService.calculateOrderPrice(settlement);
    }

    // 优惠券列表挨个试算
    // 给客户提示每个可用券的优惠额度，帮助挑选
    @PostMapping("/simulate")
    @ResponseBody
    public SimulationResponse simulate(@RequestBody SimulationOrder simulator) {
        log.info("do simulation: {}", JSONMapper.toJSONString(simulator));
        return calculationService.simulateOrder(simulator);
    }
}
