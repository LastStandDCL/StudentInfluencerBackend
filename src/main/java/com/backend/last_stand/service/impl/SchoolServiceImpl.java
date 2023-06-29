package com.backend.last_stand.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.School;
import com.backend.last_stand.mapper.SchoolMapper;
import com.backend.last_stand.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description TODO
 * &#064;date 2023/6/19 10:00
 */
@Service
public class SchoolServiceImpl  extends ServiceImpl<SchoolMapper, School> implements SchoolService {

    private static final int REJECTED = 0;
    private static final int PASSED = 1;

    private static final int PENDING = 2;
    @Override
    public ResponseResult getSchoolByName(String schoolName) {
        JSONObject jsonObject = JSON.parseObject(schoolName);
        String string = jsonObject.get("schoolName").toString();


        StringBuilder newName = new StringBuilder("%");
        for (int i = 0; i < string.length(); i++) {
            newName.append(string.charAt(i)).append("%");
        }
        List<School> schools = baseMapper.selectBySchoolName(newName.toString());
        if (schools == null) {
            throw new RuntimeException("不存在此学校");
        }

        System.out.println(newName);
        return new ResponseResult(200, "根据学校名称返回学校信息", schools);
    }

    @Override
    public ResponseResult getSchoolByProvince(String province) {
        JSONObject jsonObject = JSON.parseObject(province);
        String provinces = jsonObject.get("province").toString();
        List<School> schools = baseMapper.selectByProvince(provinces);
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
        school.setCreateTime(LocalDateTime.now());
        //还没有审核，审核后才可以看见
        school.setIsCheck(PENDING);
        int insert = baseMapper.insertSchool(school.getSchoolName(), school.getProvince(), school.getIsCheck());
        if (insert != 1) {
            return new ResponseResult(208, "添加学校失败,该学校已存在", school);
        }
        //学校还没有审核
        return new ResponseResult(200, "添加学校成功", school);
    }

    /**
     * 修改学校
     * @param school
     * @return
     */
    @Override
    public ResponseResult pending(School school) {
        return null;
    }

    /**
     * 获得正在审核中的学校列表
     * @return
     */
    @Override
    public ResponseResult getPendingList() {
        return null;
    }
}
