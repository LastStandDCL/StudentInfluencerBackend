package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ApplicationTeamMapper;
import com.backend.last_stand.mapper.PendingRecordMapper;
import com.backend.last_stand.service.ApplicationTeamService;
import com.backend.last_stand.service.PendingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bowen
 * @since 2023-06-29
 */
@RestController
@RequestMapping("/examine")
public class PendingRecordController {



    @Autowired
    private PendingRecordService pendingRecordService;

    @Autowired
    private ApplicationTeamService applicationTeamService;

    /**
     * 管理员对于团队报告进行审核
     * @param info
     * @return
     */
    @PostMapping("/examineReportOfTeam")
    public ResponseResult examineReportOfTeam (@RequestBody HashMap<String, Object> info){
        return applicationTeamService.examineReport(info);
    }

    /**
     * 管理员下载团队报告
     * @param fileName
     * @return
     */
    @GetMapping("/downloadTeamReport/{fileName}")
    public ResponseEntity<Object> downloadTeamReport(@PathVariable(name = "fileName") String fileName) throws FileNotFoundException {
        return applicationTeamService.downloadTeamReport(fileName);
    }
}
