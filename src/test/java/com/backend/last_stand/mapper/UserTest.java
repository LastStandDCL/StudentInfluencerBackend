package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.Team;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.util.JwtUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

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

    @Test
    public void  test05() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MTFkMzBkMTA1YTM0YzlkODkzN2VmNDYyMTM3NGI2NyIsInN1YiI6IjE2NjM4MDY2OTcxOTI4Nzg" +
                "wODEiLCJpc3MiOiJzZyIsImlhdCI6MTY4NjgxOTk4MSwiZXhwIjoxNjg2ODIzNTgxfQ.Aeuann9_i_4Tbtt2jfeK6xjslfFnL6lbVD47HQH7_ps";
        Claims claims = JwtUtils.parseJWT(token);

        Date expiration = claims.getExpiration();//获取过期时间
        System.out.println(expiration);

//        Date exp = (Date)claims.get("exp");

//
//        System.out.println(date);
    }


    @Test
    public void test06() {
        List<User> studentsFromActivity = userMapper.getStudentsFromActivity(1L, "2020%");
        System.out.println(studentsFromActivity);

    }

    @Test
    public void test07() {
        Integer users = userMapper.countActiveUser("2019", "2023");
        System.out.println(users);

    }
}
