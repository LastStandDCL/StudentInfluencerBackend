package com.backend.last_stand.mapper;


import com.backend.last_stand.entity.CountUserByYear;
import com.backend.last_stand.entity.Role;
import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.backend.last_stand.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/5/24 19:13
 */

public interface UserMapper extends BaseMapper<User> {


   /**
    * 根据username查询用户信息
    * @param userName
    * @return
    */
   User selectByUserName(@Param("userName") String userName);


   /**
    * 根据uid查询  用户具有的角色信息
    * @param id
    * @return
    */
   List<Role> selectRolesByUid(@Param("id") Long id);

   /**
    * 修改学生的角色信息
    */
   Integer updateRole(@Param("id") Long id, @Param("role_id") Long targetRoleId);

   IPage<User> getUsers(IPage<User> page, @Param("id") Long id);

    User selectByEmail(String email);

    List<Team> getUserTeam(Long id);

    Integer addTeam(Long userId, Long teamId);

    List<User> getStudentsFromActivity(Long activityId, String username);


    List<CountUserByYear> getDepartmentCountByYear(String year);

    List<CountUserByYear> getProvinceCountByYear(String year);

    Integer countActiveUser(@Param("begin") String begin, @Param("end") String end);
}
