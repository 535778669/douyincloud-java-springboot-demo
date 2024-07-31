package com.bytedance.douyinclouddemo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
              .setAddress(redissonProperties.getAddress())
              .setDatabase(redissonProperties.getDatabase());

        try {
            // 动态加载编码器类
            Class<?> codecClass = Class.forName(redissonProperties.getCodec());
            config.setCodec((org.redisson.client.codec.Codec) codecClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
            // 处理编码器加载失败的情况
        }

        return Redisson.create(config);
    }
}
