package com.promotion.aggregate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.*"})
public class PromotionAggregationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromotionAggregationApplication.class, args);

    }

}

