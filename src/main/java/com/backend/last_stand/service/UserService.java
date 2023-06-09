package com.backend.last_stand.service;


import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/5/24 22:44
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 用户登录业务实现
     * @param user
     * @return
     */
//    ResponseResult login(User user);

    /**
     * 用户注销业务实现
     * @return
     */
    ResponseResult logout();

    ResponseResult register(User user);

    ResponseResult update(User user);

    ResponseResult delete(User user);

    ResponseResult teacherChoose(Long id);


    ResponseResult getStudents(Integer pageNum, Integer pageSize);

    ResponseResult getViceStudents(Integer pageNum, Integer pageSize);

    ResponseResult getMainStudents(Integer pageNum, Integer pageSize);

    ResponseResult getTeachers(Integer pageNum, Integer pageSize);

    ResponseResult getManagers(Integer pageNum, Integer pageSize);

    ResponseResult selectUserByRole(Page<User> userPage, Long roleId);

    ResponseResult getUserRole(Long id);

    ResponseResult getUserInfoFromRedis(Long id);

    ResponseResult getUserTeam(User user);

    Team getMostRecentUserTeam(Long userId);

    ResponseResult addTeam(String info);

    ResponseResult updatePassword(User user);

    ResponseResult activitycount(String year);


    ResponseResult getUserCountGroupByDepartmentByYear(String year);

    ResponseResult countActiveUser(String year);

    ResponseResult getUserCountGroupByProvinceByYear(String year);

    ResponseResult exitTeam(String year);
}

