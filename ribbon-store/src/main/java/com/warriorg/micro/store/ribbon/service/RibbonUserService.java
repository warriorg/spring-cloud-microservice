package com.warriorg.micro.store.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by warrior on 20/12/2016.
 */
@Service
public class RibbonUserService {
    @Autowired
    private RestTemplate restTemplate;

    public String hello() {
        // http://服务提供者的serviceId/url
        return this.restTemplate.getForObject("http://user-provider/hello", String.class);
    }

    public ServiceInstance info() {
        return this.restTemplate.getForObject("http://user-provider/instance-info", ServiceInstance.class);
    }
}