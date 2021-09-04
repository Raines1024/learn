package com.raines.springcloudnacossampleprovider.impl;

import com.raines.nacossampleapi.IHelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String name) {
        return "Hello World:"+name;
    }
}
