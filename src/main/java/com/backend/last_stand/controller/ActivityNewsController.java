package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ActivityNews;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ActivityNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/21 12:24
 */
@RestController
@RequestMapping("/activityNews")
public class ActivityNewsController {

    private final ActivityNewsService activityNewsService;

    @Autowired
    public ActivityNewsController(ActivityNewsService activityNewsService) {
        this.activityNewsService = activityNewsService;
    }

    /**
     * 传入队伍id
     * @param activityNews
     * @return
     */
    @PostMapping("/getActivityNewsFromTeam")
    public ResponseResult getActivityNewsFromTeam(@RequestBody ActivityNews activityNews) {
        return activityNewsService.getActivityNewsFromTeam(activityNews);
    }

    /**
     * 传入用户id 和新闻稿相关信息,添加一个新闻稿件
     * @param activityNews
     * @return
     */
    @PostMapping("/insertActivityNews")
    public ResponseResult insertActivityNews(@RequestBody ActivityNews activityNews) {
        return activityNewsService.insertActivityNews(activityNews);
    }
}
