package com.backend.last_stand.service;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService extends IService<Activity> {
    ResponseResult getActivityByYear(String year);

    ResponseResult createActivity();

    ResponseResult updateActivity(Activity Activity);

    ResponseResult updateDescription(String year, int stage, String description);

    ResponseResult getAllYears();
}
