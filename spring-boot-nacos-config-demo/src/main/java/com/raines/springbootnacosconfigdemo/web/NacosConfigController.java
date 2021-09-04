package com.raines.springbootnacosconfigdemo.web;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NacosPropertySource注解：用于加载dataId为example的数据源，autoRefreshed表示开启自动更新
 */
@NacosPropertySource(dataId = "example",autoRefreshed = true)
@RestController
public class NacosConfigController {

    //@NacosValue：设置属性的值，info表示key，Local Hello World代表默认值。也就是说如果key不存在，则使用默认值，这是一种高可用的策略，考虑配置中心不可用情况下如何保证服务的可用性
    @NacosValue(value = "${info:Local Hello World}",autoRefreshed = true)
    private String info;

    @GetMapping("/config")
    public String get(){
        return info;
    }

}
