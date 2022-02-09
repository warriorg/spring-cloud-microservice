package dev.warriorg.shop.coupon.template.converter;

import dev.warriorg.shop.coupon.template.api.dto.rules.TemplateRule;
import dev.warriorg.shop.infrastructure.jackson.JSONMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RuleConverter implements AttributeConverter<TemplateRule, String> {

    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSONMapper.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JSONMapper.getInstance().fromJson(rule, TemplateRule.class);
    }
}
