package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/5/21 21:56
 */
@RestController
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
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return userService.logout();
    }

    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/user/update")
    public ResponseResult update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("user/delete")
    public ResponseResult delete(@RequestBody User user) {
        return userService.delete(user);
    }




    /**
     * 测试接口用的
     * @return
     */
    @GetMapping("/user/hello")
    public ResponseResult hello() {
        return new ResponseResult(200, "HELLO~");
    }

}
