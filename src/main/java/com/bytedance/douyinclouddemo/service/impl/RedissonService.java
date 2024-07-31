package com.bytedance.douyinclouddemo.service.impl;

import com.bytedance.douyinclouddemo.pojoUtil.BarragePoJo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class RedissonService {

    @Resource
    private RedissonClient redissonClient;

    public void setValue(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public String getValue(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Autowired
    private ObjectMapper objectMapper;

    public void saveBarrageToRedis(String key, BarragePoJo barragePoJo) {
        try {
            String json = objectMapper.writeValueAsString(barragePoJo);
            redissonClient.getBucket(key).set(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public BarragePoJo getBarrageFromRedis(String key) {
        try {
            String json = (String) redissonClient.getBucket(key).get();
            return objectMapper.readValue(json, BarragePoJo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
