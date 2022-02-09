package dev.warriorg.shop.coupon.customer.entity;

import dev.warriorg.shop.coupon.customer.api.enums.CouponStatus;
import dev.warriorg.shop.coupon.customer.convertor.CouponStatusConverter;
import dev.warriorg.shop.coupon.template.api.dto.CouponTemplateInfo;
import dev.warriorg.shop.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
public class Coupon extends BaseEntity {

    // 对应的模板ID - 不使用one to one映射
    // 不推荐使用级联查询的原因是为了防止滥用而导致的DB性能问题
    @Column(name = "template_id", nullable = false)
    private String templateId;

    // 所有者的用户ID
    @Column(name = "user_id", nullable = false)
    private String userId;

    // 冗余一个shop id方便查找
    @Column(name = "shop_id")
    private String shopId;

    // 优惠券的使用/未使用状态
    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    // 被Transient标记的属性是不会被持久化的
    @Transient
    private CouponTemplateInfo templateInfo;
}
