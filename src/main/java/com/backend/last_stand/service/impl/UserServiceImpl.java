package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.EnhancedUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.UserService;
import com.backend.last_stand.util.JwtUtils;
import com.backend.last_stand.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 22:46
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    /**
     * 次方法封装会将用户登录信息和数据库中信息进行比对，然后为uid生成一个token,再将autenticate存入redis，便于提取信息
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {
        //获取封装的信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        //与数据库中的用户密码进行比对校验
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //从authenticate中获取EnhancedUser对象
        EnhancedUser enhancedUser = (EnhancedUser) authenticate.getPrincipal();

        //使用userid生成token
        String userId = enhancedUser.getUser().getId().toString();
        //使用jwt工具类来生成token
        String jwt = JwtUtils.createJWT(userId);

        //authenticate存入redis
        redisCache.setCacheObject("login:"+userId, enhancedUser);

        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);//将token放入map然后返回给前端
        return new ResponseResult(200,"登陆成功", map);
    }

    /**
     * 登出业务实现
     * @return
     */
    @Override
    public ResponseResult logout() {
        //获取authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //从authentication中取出用户信息
        EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();
        Long userid = enhancedUser.getUser().getId();
        //从redis缓存中删除用户信息
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }




    @Override
    public ResponseResult register(User user) {
        user.setCreateTime(new Date());// 用户创建日期
        //注册的时候前端传入的json需要指定 创建的用户是 1:学生还是2:老师
        String userName = user.getUserName();
        //要求学号是唯一存在的
        User user1 = userMapper.selectByUserName(userName);
        if (user1 != null) {// 如果能够在数据库中查询到这个账号，那么冲突了
            throw new RuntimeException("注册失败，用户名存在");
        }
        //针对用户密码进行加密处理
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        userMapper.insert(user);
        return new ResponseResult(200, "注册成功");
    }

    @Override
    public ResponseResult update(User user) {
        int i = userMapper.updateById(user);
        if (i != 1) {
            throw new RuntimeException("更新信息失败");
        }

        return new ResponseResult<>(200, "更新信息成功");
    }

    @Override
    public ResponseResult delete(User user) {

        int i = userMapper.deleteById(user);
        if (i != 1) {
            throw new RuntimeException("删除信息失败");
        }
        return new ResponseResult<>(200, "删除信息成功");
    }



    @Override
    public boolean saveBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<User> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        return false;
    }

    @Override
    public User getOne(Wrapper<User> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<User> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<User> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public UserMapper getBaseMapper() {
        return null;
    }

    @Override
    public Class<User> getEntityClass() {
        return null;
    }
}

