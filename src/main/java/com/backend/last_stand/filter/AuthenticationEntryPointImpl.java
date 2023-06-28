package com.backend.last_stand.filter;

import com.alibaba.fastjson.JSON;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description token出了问题，那么就会认证失败，会返回一个json给前端，提示前端token有问题,认证失败
 * &#064;date 2023/5/25 18:18
 */

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}

