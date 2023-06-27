package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ActivityMapper;
import com.backend.last_stand.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Override
    public ResponseResult getActivityByYear(String year) {
        //转换为json对象
        JSONObject jsonObject = JSON.parseObject(year);
        String year1 = jsonObject.get("year").toString();
        Activity byYear = baseMapper.getByYear(year1);
        if (byYear == null) {
            return new ResponseResult<>(201, "不存在此年份活动，返回活动为空");
        }

        return new ResponseResult<>(200, "根据年份返回活动成功", byYear);
    }

    @Override
    public ResponseResult createActivity(Activity Activity) {
        Activity.setBegin(LocalDate.now());
        String year = Activity.getYear();
        Activity byYear = baseMapper.getByYear(year);
        if (byYear != null) {
            return new ResponseResult<>(206, "创建活动失败，请检查是否创建已有年份活动", byYear);
        }
        int insert = baseMapper.insert(Activity);
        if (insert != 1) {
            throw new RuntimeException("插入失败，已经存在此年份的活动");
        }
        return new ResponseResult<>(206, "创建活动成功", Activity);
    }

    @Override
    public ResponseResult getStageByAId(HashMap<String, String> mp) {
        String s = mp.get("id");
        Long id = Long.valueOf(s);
        Activity Activity = baseMapper.selectById(id);
        if (Activity == null) {
            return new ResponseResult<>(202, "不存在此活动");
        }
        return new ResponseResult<>(200, "根据Id查询活动完成", Activity);
    }


}
