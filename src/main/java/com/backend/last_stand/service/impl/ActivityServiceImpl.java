package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ActivityMapper;
import com.backend.last_stand.service.ActivityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description
 * &#064;date 2023/6/19 21:02
 */

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    private static final int SIGN_UP_STAGE = 0;
    private static final int MATERIAL_STAGE = 1;
    private static final int SPEECH_STAGE = 2;
    private static final int SUMMARY_STAGE = 3;
    private static final int END_STAGE = 4;

    @Override
    public ResponseResult getActivityByYear(String year) {
        //转换为json对象
        JSONObject jsonObject = JSON.parseObject(year);
        String year1 = jsonObject.get("year").toString();
        Activity byYear = baseMapper.getByYear(year1);
        if (byYear == null) {
            return new ResponseResult(201, "不存在此年份活动，返回活动为空");
        }

        return new ResponseResult(200, "根据年份返回活动成功", byYear);
    }

    @Override
    public ResponseResult createActivity() {
        LocalDate date = LocalDate.now();
        int year;
        if(date.getMonth().ordinal() >= Month.JULY.ordinal()){
            year = date.getYear();
        }else {
            year = date.getYear() - 1;
        }
        if(getActivityByYear(String.valueOf(year)).getCode() == 200){
            return new ResponseResult(200, "已存在该年的活动");
        }
        var newAct = Activity.builder()
                .activityName(date.getYear() + "学生大使活动")
                .year(String.valueOf(year))
                .begin(date)
                .signupDue(date.plusDays(10))
                .materialDue(date.plusDays(20))
                .speechDue(date.plusDays(30))
                .summaryDue(date.plusDays(40))
                .build();
        int rows = baseMapper.insert(newAct);
        if(rows == 0){
            return new ResponseResult(200, "创建失败");
        }else {
            return new ResponseResult(200, "创建成功");
        }
    }

    @Override
    public ResponseResult updateActivity(@NotNull Activity activity) {
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        wrapper.eq("year", activity.getYear());
        int rows = baseMapper.update(activity, wrapper);
        if(rows == 0){
            return new ResponseResult(200, "更新失败");
        }else {
            return new ResponseResult(200, "更新成功");
        }
    }

    @Override
    public ResponseResult updateDescription(
            String year, int stage, String description) {
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        String descriptionTitle;
        switch (stage) {
            case SIGN_UP_STAGE -> descriptionTitle = "sign_up_description";
            case MATERIAL_STAGE -> descriptionTitle = "material_description";
            case SPEECH_STAGE -> descriptionTitle = "speech_description";
            case SUMMARY_STAGE -> descriptionTitle = "summary_description";
            case END_STAGE -> descriptionTitle = "end_description";
            default -> {
                return null;
            }
        }
        wrapper.set(descriptionTitle, description).eq("year", year);
        int rows = baseMapper.update(null, wrapper);
        if(rows == 0){
            return new ResponseResult(200, "更新失败");
        }else {
            return new ResponseResult(200, "更新成功");
        }
    }

    @Override
    public ResponseResult getAllYears() {
        List<Activity> all = baseMapper.selectList(null);
        ArrayList<String> years = new ArrayList<>();
        for(Activity activity : all){
            years.add(activity.getYear());
        }
        return new ResponseResult(200, "所有年份", years);
    }
}
