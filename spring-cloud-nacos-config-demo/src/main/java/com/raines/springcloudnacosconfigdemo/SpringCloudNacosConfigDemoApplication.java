package com.raines.springcloudnacosconfigdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类中动态读取配置中心的数据
 */
@SpringBootApplication
public class SpringCloudNacosConfigDemoApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringCloudNacosConfigDemoApplication.class, args);
        while (true){
            String info = context.getEnvironment().getProperty("info");
            System.out.println(info);
            Thread.sleep(2000);
        }
    }

}
