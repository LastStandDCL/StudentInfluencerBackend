package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
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
     * 需要传入 team_name、school_id
     * @param team
     * @return
     */
    @PostMapping("/createTeam")
    public ResponseResult createTeam(@RequestBody Team team) {
        System.out.println("请求/team/createTeam------------");
        return teamService.createTeam(team);
    }

    /**
     * 传入team的id， 可以获取队伍成员
     * @param
     * @return
     */
    @PostMapping("/getMemembers")
    public ResponseResult getTeamMembers(@RequestBody Team team) {
        System.out.println("请求/getMemembers------------" + "id :" + team.getId());
        return teamService.getTeamMembers(team.getId());
    }

    /**
     * 传入id
     * @param team
     * @return
     */
    @PostMapping("/getSchool")
    public ResponseResult getSchool(@RequestBody Team team) {
        return teamService.getSchool(team.getId());
    }

    /**
     * 传入id
     * @param team
     * @return
     */
    @PostMapping("/getTeamInfo")
    public ResponseResult getTeamInfo(@RequestBody Team team) {
        return teamService.getTeamInfo(team.getId());
    }
}
