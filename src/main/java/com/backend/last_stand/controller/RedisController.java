package com.backend.last_stand.controller;

import com.backend.last_stand.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type Redis controller.
 *
 * @author chenhong
 * @version 1.0
 * &#064;description 测试redis用的
 * &#064;date 2023 /5/24 21:49
 */
@RestController
public class RedisController {

    private final RedisCache redisCache;

    @Autowired
    public RedisController(RedisCache redisCache) {
        this.redisCache = redisCache;
    }
    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     */
    @PreAuthorize("hasAuthority('system:test:list')")
    @GetMapping("/redis/get/{key}")
    public Object get(@PathVariable("key") String key) {
        return redisCache.getCacheObject(key);
    }


}
