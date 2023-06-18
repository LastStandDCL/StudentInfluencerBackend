package com.backend.last_stand.service;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface TeamService extends IService<Team> {
    ResponseResult getTeamMembers(Long id);
}
