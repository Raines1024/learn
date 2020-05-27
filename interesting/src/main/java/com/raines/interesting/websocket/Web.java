package com.raines.interesting.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Web {

    @GetMapping(value = "/a")
    public String login(){
        return "a";
    }
}
