package dev.warriorg.shop.coupon.template.controller;

import dev.warriorg.shop.coupon.template.api.dto.CouponTemplateInfo;
import dev.warriorg.shop.coupon.template.api.dto.PagedCouponTemplateInfo;
import dev.warriorg.shop.coupon.template.api.dto.TemplateSearchParams;
import dev.warriorg.shop.coupon.template.service.CouponTemplateService;
import dev.warriorg.shop.infrastructure.jackson.JSONMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/template")
public class CouponTemplateController {

    @Autowired
    private CouponTemplateService couponTemplateService;

    // 创建优惠券
    @PostMapping("/addTemplate")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request) {
        log.info("Create coupon template: data={}", request);
        return couponTemplateService.createTemplate(request);
    }

    @PostMapping("/cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@RequestParam("id") String templateId) {
        log.info("Clone coupon template: data={}", templateId);
        return couponTemplateService.cloneTemplate(templateId);
    }

    // 读取优惠券
    @GetMapping("/getTemplate")
    public CouponTemplateInfo getTemplate(@RequestParam("id") String id){
        log.info("Load template, id={}", id);
        return couponTemplateService.loadTemplateInfo(id);
    }

    // 批量获取
    @GetMapping("/getBatch")
    public Map<String, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<String> ids) {
        log.info("getTemplateInBatch: {}", JSONMapper.toJSONString(ids));
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    // 搜索模板
    @PostMapping("/search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request) {
        log.info("search templates, payload={}", request);
        return couponTemplateService.search(request);
    }

    // 优惠券无效化
    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam("id") String id){
        log.info("Load template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }
}
