package com.warriorg.micro.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by warrior on 19/12/2016.
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class NetflixApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NetflixApplication.class, args);
    }
}
