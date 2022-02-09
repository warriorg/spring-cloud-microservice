package dev.warriorg.shop.coupon.customer.repository;

import dev.warriorg.shop.coupon.customer.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, String> {

    long countByUserIdAndTemplateId(String userId, String templateId);
}
