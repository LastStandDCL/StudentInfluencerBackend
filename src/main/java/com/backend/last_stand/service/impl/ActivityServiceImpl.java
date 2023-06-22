package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.Menu;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ActivityMapper;
import com.backend.last_stand.mapper.MenuMapper;
import com.backend.last_stand.service.ActivityService;
import com.backend.last_stand.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/19 21:02
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public ResponseResult getActivityByYear(String year) {
        JSONObject jsonObject = JSON.parseObject(year);
        String year1 = jsonObject.get("year").toString();


        return null;
    }
}
