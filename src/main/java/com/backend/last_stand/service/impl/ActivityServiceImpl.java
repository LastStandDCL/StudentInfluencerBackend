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

import java.util.Date;
import java.util.HashMap;

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
        //转换为json对象
        JSONObject jsonObject = JSON.parseObject(year);
        String year1 = jsonObject.get("year").toString();
        Activity byYear = activityMapper.getByYear(year1);
        if (byYear == null) {
            return new ResponseResult<>(201, "不存在此年份活动，返回活动为空");
        }

        return new ResponseResult<>(200, "根据年份返回活动成功", byYear);
    }

    @Override
    public ResponseResult createActivity(Activity activity) {
        activity.setBegin(new Date());
        String year = activity.getYear();
        Activity byYear = activityMapper.getByYear(year);
        if (byYear != null) {
            return new ResponseResult<>(206, "创建活动失败，请检查是否创建已有年份活动", byYear);
        }
        int insert = activityMapper.insert(activity);
        if (insert != 1) {
            throw new RuntimeException("插入失败，已经存在此年份的活动");
        }
        return new ResponseResult<>(206, "创建活动成功", activity);
    }


    @Override
    public ResponseResult nextStage(HashMap<String, String> mp) {
        String stage = mp.get("stage");
        String year = mp.get("year");
        Activity byYear = activityMapper.getByYear(year);
        if (byYear == null) {
            return new ResponseResult<>(202, "输入年份有误，此年份活动不存在");
        }
        //设置活动阶段
        byYear.setCurrentStage(Integer.valueOf(stage));
        activityMapper.updateById(byYear);
        return new ResponseResult<>(200, "活动阶段设置为指定阶段", byYear);
    }

    @Override
    public ResponseResult getStageByAId(HashMap<String, String> mp) {
        String s = mp.get("id");
        Long id = Long.valueOf(s);
        Activity activity = activityMapper.selectById(id);
        if (activity == null) {
            return new ResponseResult<>(202, "不存在此活动");
        }
        return new ResponseResult<>(200, "根据Id查询活动完成", activity);
    }


}
