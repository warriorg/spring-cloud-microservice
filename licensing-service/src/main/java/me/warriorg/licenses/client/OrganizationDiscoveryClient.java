package me.warriorg.licenses.client;

import me.warriorg.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author: warriorg
 * @date: 2018-12-30
 */
@Component
public class OrganizationDiscoveryClient {

    /***
     * 在实际运用中，只有在服务需要查询Ribbon以了解那些服务和服务实例已经通过它注册时，才应该直接使用DiscoveryClient。存在的问题
     * 1. 没有利用Ribbon的客户端负载均衡
     * 2. 开发人员做了太多的工作
     */


    /***
     * 用于Ribbon的交互类
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        /***
         * 使用getInstances方法，传入关键字，检索通过Eureka注册的所有组织服务实例
         */
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");

        if (instances.size()==0) return null;
        String serviceUri = String.format("%s/v1/organizations/%s",instances.get(0).getUri().toString(), organizationId);

        ResponseEntity< Organization > restExchange =
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
