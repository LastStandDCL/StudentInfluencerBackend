package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 12:18
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * 传入team的id， 可以获取队伍成员
     * @param id
     * @return
     */
    @PostMapping("/getMemembers")
    public ResponseResult getTeamMembers(Long id) {
        return teamService.getTeamMembers(id);
    }
}
