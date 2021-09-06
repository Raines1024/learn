package com.raines.springcloudnacosconfigdemo;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @NacosValue(value = "${info:Local Hello World}",autoRefreshed = true)
    private String info;

    @GetMapping("/config")
    public String get(){
        return info;
    }
}
