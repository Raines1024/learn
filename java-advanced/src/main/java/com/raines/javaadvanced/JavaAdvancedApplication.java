package com.raines.javaadvanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class JavaAdvancedApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAdvancedApplication.class, args);
    }

}
