package me.warriorg.licenses.client;

import me.warriorg.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author: warriorg
 * @date: 2018-12-30
 */
@Component
public class OrganizationRestTemplateClient {

    /***
     * 使用带有Ribbon功能的Spring RestTemplate调用服务
     * @LoadBalanced 定义RestTemplate bean 方法。在Application
     */

    @Autowired
    RestTemplate restTemplate;

    public Organization getOrganization(String organizationId){
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://organizationservice/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
