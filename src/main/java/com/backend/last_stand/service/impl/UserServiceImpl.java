package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.*;
import com.backend.last_stand.mapper.MenuMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;


import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.UserService;
import com.backend.last_stand.util.RedisCache;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 次方法封装会将用户登录信息和数据库中信息进行比对，然后为uid生成一个token,再将autenticate存入redis，便于提取信息
     * @param user
     * @return
     */
//    @Override
//    public ResponseResult login(User user) {
//        System.out.println("进入UserService方法， login中");
//        //获取封装的信息
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
//
//        //与数据库中的用户密码进行比对校验
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("用户名或密码错误");
//        }
//
//        //从authenticate中获取EnhancedUser对象
//        EnhancedUser enhancedUser = (EnhancedUser) authenticate.getPrincipal();
//
//        //使用userid生成token
//        String userId = enhancedUser.getUser().getId().toString();
//        //使用jwt工具类来生成token
//        String jwt = JwtUtils.createJWT(userId);
//
//        //authenticate存入redis
//        redisCache.setCacheObject("login:"+userId, enhancedUser);
//        System.out.println("login方法中将用户信息存入redis");
//
//        //把token响应给前端
//        HashMap<String,String> map = new HashMap<>();
//        map.put("token",jwt);//将token放入map然后返回给前端
//        return new ResponseResult(200,"登陆成功", map);
//    }

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
        //String userName = user.getUserName();

//        //要求学号是唯一存在的
//        User user1 = userMapper.selectByUserName(userName);
//        if (user1 != null) {// 如果能够在数据库中查询到这个账号，那么冲突了
//            throw new RuntimeException("注册失败，用户名存在");
//        }

        //查询是否已经存在此邮箱
        User user1 = userMapper.selectByEmail(user.getEmail());
        if (user1 != null) {
            throw new RuntimeException("注册失败，此邮箱已使用");
        }

        //没有认证学号之前，先赋值为邮箱
        user.setUserName(user.getEmail());

        //针对用户密码进行加密处理
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

        userMapper.insert(user);
        return new ResponseResult(200, "注册成功");
    }

    @Override
    public ResponseResult update(User user) {
        user.setUpdateTime(new Date());
        //这里如果要修改密码，那么密码需要加密后加入数据库中
        //针对用户密码进行加密处理
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);

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
    public ResponseResult teacherChoose(Long id) {
        //先根据id查找老师要指定的学生id
        User user = userMapper.selectById(id);

        //总负责人的Role_Id为3
        Long targetRoleId = 3L;

        Integer integer = userMapper.updateRole(id, targetRoleId);
        if (integer != 1) {
            throw new RuntimeException("用户角色更改失败");
        }

        //修改学生权限信息
        return new ResponseResult(200, "用户角色修改成功");
    }

    @Override
    public ResponseResult getStudents(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);

        IPage<User> getUser = userMapper.getUsers(page, 1L);
        return new ResponseResult<>(200, "返回学生结果", getUser);
    }

    @Override
    public ResponseResult getViceStudents(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> getUser = userMapper.getUsers(page, 2L);
        return new ResponseResult<>(200, "返回分省负责人结果", getUser);
    }

    @Override
    public ResponseResult getMainStudents(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> getUser = userMapper.getUsers(page, 3L);
        return new ResponseResult<>(200, "返回总负责人结果", getUser);
    }

    @Override
    public ResponseResult getTeachers(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> getUser = userMapper.getUsers(page, 4L);
        return new ResponseResult<>(200, "返回老师结果", getUser);
    }

    @Override
    public ResponseResult getManagers(Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> getUser = userMapper.getUsers(page, 5L);
        return new ResponseResult<>(200, "返回管理员结果", getUser);
    }

    @Override
    public ResponseResult selectUserByRole(Page<User> userPage, Long roleId) {
        IPage<User> users = userMapper.getUsers(userPage, roleId);
        return new ResponseResult<>(200, "返回指定角色结果", users);
    }

    @Override
    public ResponseResult getUserRole(Long id) {
        List<Role> roles = userMapper.selectRolesByUid(id);
        return new ResponseResult(200, "返回用户角色信息", roles);
    }

    @Override
    public ResponseResult getUserInfoFromRedis(Long id) {
        //从redis中获取用户信息
        String redisKey = "login:" + id;
        EnhancedUser enhancedUser = redisCache.getCacheObject(redisKey);
        if (enhancedUser == null) {
            throw new RuntimeException("从redis中获取用户信息失败");
        }
        return new ResponseResult(200, "从redis中获取用户信息成功", enhancedUser);
    }

    @Override
    public ResponseResult getUserTeam(User user) {
        List<Team> teams = userMapper.getUserTeam(user.getId());
        if (teams == null) {
            throw new RuntimeException("获取用户队伍为空");
        }
        return new ResponseResult(200, "获取用户队伍成功", teams);
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

