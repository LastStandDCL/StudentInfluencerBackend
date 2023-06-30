package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.mapper.ApplicationTeamMapper;
import com.backend.last_stand.mapper.PendingRecordMapper;
import com.backend.last_stand.service.ApplicationTeamService;
import com.backend.last_stand.service.PendingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
