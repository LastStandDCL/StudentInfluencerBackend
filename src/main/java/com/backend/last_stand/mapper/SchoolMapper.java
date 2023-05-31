package com.backend.last_stand.mapper;

import com.backend.last_stand.entity.School;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/5/31 19:13
 */
public interface SchoolMapper extends BaseMapper<School> {

    List<School> selectBySchoolName(String schoolName);

    List<School> selectByProvince(String province);
}
