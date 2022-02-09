package dev.warriorg.shop.coupon.template.entity;

import dev.warriorg.shop.coupon.template.api.dto.rules.TemplateRule;
import dev.warriorg.shop.coupon.template.api.enums.CouponType;
import dev.warriorg.shop.coupon.template.converter.CouponTypeConverter;
import dev.warriorg.shop.coupon.template.converter.RuleConverter;
import dev.warriorg.shop.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 优惠券模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon_template")
public class CouponTemplate extends BaseEntity {

    // 状态是否可用
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Column(name = "name", nullable = false)
    private String name;

    // 适用门店-如果为空，则为全店满减券
    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "description", nullable = false)
    private String description;

    // 优惠券类型
    @Column(name = "type", nullable = false)
    @Convert(converter = CouponTypeConverter.class)
    private CouponType category;


    // 优惠券核算规则，平铺成JSON字段
    @Column(name = "rule", nullable = false)
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;

}