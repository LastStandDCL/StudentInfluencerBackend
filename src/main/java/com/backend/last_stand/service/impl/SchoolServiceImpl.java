package com.backend.last_stand.service.impl;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.mapper.SchoolMapper;
import com.backend.last_stand.mapper.UserMapper;
import com.backend.last_stand.service.SchoolService;
import com.backend.last_stand.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/19 10:00
 */
@Service
public class SchoolServiceImpl  extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public ResponseResult getSchoolByName(String schoolName) {
        List<School> schools = schoolMapper.selectBySchoolName(schoolName);
        if (schools == null) {
            throw new RuntimeException("不存在此学校");
        }
        return new ResponseResult(200, "根据学校名称返回学校信息", schools);
    }

    @Override
    public ResponseResult getSchoolByProvince(String province) {
        List<School> schools = schoolMapper.selectByProvince(province);
        if (schools == null) {
            throw new RuntimeException("不存在此学校");
        }
        return new ResponseResult(200, "根据省份返回学校信息", schools);
    }
}
