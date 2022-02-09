package dev.warriorg.shop.coupon.calculation.template;

import dev.warriorg.shop.coupon.calculation.api.dto.ShoppingCart;

public interface RuleTemplate {
    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
