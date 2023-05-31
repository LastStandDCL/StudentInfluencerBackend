package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.EnhancedUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.mapper.MenuMapper;
import com.backend.last_stand.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author chenhong
 * @version 1.0
 * @description UserDetailsService的实现类
 * @date 2023/5/25 13:50
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        //根据用户名获取用户信息
        User user = userMapper.selectOne(wrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }


        //根据用户信息获取权限，并且封装到EnhancedUser类中
        List<String> permissionKeyList =  menuMapper.selectPermsByUserId(user.getId());
        System.out.println("loadUserByUser方法调用完成，用户校验成功,封装权限并且返回");
        //填充权限信息，并且返回
        return new EnhancedUser(user,permissionKeyList);
    }

}