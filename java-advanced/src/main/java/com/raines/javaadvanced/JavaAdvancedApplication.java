package com.raines.javaadvanced;

import com.raines.javaadvanced.autoimport.EnableAutoImport;
import com.raines.javaadvanced.autoimport.FirstClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
@EnableAutoImport
public class JavaAdvancedApplication {

    public static void main(String[] args) {
//        SpringApplication.run(JavaAdvancedApplication.class, args);
        ConfigurableApplicationContext ca = SpringApplication.run(JavaAdvancedApplication.class, args);
        FirstClass fc = ca.getBean(FirstClass.class);
    }

}
