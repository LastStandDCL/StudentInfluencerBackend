package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.SendMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产者
 */
@RestController
@Slf4j
@RequestMapping("/send")
public class RabbitMqController {
 
    @Autowired
    private SendMaterialService sendMaterialService;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 生产者  存入申报材料信息到消息队列缓存
     * @param msg
     * @return
     */
    @PostMapping("/sendMsg")
    public ResponseResult sendMsg(@RequestBody String msg){
        //发送消息到消息队列中
        rabbitTemplate.convertAndSend("", "material", msg);
        return new ResponseResult<>(200, "发送到消息队列中");
    }


 
}