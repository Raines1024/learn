package com.raines.javaadvanced.redisstarter;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedisDemoController {

    @Resource
    private RedissonClient client;

    @GetMapping("/redissonDemo/{key}")
    public Object getUserInfo(@PathVariable String key) {
        RBucket<String> bucket = client.getBucket("key");
        bucket.set(key);
        return bucket.get();
    }

}
