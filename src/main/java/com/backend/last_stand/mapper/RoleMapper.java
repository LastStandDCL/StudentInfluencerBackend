package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.Role;
import com.backend.last_stand.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<Menu> getPermissonFromRoleId(@Param("id") Long id);


}
