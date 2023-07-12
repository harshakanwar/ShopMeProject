package com.shopme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan({"com.shopme.common.entity", "com.shopme.admin.user"})
@SpringBootApplication
public class ShopMeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMeBackendApplication.class, args);
    }

}
