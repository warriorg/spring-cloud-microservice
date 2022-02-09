package dev.warriorg.shop.coupon.template.repository;

import dev.warriorg.shop.coupon.template.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, String> {

    // 根据Shop ID查询出所有券模板
    List<CouponTemplate> findAllByShopId(Long shopId);

    // IN查询 + 分页支持的语法
    Page<CouponTemplate> findAllByIdIn(List<String> Id, Pageable page);

    // 根据shop ID + 可用状态查询店铺有多少券模板
    Integer countByShopIdAndAvailable(String shopId, Boolean available);

    // 将优惠券设置为不可用
    @Modifying
    @Query("update CouponTemplate c set c.available = 0 where c.uid = :id")
    int makeCouponUnavailable(@Param("id") String id);
}
