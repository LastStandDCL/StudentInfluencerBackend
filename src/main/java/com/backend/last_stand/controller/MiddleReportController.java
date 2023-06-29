package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ApplicationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
