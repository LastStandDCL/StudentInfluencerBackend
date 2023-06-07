package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Role;
import com.backend.last_stand.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhong
 * @version 1.0
 * @description 这个接口主要提供给manager和 super使用
 * @date 2023/6/6 11:44
 */
@RestController("/managers")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getPermissonFromRoleId")
    public ResponseResult getPermissonFromRoleId(@RequestBody Long id) {
        return roleService.getPermissonFromRoleId(id);
    }

    @GetMapping("/getAllRoles")
    public ResponseResult getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/updateRole")
    public ResponseResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @PostMapping("/deleteRole")
    public ResponseResult deleteRole(@RequestBody Long id) {
        return  roleService.deleteRole(id);
    }

    @PostMapping("/addRole")
    public ResponseResult addRole(@RequestBody Role role) {
        return  roleService.addRole(role);
    }

}
