package com.raines.springcloudsentinelsample.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //通过@SentinelResource配置限流保护资源
    @SentinelResource(value = "hello",blockHandler = "blockHandlerHello")
    @GetMapping("/say")
    public String hello(){
        return "hello,raines";
    }

    public String blockHandlerHello(BlockException e){
        return "被限流了";
    }

}
