package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.backend.last_stand.entity.User;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/18 11:54
 */
public interface TeamMapper extends BaseMapper<Team> {


    List<User> getTeamMembers(Long id);

    School getSchool(Long id);

    List<Team> getTeamBySchoolName(String schoolName);

    List<Team> getTeamByYear(String year);

    List<Team> getTeamByProvince(String province);

    List<Team> getTeamByProvinceAndYear(String province, String year);

    List<Team> getTeamByYearAndProvince(String years, String provinceName);

    List<Team> getTeamByYearAndSchoolName(String year, String schoolName);
}
