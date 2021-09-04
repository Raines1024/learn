package com.raines.springcloudnacosconsumer.web;

import com.raines.nacossampleapi.IHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Dubbo服务的访问
 */
@RestController
public class HelloController {

    @Reference
    private IHelloService helloService;

    @GetMapping("/say")
    public String sayHello(){
        return helloService.sayHello("raines");
    }

}
