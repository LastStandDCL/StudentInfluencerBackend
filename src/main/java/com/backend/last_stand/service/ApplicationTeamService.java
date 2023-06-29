package com.backend.last_stand.service;

import com.backend.last_stand.entity.ApplicationTeam;
import com.backend.last_stand.entity.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
}
