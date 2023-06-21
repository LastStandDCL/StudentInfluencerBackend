package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.impl.KeyValueServiceImpl;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Key value controller.
 *
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023 /6/19 10:34
 */
@Slf4j
@RestController
@RequestMapping("/key")
public class KeyValueController {

    @Autowired
    private KeyValueServiceImpl keyValueService;

    /**
     * 格式  "key" : "value"
     *
     * @param key the key
     * @return response result
     */
    @PostMapping("/insertkey")
    public ResponseResult insertkey(@RequestBody String key) {
        System.out.println(key);
        return keyValueService.insertkey(key);
    }
}
