package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Stage;
import com.backend.last_stand.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/19 20:58
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 根据年份返回活动
     * @param year
     * @return
     */
    @PostMapping("/getActivityByYear")
    public ResponseResult getActivityByYear(@RequestBody String year) {
        return null;
    }

    /**
     * 根据活动 id 返回活动的所有阶段
     * @param id
     * @return
     */
    @PostMapping("/getStageByAId")
    public ResponseResult getStageByAId(@RequestBody Long id) {
        return null;
    }

    /**
     * 当前活动新加阶段Stage
     * @param stage
     * @return
     */
    @PostMapping("/addStage")
    public ResponseResult addStage(@RequestBody Stage stage) {
        return null;
    }

    @PostMapping("/getStageByName")
    public ResponseResult getStageByName(@RequestBody String name) {
        return null;
    }




}
