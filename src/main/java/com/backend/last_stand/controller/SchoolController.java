package com.backend.last_stand.controller;

import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenhong
 * @version 1.0
 * @description TODO
 * @date 2023/6/19 10:00
 */
@Slf4j
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * 直接传入名称即获取学校的信息  如   七台河市第一中学     不需要加引号，不需要以JSON格式给出，直接Body中写学校名称
     * @param schoolName
     * @return
     */
    @PostMapping("/getSchoolByName")
    public ResponseResult getSchoolByName(@RequestBody String schoolName) {
        log.info("/school/getSchoolByName");
        return schoolService.getSchoolByName(schoolName);
    }

    /**
     * 同上，直接传入省份，会返回这个省份的所有学校
     * @param province
     * @return
     */
    @PostMapping("/getSchoolByProvince")
    public ResponseResult getSchoolByProvince(@RequestBody String province) {
        log.info("/school/getSchoolByProvince");
        return schoolService.getSchoolByProvince(province);
    }

}
