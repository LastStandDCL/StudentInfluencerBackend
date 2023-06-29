package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.ApplicationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/21 13:16
 */
@RestController
@RequestMapping("/teamReport")
public class TeamReportController {

    @Autowired
    private ApplicationTeamService applicationTeamService;

    /**
     * 上传团队总结报告
     * @param file
     * @param year
     * @return
     */
    @PostMapping("/uploadTeamReport")
    public ResponseResult uploadTeamReport(@RequestParam("file") MultipartFile file, @RequestParam("year") String year){
        return applicationTeamService.uploadTeamReport(file, year);
    }

}
