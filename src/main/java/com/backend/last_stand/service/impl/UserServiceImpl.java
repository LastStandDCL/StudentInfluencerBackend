package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.*;
import com.backend.last_stand.mapper.ActivityMapper;
import com.backend.last_stand.mapper.MenuMapper;
import com.backend.last_stand.mapper.TeamMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;


import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.UserService;
import com.backend.last_stand.util.RedisCache;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jnr.ffi.annotations.In;
import liquibase.util.CollectionUtil;
import org.jetbrains.annotations.NotNull;
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
    private TeamMapper teamMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private ActivityMapper activityMapper;

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
            System.out.println("注册失败，邮箱已经被使用");
            return new ResponseResult(202, "注册失败，邮箱已经被使用");
//            throw new RuntimeException("注册失败，此邮箱已使用");
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
        //更新时间修改
        user.setUpdateTime(new Date());

        int i = userMapper.updateById(user);
        if (i != 1) {
            return new ResponseResult<>(203, "更新信息失败", user);
//            throw new RuntimeException("更新信息失败");
        }

        return new ResponseResult<>(200, "更新信息成功", user);
    }

    @Override
    public ResponseResult delete(User user) {

        int i = userMapper.deleteById(user);
        if (i != 1) {
            return new ResponseResult<>(206, "删除信息失败");
//            throw new RuntimeException("删除信息失败");
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
    public ResponseResult addTeam(String info) {
        JSONObject jsonObject = JSON.parseObject(info);
        //队伍id
        String teamId = jsonObject.get("teamId").toString();
        //用户id
        String userId = jsonObject.get("userId").toString();
        //年丰
        String year = jsonObject.get("year").toString();

        //转化为Long
        long luser = Long.parseLong(userId);
        long lteam = Long.parseLong(teamId);

        //先获取用户的队伍信息,不允许一年内加入多个队伍,年份不允许冲突
        List<Team> userTeam = userMapper.getUserTeam(luser);
        if (userTeam != null) {
            for (Team team : userTeam) {
                String year1 = team.getYear();
                if (year1.equals(year)) {
                    throw new RuntimeException("用户在" + year + "年已经加入队伍");
                }
            }
        }

        //获取需要加入的队伍的信息
        Team team = teamMapper.selectById(lteam);
        if (team == null) {
            throw new RuntimeException("要加入的队伍不存在");
        }

        //如果不存在问题，那么就会在sys_ts表中添加记录  user_id team_id
        userMapper.addTeam(luser, lteam);

        return new ResponseResult<>(200, "加入队伍成功");
    }

    @Override
    public ResponseResult updatePassword(User user) {
        //修改时间
        user.setUpdateTime(new Date());
        String password = user.getPassword();
        if (password != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder.encode(password);
            user.setPassword(encode);
            int update = userMapper.updateById(user);
            if (update != 1) {
                return new ResponseResult<>(204, "更新密码失败", user);
            } else {
                return new ResponseResult<>(200, "更新用户密码成功", user);
            }
        } else {
            return new ResponseResult<>(205, "输入密码为空", user);
        }
    }

    @Override
    public ResponseResult activitycount(String year) {
        JSONObject jsonObject = JSON.parseObject(year);
        String years = jsonObject.get("year").toString();
        System.out.println(years);
        //根据年份获取活动信息
        Activity activity = activityMapper.getByYear(years);
        System.out.println(activity);
        HashMap<String, String> hashMap = new HashMap<>();

        //获取参与这个活动的年份的学生总数
        if (activity != null) {
            String str = "2020%";
            List<User> users1 = activityMapper.getStudents(activity.getId(), str);

            hashMap.put("2020", Integer.toString(users1.size()));

            //获取参与这个活动的年份的学生总数
            List<User> users2 = activityMapper.getStudents(activity.getId(), "2021%");
            hashMap.put("2021", Integer.toString(users2.size()));

            //获取参与这个活动的年份的学生总数
            List<User> users3 = activityMapper.getStudents(activity.getId(), "2022%");
            hashMap.put("2022", Integer.toString(users3.size()));

            //获取参与这个活动的年份的学生总数
            List<User> users4 = activityMapper.getStudents(activity.getId(), "2023%");
            hashMap.put("2023", Integer.toString(users4.size()));

            //获取参与这个活动的年份的学生总数
            List<User> users5 = activityMapper.getStudents(activity.getId(), "2024%");
            hashMap.put("2024", Integer.toString(users5.size()));
        }

        return new ResponseResult<>(200, "返回人数成功", hashMap);
    }


    /**
     * 根据年份返回各个学院参加的学生数
     * @param year
     * @return
     */
    @Override
    public ResponseResult getUserCountGroupByDepartmentByYear(String year) {
        JSONObject jsonObject = JSON.parseObject(year);
        String years = jsonObject.get("year").toString();
        System.out.println(years);

        //获取对应年份各学院参加活动的人数
        List<DepartmentCountByYear> departmentCountByYears = userMapper.getDepartmentCountByYear(years);
        List<HashMap<String,String>> data = new ArrayList();

        for(int i = 0 ; i < departmentCountByYears.size() - 1  ; i++){
            HashMap<String,String> hashMap = new HashMap<>();
//            hashMap.put("value",departmentCountByYears.get(i).getValue().toString());
//            hashMap.put("name",departmentCountByYears.get(i).getName());
            data.add(hashMap);
        }

        //返回前端响应，未找到data为空
        return new ResponseResult<>(200,"返回各学院人数成功",data);

    }
  
     @Override
    public ResponseResult countActiveUser(String year) {
    List<User> total = userMapper.countActiveUser(year);
        return null;
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

