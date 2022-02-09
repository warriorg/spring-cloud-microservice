package dev.warriorg.shop.coupon.calculation.template.impl;

import dev.warriorg.shop.coupon.calculation.template.AbstractRuleTemplate;
import dev.warriorg.shop.coupon.calculation.template.RuleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// PAU客户专用优惠计算逻辑，每笔订单享受996暴击
@Slf4j
@Component
public class AntiPauTemplate extends AbstractRuleTemplate implements RuleTemplate {

    @Override
    protected Long calculateNewPrice(Long orderTotalAmount, Long shopTotalAmount, Long quota) {
        return orderTotalAmount * 996;
    }

    // 设置订单最小支付金额=996
    @Override
    protected long minCost() {
        return 996;
    }
}
