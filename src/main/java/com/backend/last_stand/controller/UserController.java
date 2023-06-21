package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.Team;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 *
 * @author chenhong
 * @version 1.0
 * @description 用户类
 * @date 2023 /5/21 21:56
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录 只需要在请求体中携带Json数据即可,格式如 {"userName" : "*", "password":"1234"}
     * 后端会校验用户名和密码，并且根据uid生成一个jwt_token，返回给前端，前端在进行其他请求的时候
     * 需要校验jwt_token是否合法，合法才能进行其他操作
     * @param user
     * @return
     */
//    @PostMapping("/user/login")
//    public ResponseResult login(@RequestBody User user){
//        System.out.println("进入UserController中  /user/login");
//        return userService.login(user);
//    }


    /**
     * 登录出去，需要用户已经登录之后才可以登出，会检验登入的token值，如果不携带token，那么就没有权限，会返回403
     *
     * @return response result
     */
    @PostMapping("logout")
    @PreAuthorize("hasAnyAuthority('student')")
    public ResponseResult logout(){
        return userService.logout();
    }

    /**
     * 用户注册接口
     *
     * @param user the user
     * @return response result
     */
    @PostMapping("register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 更新用户信息，包括密码也可以传进来更新，更新资料和更新密码可以调用同一个接口
     *
     * @param user the user
     * @return response result
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody User user) {
        return userService.update(user);
    }

    /**
     * 删除用户
     *
     * @param user the user
     * @return response result
     */
    @PostMapping("delete")
    public ResponseResult delete(@RequestBody User user) {
        return userService.delete(user);
    }


    /**
     * 老师指定总负责人,传入学生的uid即可
     *
     * @param id the id
     * @return response result
     */
    @PostMapping("/teacher/choose")
    @PreAuthorize("hasAnyAuthority('teacher:choose')")
    public ResponseResult teacherChoose(@RequestBody Long id) {
        return userService.teacherChoose(id);
    }


    /**
     * Select by role response result.
     *
     * @param roleId   the role id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the response result
     */
    @GetMapping("/selectByRole")
    public ResponseResult selectByRole(@RequestBody Long roleId, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        return userService.selectUserByRole(page, roleId);
    }

    /**
     * 传入页码和分页的大小，可以获取role 为 1类型的用户 不包括分省负责人和总负责人
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return students
     */
    @GetMapping("/getStudents")
    public ResponseResult getStudents(Integer pageNum, Integer pageSize) {
        return userService.getStudents(pageNum, pageSize);
    }

    /**
     * 传入页码和分页的大小，可以获取role 为 2类型的用户  只返回分省负责人
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return vice students
     */
    @GetMapping("/getViceStudents")
    public ResponseResult getViceStudents(Integer pageNum, Integer pageSize) {
        return userService.getViceStudents(pageNum, pageSize);
    }


    /**
     * Gets main students.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the main students
     */
    @GetMapping("/getMainStudent")
    public ResponseResult getMainStudents(Integer pageNum, Integer pageSize) {
        return userService.getMainStudents(pageNum, pageSize);
    }

    /**
     * Gets teacher.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the teacher
     */
    @GetMapping("/getTeachers")
    public ResponseResult getTeacher(Integer pageNum, Integer pageSize) {
        return userService.getTeachers(pageNum, pageSize);
    }

    /**
     * Gets managers.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the managers
     */
    @GetMapping("/getManagers")
    public ResponseResult getManagers(Integer pageNum, Integer pageSize) {
        return userService.getManagers(pageNum, pageSize);
    }


    /**
     * Gets user role.
     *
     * @param id the id
     * @return the user role
     */
    @GetMapping("/getUserRole")
    public ResponseResult getUserRole(Long id) {
        return userService.getUserRole(id);
    }


    /**
     * 提供这个接口可以降低mysql压力，从缓存在redis中可以获取用户权限，用户角色以及用户信息
     *
     * @param id the id
     * @return user info from redis
     */
    @GetMapping("/getUserInfoFromRedis")
    public ResponseResult getUserInfoFromRedis(Long id) {
        return userService.getUserInfoFromRedis(id);
    }


    /**
     * Gets user team.
     *
     * @param user the user
     * @return the user team
     */
    @PostMapping("/getUserTeam")
    public ResponseResult getUserTeam(@RequestBody User user) {
        return userService.getUserTeam(user);
    }


    /**
     * 队员加入队伍,如果在当前活动年份已经有队伍，那就不能再加入新的队伍，除非退出原来队伍,需要传入队伍id和活动年份
     *
     * @param info the info
     * @return response result
     */
    @PostMapping("/addTeam")
    public ResponseResult addTeam(@RequestBody String info) {
        return userService.addTeam(info);
    }

}
