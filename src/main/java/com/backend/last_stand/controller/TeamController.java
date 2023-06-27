package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Team controller.
 *
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023 /6/18 12:18
 */
@Slf4j
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;


    /**
     * 需要传入 team_name、school_id
     *
     * @param team the team
     * @return response result
     */
    @PostMapping("/createTeam")
    public ResponseResult createTeam(@RequestBody Team team) {
        log.info("请求/team/createTeam");
        return teamService.createTeam(team);
    }


    /**
     * Delete team response result.
     *
     * @param team the team
     * @return the response result
     */
    @PostMapping("/deleteTeam")
    public ResponseResult deleteTeam(@RequestBody Team team) {
        log.info("请求/team/deleteTeam");
        return teamService.deleteTeam(team);
    }

    /**
     * Update team response result.
     *
     * @param team the team
     * @return the response result
     */
    @PostMapping("/updateTeam")
    public ResponseResult updateTeam(@RequestBody Team team) {
        log.info("请求/team/updateTeam");
        return teamService.updateTeam(team);
    }

    /**
     * Gets team by year.
     *
     * @param year the year
     * @return the team by year
     */
    @PostMapping("/getTeamByYear")
    public ResponseResult getTeamByYear(@RequestBody String year) {
        return teamService.getTeamByYear(year);
    }

    /**
     * Gets team by school name.
     *
     * @param schoolName the school name
     * @return the team by school name
     */
    @PostMapping("/getTeamBySchoolName")
    public ResponseResult getTeamBySchoolName(@RequestBody String schoolName) {
        return teamService.getTeamBySchoolName(schoolName);
    }

    /**
     * Gets team by year and school name
     *
     * @param map year and school name
     * @return the team by year and school name
     */
    @PostMapping("/getTeamByYearAndSchoolName")
    public ResponseResult getTeamByYearAndSchoolName(@RequestBody HashMap<String,Object> map){
        return teamService.getTeamByYearAndSchoolName(map);
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
     *
     * @param team the team
     * @return team members
     */
    @PostMapping("/getMemembers")
    public ResponseResult getTeamMembers(@RequestBody Team team) {
        log.info("请求/getMemembers------------" + "id :" + team.getId());
        return teamService.getTeamMembers(team.getId());
    }

    /**
     * 传入id，获取队伍所在学校
     *
     * @param team the team
     * @return school
     */
    @PostMapping("/getSchool")
    public ResponseResult getSchool(@RequestBody Team team) {
        log.info("请求/team/getSchool------------");
        return teamService.getSchool(team.getId());
    }

    /**
     * 传入id
     *
     * @param team the team
     * @return team info
     */
    @PostMapping("/getTeamInfo")
    public ResponseResult getTeamInfo(@RequestBody Team team) {
        return teamService.getTeamInfo(team.getId());
    }


    @PostMapping("/count-province-info")
    public ResponseResult countByYear(@RequestBody Map<String,Object> map) {
        return teamService.countByYear(map);
    }



}
