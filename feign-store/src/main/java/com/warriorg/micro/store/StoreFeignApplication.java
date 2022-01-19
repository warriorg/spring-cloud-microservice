package com.warriorg.micro.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by warrior on 20/12/2016.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class StoreFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreFeignApplication.class, args);
    }
}