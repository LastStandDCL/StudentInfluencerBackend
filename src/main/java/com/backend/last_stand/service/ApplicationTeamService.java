package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationTeam;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 团队报告申请服务实现类接口
 *
 * @author wangziyang
 * @since 2023-06-28
 */
public interface ApplicationTeamService extends IService<ApplicationTeam> {

    ResponseResult uploadTeamReport(MultipartFile file, String year);

    ResponseResult uploadMiddleReport(MultipartFile file, String year);

    ResponseResult examineReport(HashMap<String, Object> info);

    ResponseResult getTeamReportByYearAndStage(ApplicationTeam applicationTeam);

    ResponseResult getMiddleReportByYearAndStage(ApplicationTeam applicationTeam);

    ResponseEntity<Object> downloadTeamReport(String fileName) throws FileNotFoundException;
}
