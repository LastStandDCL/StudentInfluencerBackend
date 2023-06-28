package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Role;
import com.backend.last_stand.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The type Role controller.
 *
 * @author chenhong
 * @version 1.0
 * &#064;description 这个接口主要提供给manager和 super使用
 * &#064;date 2023 /6/6 11:44
 */
@RestController
@RequestMapping("/managers")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Gets permisson from role id.
     *
     * @param id the id
     * @return the permisson from role id
     */
    @GetMapping("/getPermissonFromRoleId")
    public ResponseResult getPermissonFromRoleId(@RequestBody Long id) {
        return roleService.getPermissionFromRoleId(id);
    }

    /**
     * Gets all roles.
     *
     * @return the all roles
     */
    @GetMapping("/getAllRoles")
    public ResponseResult getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * Update role response result.
     *
     * @param role the role
     * @return the response result
     */
    @PostMapping("/updateRole")
    public ResponseResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    /**
     * Delete role response result.
     *
     * @param id the id
     * @return the response result
     */
    @PostMapping("/deleteRole")
    public ResponseResult deleteRole(@RequestBody Long id) {
        return  roleService.deleteRole(id);
    }

    /**
     * Add role response result.
     *
     * @param role the role
     * @return the response result
     */
    @PostMapping("/addRole")
    public ResponseResult addRole(@RequestBody Role role) {
        return  roleService.addRole(role);
    }

}
