package com.backend.last_stand.service;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ActivityNews;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface ActivityNewsService  extends IService<ActivityNews> {
    ResponseResult getActivityNewsFromTeam(ActivityNews activityNews);

    ResponseResult insertActivityNews(ActivityNews activityNews);
}
