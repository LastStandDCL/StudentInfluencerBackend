package com.backend.last_stand.service;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface ActivityService extends IService<Activity> {
    ResponseResult getActivityByYear(String year);

    ResponseResult createActivity(Activity activity);

    ResponseResult nextStage(HashMap<String, String> mp);

    ResponseResult getStageByAId(HashMap<String, String> mp);
}
