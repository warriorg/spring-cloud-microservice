package dev.warriorg.shop.coupon.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"dev.warriorg.shop"})
public class CouponCalculationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponCalculationApplication.class, args);
    }
}
