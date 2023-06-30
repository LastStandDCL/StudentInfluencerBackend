package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ApplicationTeam;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ApplicationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/21 13:15
 */
@RestController
@RequestMapping("/middleReport")
public class MiddleReportController {

    @Autowired
    private ApplicationTeamService applicationTeamService;

    /**
     * 上传团队中期报告
     * @param file
     * @param year
     * @return
     */
    @PostMapping("/uploadMiddleReport")
    public ResponseResult uploadMiddleReport(@RequestParam("file") MultipartFile file, @RequestParam("year") String year){
        return applicationTeamService.uploadMiddleReport(file, year);
    }

    /**
     * 管理员通过年份和审核状态获得团队中期报告申报列表
     * @param applicationTeam
     * @return
     */
    @PostMapping("/getMiddleReportByYearAndStage")
    public ResponseResult getMiddleReportByYearAndStage(@RequestBody ApplicationTeam applicationTeam){
        return applicationTeamService.getMiddleReportByYearAndStage(applicationTeam);
    }
}
