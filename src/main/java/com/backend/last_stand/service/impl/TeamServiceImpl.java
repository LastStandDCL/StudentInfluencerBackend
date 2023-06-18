package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.mapper.TeamMapper;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
