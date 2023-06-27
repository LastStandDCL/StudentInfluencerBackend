package com.backend.last_stand.controller;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ActivityService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * The type Activity controller.
 *
 * @author chenhong
 * @version 1.0
 * &#064;description
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

    @GetMapping("/createActivity")
    public ResponseResult createActivity() {
        return activityService.createActivity();
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
     * 根据年份更新活动信息
     *
     * @param activity 更改后的activity，后端会把非空字段都更新掉
     * @return 更新是否成功
     */
    @PostMapping("/updateActivityByYear")
    public ResponseResult updateActivity(@RequestBody Activity activity) {
        return activityService.updateActivity(activity);
    }

    /**
     * 根据年份更新活动信息
     *
     * @param params 传入 year， stage， description
     * @return 更新是否成功
     */
    @PostMapping("/updateDescriptionByYear")
    public ResponseResult updateDescription(
            @RequestBody @NotNull
            Map<String,String> params) {
        return activityService.updateDescription(
                params.get("year"),
                Integer.parseInt(params.get("stage")),
                params.get("description"));
    }
}
