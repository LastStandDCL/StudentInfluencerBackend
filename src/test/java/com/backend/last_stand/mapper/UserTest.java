package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/5/30 15:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUserName("2020300945");
        user.setNickName("小明");
        user.setPassword("1234567890");
        user.setEmail("1875435468@qq.com");
        user.setProvince("陕西省");
        user.setCity("西安市");
        userMapper.insert(user);
    }

    @Test
    public void testDate() {
        System.out.println(new Date());
    }

    @Test
    public void test03() {
        IPage<User> users = userMapper.getUsers(new Page<>(1, 1), 1L);
        System.out.println(users);
    }

    @Test
    public void test04() {
        Page<User> userPages = new Page<>(1, 5);
        Page<User> page = userMapper.selectPage(userPages, null);
        System.out.println(page);
    }
}
