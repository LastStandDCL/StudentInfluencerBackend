package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.controller.SchoolController;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        JSONObject jsonObject = JSON.parseObject(schoolName);
        String string = jsonObject.get("schoolName").toString();


        String newname = "%";
        for (int i = 0; i < string.length(); i++) {
            newname += string.charAt(i) + "%";
        }
        List<School> schools = schoolMapper.selectBySchoolName(newname);
        if (schools == null) {
            throw new RuntimeException("不存在此学校");
        }

        System.out.println(newname);
        return new ResponseResult(200, "根据学校名称返回学校信息", schools);
    }

    @Override
    public ResponseResult getSchoolByProvince(String province) {
        JSONObject jsonObject = JSON.parseObject(province);
        String provinces = jsonObject.get("province").toString();
        List<School> schools = schoolMapper.selectByProvince(provinces);
        List<String> info = new ArrayList<>();
        for (School school : schools) {
            info.add(school.getSchoolName());
        }
        if (schools == null) {
            return new ResponseResult(200, "该省份学校为空", info);
//            throw new RuntimeException("不存在此学校");
        }
        return new ResponseResult(200, "根据省份返回学校信息", info);
    }

    @Override
    public ResponseResult addSchool(School school) {
        //设置学校创建日期
        school.setCreateTime(new Date());
        //还没有审核，审核后才可以看见
        school.setIscheck("0");
        int insert = schoolMapper.insertSchool(school.getSchoolName(), school.getProvince(), school.getIscheck());
        if (insert != 1) {
            return new ResponseResult<>(208, "添加学校失败,该学校已存在", school);
        }
        //学校还没有审核
        return new ResponseResult<>(200, "添加学校成功", school);
    }
}
