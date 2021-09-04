package com.raines.nacossampleprovider.impl;

import com.raines.nacossampleapi.IHelloService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("nacos sample provider");
        return "Hello World:"+name;
    }

}
