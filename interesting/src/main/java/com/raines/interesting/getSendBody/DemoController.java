package com.raines.interesting.getSendBody;

import org.springframework.web.bind.annotation.*;

/**
 * 测试Get请求在Body中发送数据
 */
@RestController
public class DemoController {

    @RequestMapping(value = "/getSendBody", method = RequestMethod.GET)
    public String getRequest(@RequestParam("id") String id, @RequestBody Person body) {
        return id+"|"+body.toString();
    }

}
