package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/19 10:36
 */
@Service
public class KeyValueServiceImpl {

    private final RedisCache redisCache;

    @Autowired
    public KeyValueServiceImpl(RedisCache redisCache){
        this.redisCache = redisCache;
    }


    public ResponseResult insertkey(String key) {
        JSONObject jsonObject = JSON.parseObject(key);
        String keys = jsonObject.get("key").toString();
        String value = jsonObject.get("value").toString();
        redisCache.setCacheObject(keys, value);
        return new ResponseResult(200, "插入键值对成功");
    }
}
