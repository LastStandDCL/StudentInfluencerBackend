package com.backend.last_stand.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.backend.last_stand.entity.User;
import io.lettuce.core.dynamic.annotation.Param;


/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 19:13
 */


public interface UserMapper extends BaseMapper<User> {

   User selectByUserName(@Param("userName") String userName);

}
