package dev.warriorg.shop.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author warriorg
 * @date 2022/1/20 11:31
 */
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("dev.warriorg.shop")
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
