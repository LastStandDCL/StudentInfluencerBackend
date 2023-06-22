package com.backend.last_stand.service;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface TeamService extends IService<Team> {
    ResponseResult getTeamMembers(Long id);

    ResponseResult getSchool(Long id);

    ResponseResult getTeamInfo(Long id);

    ResponseResult createTeam(Team team);

    ResponseResult deleteTeam(Team team);

    ResponseResult updateTeam(Team team);

    ResponseResult getTeamBySchoolName(String schoolName);

    ResponseResult getTeamByYear(String year);

    ResponseResult countByYear(Map<String,Object> map);
}
