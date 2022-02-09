package dev.warriorg.shop.coupon.template.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建优惠券模板
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateSearchParams {

    private String uid;

    private String name;

    private String type;

    private String shopId;

    private Boolean available;

    private int page;

    private int pageSize;

}