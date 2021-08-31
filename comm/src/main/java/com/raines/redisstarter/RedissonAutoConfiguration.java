package com.raines.redisstarter;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissionProperties.class)
@Slf4j
public class RedissonAutoConfiguration {

    @Bean
    RedissonClient redissonClient(RedissionProperties redissionProperties){
        log.info("redisson starter bean initialization");
        Config config = new Config();
        String prefix = "redis://";
        if (redissionProperties.isSsl()){
            prefix="rediss://";
        }
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(prefix+redissionProperties.getHost()+":"+redissionProperties.getPort())
                .setConnectTimeout(redissionProperties.getTimeout());
        if (!StringUtils.isEmpty(redissionProperties.getPassword())){
            singleServerConfig.setPassword(redissionProperties.getPassword());
        }
        return Redisson.create(config);
    }

}
