package com.backend.last_stand.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 22:44
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 用户登录业务实现
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 用户注销业务实现
     * @return
     */
    ResponseResult logout();

    ResponseResult register(User user);

    ResponseResult update(User user);

    ResponseResult delete(User user);
}

