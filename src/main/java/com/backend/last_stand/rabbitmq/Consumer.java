package com.backend.last_stand.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.service.SendMaterialService;
import com.backend.last_stand.util.RedisCache;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/27 20:56
 */
@Component
public class Consumer {

    @Autowired
    private SendMaterialService sendMaterialService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisCache redisCache;

    /**
     * 监听material队列,并且一次取出一个消息
     * @param msg
     */
    @RabbitListener(queuesToDeclare = @Queue("material"))
    public void consumer(String msg) {
        JSONObject jsonObject = JSON.parseObject(msg);
        String u = jsonObject.get("uid").toString();
        String m = jsonObject.get("mid").toString();
        //键
        String key = "material:" + m + ":" + u;

        Object cacheObject = redisCache.getCacheObject(key);

        //为空代表没有抢过,否则不处理,这样的目的是为了确保每个用户只抢到一种类型的材料存入记录到redis，后续从redis中取出数据，并操作mysql
        if (cacheObject == null) {
            //存入redis数据库中
            redisCache.setCacheObject(key, m + ":" + u);

            //解析出消息中包含的uid和mid
            Long uid = Long.valueOf(u);
            Long mid = Long.valueOf(m);

            //调用业务层，包括减少sendmaterial库存量并且新增getmaterial字段项
            sendMaterialService.declare(mid, uid);
//        } else {
//            System.out.println("用户重复抢相同材料");
//        }

            //输出一下，控制台看看每条消息具体信息
            System.out.println("消费者成功监听到material队列中的消息，消费者取出消息为" + msg);
        }


    }
}
