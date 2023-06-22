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
 * @date 2023/6/22 19:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ActivityTest {

    @Autowired
    private ActivityMapper activityMapper;

    @Test
    public void test01() {
        List<User> students = activityMapper.getStudents(1L, "2020%");
        System.out.println(students);
    }
}
