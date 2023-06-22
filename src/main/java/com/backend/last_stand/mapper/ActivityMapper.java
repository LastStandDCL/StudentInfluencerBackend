package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.Activity;
import com.backend.last_stand.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface ActivityMapper extends BaseMapper<Activity> {

    Activity getByYear(String years);

    List<User> getStudents(Long id, String username);
}
