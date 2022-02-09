package dev.warriorg.shop.coupon.template.service;

import dev.warriorg.shop.coupon.template.api.dto.CouponTemplateInfo;
import dev.warriorg.shop.coupon.template.api.dto.PagedCouponTemplateInfo;
import dev.warriorg.shop.coupon.template.api.dto.TemplateSearchParams;

import java.util.Collection;
import java.util.Map;

public interface CouponTemplateService {
    // 创建优惠券模板
    CouponTemplateInfo createTemplate(CouponTemplateInfo request);

    CouponTemplateInfo cloneTemplate(String templateId);

    // 模板查询（分页）
    PagedCouponTemplateInfo search(TemplateSearchParams request);

    // 通过模板ID查询优惠券模板
    CouponTemplateInfo loadTemplateInfo(String id);

    // 让优惠券模板无效
    void deleteTemplate(String id);

    // 批量查询
    // Map是模板ID，key是模板详情
    Map<String, CouponTemplateInfo> getTemplateInfoMap(Collection<String> ids);
}
