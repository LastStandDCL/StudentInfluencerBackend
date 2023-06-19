package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.TeamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 12:57
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamTest {
    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TeamService teamService;


    @Test
    public void test01() {
        List<User> teamMembers = teamMapper.getTeamMembers(1L);
        System.out.println(teamMembers);
    }

    @Test
    public void test02() {
        School school = teamMapper.getSchool(1L);
        System.out.println(school);
    }

    @Test
    public void test03() {
        ResponseResult teamMembers = teamService.getTeamMembers(1L);
        System.out.println(teamMembers);
    }

    @Test
    public void test04() {
        Team team = new Team();
        team.setTeamName("香锅队");
        team.setSchoolId(1663771895756554241L);
        ResponseResult team1 = teamService.createTeam(team);
        System.out.println(team1);
    }

    @Test
    public void test05() {
        List<Team> teams = teamMapper.getTeamBySchoolName("合肥一六八中学");
        System.out.println(teams);
    }
}
