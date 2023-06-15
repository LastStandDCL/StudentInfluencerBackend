package com.backend.last_stand.controller;

import com.backend.last_stand.service.MailService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

/**
 * 发送邮箱验证码，返回给前端
 * 完成生成邮箱验证码发送给用户功能，后端生成的验证码会返回给前端，前端进行校验
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * 只接受email数据
     * @param emailObj
     * @return
     */
    @GetMapping("/mails/sendRegisterCode")
    public String getRegeisterCode(@RequestBody Map<String,String> emailObj){
        String email = emailObj.get("email");
        System.out.println(email);

        //随机生成验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            return "";
        }
        return checkCode;
    }

    @GetMapping("/mails/sendLoginCode")
    public String getLoginCode(@RequestBody Map<String,String> emailObj){
        String email = emailObj.get("email");
        System.out.println(email);

        //随机生成验证码
        String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的登录验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "登录验证码", message);
        }catch (Exception e){
            return "";
        }
        return checkCode;
    }
}