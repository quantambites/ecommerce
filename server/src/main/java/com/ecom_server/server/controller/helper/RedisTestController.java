package com.ecom_server.server.controller.helper;

import com.ecom_server.server.service.helper.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisTestController {

    @Autowired
    private RedisTestService redisTestService;

    @GetMapping("/ping")
    public ResponseEntity<?> pingRedis() {
        String response = redisTestService.testRedisPing();
        return ResponseEntity.ok("Redis says: " + response);
    }
}
