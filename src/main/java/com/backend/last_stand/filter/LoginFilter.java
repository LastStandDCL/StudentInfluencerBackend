package com.backend.last_stand.filter;

import com.backend.last_stand.util.MyServletRequestWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

import java.io.IOException;

import java.util.Map;

/**
  * 自定义前后端分离认证 Filter
  */
 public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Override
     public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
         System.out.println("========================================");

         //1.判断是否是 post 方式请求
         if (!request.getMethod().equals("POST")) {
             throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
         }

         //2.判断是否是 json 格式请求类型
         if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
             //3.从 json 数据中获取用户输入用户名和密码进行认证 {"userName":"xxx","password":"xxx","remember-me":true}

             try {
                 //request.getInputStream()在这里以流的方式获取数据，会导致controller无法接收到参数
                 Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                 String username = userInfo.get(getUsernameParameter());
                 String password = userInfo.get(getPasswordParameter());

                 String rememberValue = userInfo.get(AbstractRememberMeServices.DEFAULT_PARAMETER);
                 //如果没有勾选remember-me，那就设置默认的属性
                 if (!ObjectUtils.isEmpty(rememberValue)) {
                     request.setAttribute(AbstractRememberMeServices.DEFAULT_PARAMETER, rememberValue);
                 }

                 System.out.println("用户名: " + username + " 密码: " + password + " 是否记住我: " + rememberValue);
                 //获取封装的信息

                 UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

                 setDetails(request, authRequest);

                 System.out.println("Loginfilter完成，返回authenticate,进行autenticate()");
                 //与数据库中的用户密码进行比对校验
                 return this.getAuthenticationManager().authenticate(authRequest);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

         System.out.println("LoginFilter执行完成， attenptAuthentication");
         return super.attemptAuthentication(request, response);
     }



 }
