package com.ecom_server.server.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisTestService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisTestService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String testRedisPing() {
        redisTemplate.opsForValue().set("ping", "pong");
        return redisTemplate.opsForValue().get("ping");
    }
}
