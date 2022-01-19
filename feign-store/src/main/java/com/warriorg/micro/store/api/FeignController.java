package com.warriorg.micro.store.api;

import com.warriorg.micro.store.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by warrior on 20/12/2016.
 */
@RestController
public class FeignController {
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("hello")
    public String hello() {
        return "hello, NB周";
    }

    @GetMapping("remote/hello")
    public String remoteHello() {
        return this.userFeignClient.hello();
    }

    @GetMapping("feign/instance-info")
    public ServiceInstance findByIdFeign() {
        ServiceInstance instance = this.userFeignClient.showInfo();
        return instance;
    }
}