package com.backend.last_stand.service;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface SchoolService extends IService<School> {

    ResponseResult getSchoolByName(String schoolName);

    ResponseResult getSchoolByProvince(String province);

    ResponseResult addSchool(School school);

    ResponseResult pending(Long schoolId, boolean pass);

    ResponseResult getPendingList();
}
