package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.mapper.TeamMapper;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 12:19
 */
@Service
public class TeamServiceImpl  extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public ResponseResult getTeamMembers(Long id) {
        List<User> teamMembers = teamMapper.getTeamMembers(id);
        if (teamMembers == null) {
            throw new RuntimeException("队伍中人员为空");
        }
        return new ResponseResult<>(200, "获取队伍成员成功", teamMembers);
    }

    @Override
    public ResponseResult getSchool(Long id) {
        School school = teamMapper.getSchool(id);
        if (school == null) {
            throw new RuntimeException("队伍所属学校为空");
        }
        return new ResponseResult<>(200, "获取队伍所属成功", school);
    }

    @Override
    public ResponseResult getTeamInfo(Long id) {
        Team team = teamMapper.selectById(id);
        School school = teamMapper.getSchool(id);
        List<User> teamMembers = teamMapper.getTeamMembers(id);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("teamName", team.getTeamName());
        hashMap.put("school", school.getSchoolName());
        hashMap.put("members", teamMembers.toString());
//        hashMap.put("createTime", team.getCreateTime().toString());

        return new ResponseResult<>(200, "返回队伍相关信息", hashMap);
    }
}
