package com.backend.last_stand.controller;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * The type Activity controller.
 *
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023 /6/19 20:58
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/createActivity")
    public ResponseResult createActivity(@RequestBody Activity Activity) {
        return activityService.createActivity(Activity);
    }

    /**
     * 根据年份返回活动
     *
     * @param year the year
     * @return activity by year
     */
    @PostMapping("/getActivityByYear")
    public ResponseResult getActivityByYear(@RequestBody String year) {
        return activityService.getActivityByYear(year);
    }

    /**
     * 根据活动 id 返回活动的所有阶段
     *
     * @param  mp the id and year
     * @return stage by a id
     */
    @PostMapping("/getStageByAId")
    public ResponseResult getStageByAId(@RequestBody HashMap<String, String> mp) {
        return activityService.getStageByAId(mp);
    }

    /**
     * Gets stage by name.
     *
     * @param mp the name
     * @return the stage by name
     */
    @PostMapping("/getStageByName")
    public ResponseResult getStageByName(@RequestBody HashMap<String, String> mp) {
        return null;
    }






}
