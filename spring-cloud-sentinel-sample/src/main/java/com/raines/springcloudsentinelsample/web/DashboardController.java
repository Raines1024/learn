package com.raines.springcloudsentinelsample.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @GetMapping("/dash")
    public String dash(){
        return "Hello Dash";
    }

}
