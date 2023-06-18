package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.backend.last_stand.entity.User;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 11:54
 */
public interface TeamMapper extends BaseMapper<Team> {


    List<User> getTeamMembers(Long id);
}
