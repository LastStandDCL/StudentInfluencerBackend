package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.*;
import com.backend.last_stand.mapper.ApplicationTeamMapper;
import com.backend.last_stand.mapper.TeamMapper;
import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.ApplicationTeamService;
import com.backend.last_stand.service.FileService;
import com.backend.last_stand.service.PendingRecordService;
import com.backend.last_stand.service.TeamService;
import com.backend.last_stand.util.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 *
 *  团队报告申请服务实现类
 *
 *
 * @author wangziyang
 * @since 2023-06-28
 */
@Service
public class ApplicationTeamServiceImpl extends ServiceImpl<ApplicationTeamMapper, ApplicationTeam> implements ApplicationTeamService {

    private static final int TYPE_TEAMREPORT = 1;

    private static final int TYPE_MIDDLEREPORT = 0;

    private static final int STAGE_FAIL = 0;

    private static final int STAGE_SUCCESS = 1;

    private static final int STAGE_EXAMINING = 2;


    @Autowired
    private ApplicationTeamMapper applicationTeamMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PendingRecordService pendingRecordService;


    @Autowired
    private RedisCache redisCache;

    /**
     * 上传团队总结报告
     * @param file
     * @param year
     * @return
     */
    @Override
    public ResponseResult uploadTeamReport(MultipartFile file, String year) {

        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();

        List<Team> teams = teamMapper.getTeamByYearAndUserId(year, userId);
        if(teams == null){
            throw new RuntimeException("用户尚未加入队伍");
        }
        Team team = teams.get(0);
        String fileURL = fileService.saveFile(file, userId);

        //判断该年份总结报告是否重复提交
        QueryWrapper<ApplicationTeam> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ApplicationTeam::getTeamId, team.getId())
                        .eq(ApplicationTeam::getType, TYPE_TEAMREPORT)
                        .eq(ApplicationTeam::getYear, year);
        if(this.count(wrapper) > 0){
            throw new RuntimeException("重复提交年度总结报告");
        }

        //报告写入数据库
        ApplicationTeam applicationTeam = new ApplicationTeam();
        applicationTeam.setName(year + "年度" + team.getTeamName() + "团队总结报告");
        applicationTeam.setTeamId(team.getId());//队伍ID
        applicationTeam.setUpdaterId(userId);//上传者ID
        applicationTeam.setType(TYPE_TEAMREPORT);//上传报告类型
        Date time = new Date();
        applicationTeam.setApplyDate(time);//上传时间
        applicationTeam.setMaterialUrl(fileURL);//上传文件路径
        applicationTeam.setYear(year);//活动年份
        applicationTeamMapper.insert(applicationTeam);

        //日志写入数据库
        pendingRecordService.logTeamReportApplication(userId, team.getId(), year, time);

        return new ResponseResult(200,"团队总结报告提交成功");


    }

    /**
     * 上传团队中期报告
     * @param file
     * @param year
     * @return
     */
    @Override
    public ResponseResult uploadMiddleReport(MultipartFile file, String year) {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userId = enhancedUser.getUser().getId();

        List<Team> teams = teamMapper.getTeamByYearAndUserId(year, userId);
        if(teams == null){
            throw new RuntimeException("用户尚未加入队伍");
        }
        Team team = teams.get(0);
        String fileURL = fileService.saveFile(file, userId);

        //判断该年份总结报告是否重复提交
        QueryWrapper<ApplicationTeam> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ApplicationTeam::getTeamId, team.getId())
                .eq(ApplicationTeam::getType, TYPE_MIDDLEREPORT)
                .eq(ApplicationTeam::getYear, year);
        if(this.count(wrapper) > 0){
            throw new RuntimeException("重复提交年度总结报告");
        }

        //报告写入数据库
        ApplicationTeam applicationTeam = new ApplicationTeam();
        applicationTeam.setName(year + "年度" + team.getTeamName() + "团队中期报告");
        applicationTeam.setTeamId(team.getId());//队伍ID
        applicationTeam.setUpdaterId(userId);//上传者ID
        applicationTeam.setType(TYPE_MIDDLEREPORT);//上传报告类型
        Date time = new Date();
        applicationTeam.setApplyDate(time);//上传时间
        applicationTeam.setMaterialUrl(fileURL);//上传文件路径
        applicationTeam.setYear(year);//活动年份
        applicationTeamMapper.insert(applicationTeam);

        //日志写入数据库
        pendingRecordService.logMiddleReportApplication(userId, team.getId(), year, time);

        return new ResponseResult(200,"团队总结报告提交成功");
    }

    /**
     * 审查团队报告
     * @param info
     * @return
     */
    @Override
    public ResponseResult examineReport(HashMap<String, Object> info) {
        Long teamReportId = Long.parseLong(info.get("teamReportId").toString());
        int stage = Integer.parseInt(info.get("stage").toString());
        String message = info.get("message").toString();

        ApplicationTeam applicationTeam = applicationTeamMapper.selectById(teamReportId);
        applicationTeam.setStage(stage);
        applicationTeamMapper.updateById(applicationTeam);

        pendingRecordService.logReportExamination(applicationTeam, stage, message);

        return new ResponseResult(200, "审核完成");

    }

    /**
     * 通过年份和审核状态获得团队总结报告申报列表
     * @param applicationTeam
     * @return
     */
    @Override
    public ResponseResult getTeamReportByYearAndStage(ApplicationTeam applicationTeam) {
        String year = applicationTeam.getYear();
        int stage = applicationTeam.getStage();

        List<ApplicationTeam> applicationTeamList = applicationTeamMapper.getTeamReportByYearAndStage(year, stage);

        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (ApplicationTeam newApplicationTeam:
             applicationTeamList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("teamName", teamMapper.selectById(newApplicationTeam.getTeamId()).getTeamName());
            hashMap.put("updaterName", userMapper.selectById(newApplicationTeam.getUpdaterId()).getName());
            hashMap.put("updateTime", newApplicationTeam.getApplyDate());
            hashMap.put("fileURL", newApplicationTeam.getMaterialUrl());
            hashMap.put("teamReportId", newApplicationTeam.getId());
            hashMapList.add(hashMap);

        }

        int count = applicationTeamList.size();

        HashMap<String, Object> data = new HashMap<>();
        data.put("total", count);
        data.put("info", hashMapList);

        return new ResponseResult(200, "团队总结报告列表返回成功", data);
    }
}
