package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.GetMaterial;
import com.backend.last_stand.entity.SendMaterial;
import com.backend.last_stand.mapper.GetMaterialMapper;
import com.backend.last_stand.mapper.SendMaterialMapper;
import com.backend.last_stand.service.GetMaterialService;
import com.backend.last_stand.service.SendMaterialService;
import com.backend.last_stand.util.RedisCache;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/27 21:57
 */
@Service
public class SendMaterialServiceImpl  extends ServiceImpl<SendMaterialMapper, SendMaterial> implements SendMaterialService {

    @Autowired
    private SendMaterialMapper sendMaterialMapper;

    @Autowired
    private GetMaterialMapper getMaterialMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void declare(Long mid, Long uid) {
        //从数据库中查找
        Object cacheObject = redisCache.getCacheObject("material:" + mid + ":" + uid);

        Integer remainInteger = sendMaterialMapper.getRemainInteger(mid);
        if (remainInteger >= 1) {
            //减少库存
            sendMaterialMapper.declare(mid);
            GetMaterial getMaterial = new GetMaterial();
            getMaterial.setUid(uid);
            getMaterial.setGetDate(new Date());
            getMaterial.setMid(mid);
            //插入新的项
            getMaterialMapper.insert(getMaterial);
        }
    }

    @Override
    public void declareAll() {
        //获取前缀为material的键
        Set<String> materials = redisTemplate.keys("material*");
        //遍历键
        for (String s : materials) {
            String[] split = s.split(":");
            String m = split[1];
            String u = split[2];

            Long mid = Long.parseLong(m);
            Long uid = Long.parseLong(u);
            //获取库存
            Integer remainInteger = sendMaterialMapper.getRemainInteger(mid);
            //如果库存大于一，代表库存还能取,如果目前库存不够了，那就不操作mysql
            if (remainInteger >= 1) {
                sendMaterialMapper.declare(mid);
                GetMaterial getMaterial = new GetMaterial();
                getMaterial.setUid(uid);
                getMaterial.setGetDate(new Date());
                getMaterial.setMid(mid);

                getMaterialMapper.insert(getMaterial);
            }
            //处理完之后删除
            redisCache.deleteObject(s);
        }

    }
}
