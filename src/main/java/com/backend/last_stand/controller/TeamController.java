package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 12:18
 */
@Slf4j
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
        log.info("请求/team/createTeam");
        return teamService.createTeam(team);
    }


    @PostMapping("/deleteTeam")
    public ResponseResult deleteTeam(@RequestBody Team team) {
        log.info("请求/team/deleteTeam");
        return teamService.deleteTeam(team);
    }

    @PostMapping("/updateTeam")
    public ResponseResult updateTeam(@RequestBody Team team) {
        log.info("请求/team/updateTeam");
        return teamService.updateTeam(team);
    }

    @PostMapping("/getTeamByYear")
    public ResponseResult getTeamByYear(@RequestBody String year) {
        return teamService.getTeamByYear(year);
    }

    @PostMapping("/getTeamBySchoolName")
    public ResponseResult getTeamBySchoolName(@RequestBody String schoolName) {
        return teamService.getTeamBySchoolName(schoolName);
    }

//    /**
//     * 添加成员
//     * @param team
//     * @return
//     */
//    @PostMapping("/addMember")
//    public ResponseResult addMember(@RequestBody Team team) {
//        log.info("请求/team/addMember");
//        return teamService.updateTeam(team);
//    }

    /**
     * 传入team的id， 可以获取队伍成员
     * @param
     * @return
     */
    @PostMapping("/getMemembers")
    public ResponseResult getTeamMembers(@RequestBody Team team) {
        log.info("请求/getMemembers------------" + "id :" + team.getId());
        return teamService.getTeamMembers(team.getId());
    }

    /**
     * 传入id，获取队伍所在学校
     * @param team
     * @return
     */
    @PostMapping("/getSchool")
    public ResponseResult getSchool(@RequestBody Team team) {
        log.info("请求/team/getSchool------------");
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
