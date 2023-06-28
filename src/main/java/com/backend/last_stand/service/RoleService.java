package com.backend.last_stand.service;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface RoleService extends IService<Role> {


    ResponseResult getPermissionFromRoleId(Long id);

    ResponseResult getAllRoles();

    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long id);

    ResponseResult addRole(Role role);
}
