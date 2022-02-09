package dev.warriorg.shop.coupon.template;

import com.netflix.discovery.shared.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"dev.warriorg.shop"})
public class CouponTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
