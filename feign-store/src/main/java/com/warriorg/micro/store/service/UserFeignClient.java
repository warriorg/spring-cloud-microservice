package com.warriorg.micro.store.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by warrior on 20/12/2016.
 */
@FeignClient(name = "user-provider")
public interface UserFeignClient {

    @RequestMapping("instance-info")
    ServiceInstance showInfo();

    @RequestMapping("hello")
    String hello();
}
