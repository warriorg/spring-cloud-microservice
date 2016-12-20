package com.warriorg.micro.store.ribbon.api;

import com.warriorg.micro.store.ribbon.service.RibbonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by warrior on 20/12/2016.
 */
@RestController
public class RibbonController {
    @Autowired
    private RibbonUserService ribbonService;

    @GetMapping("/ribbon/info")
    public ServiceInstance info() {
        return this.ribbonService.info();
    }

    @GetMapping("/ribbon/hello")
    public String hello() {
        return this.ribbonService.hello();
    }
}
