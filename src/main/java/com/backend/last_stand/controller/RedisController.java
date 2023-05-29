package com.backend.last_stand.controller;

import com.backend.last_stand.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenhong
 * @version 1.0
 * @description 测试redis用的
 * @date 2023/5/24 21:49
 */
@RestController
public class RedisController {
    @Autowired
    private RedisCache redisCache;

    @PreAuthorize("hasAuthority('system:test:list')")
    @GetMapping("/redis/get/{key}")
    public Object get(@PathVariable("key") String key) {
        return redisCache.getCacheObject(key);
    }


}
