package com.qh.auroraremark.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson客户端
 *
 * @author qh
 * @date 2022/10/16 16:14:13
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
                .setPassword("redis");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }
}
