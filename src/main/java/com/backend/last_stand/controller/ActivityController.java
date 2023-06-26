package com.backend.last_stand.controller;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Stage;
import com.backend.last_stand.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Activity controller.
 *
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023 /6/19 20:58
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @PostMapping("/createActivity")
    public ResponseResult createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
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
     * @param id the id
     * @return stage by a id
     */
    @PostMapping("/getStageByAId")
    public ResponseResult getStageByAId(@RequestBody Long id) {
        return null;
    }

    /**
     * 当前活动新加阶段Stage
     *
     * @param stage the stage
     * @return response result
     */
    @PostMapping("/addStage")
    public ResponseResult addStage(@RequestBody Stage stage) {
        return null;
    }

    /**
     * Gets stage by name.
     *
     * @param name the name
     * @return the stage by name
     */
    @PostMapping("/getStageByName")
    public ResponseResult getStageByName(@RequestBody String name) {
        return null;
    }




}
