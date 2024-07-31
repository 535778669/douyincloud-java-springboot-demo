package com.bytedance.douyinclouddemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
    private String address;
    private int database;
    private String codec;
}
