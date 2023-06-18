package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.User;
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


    @Test
    public void test01() {
        List<User> teamMembers = teamMapper.getTeamMembers(1L);
        System.out.println(teamMembers);
    }
}
