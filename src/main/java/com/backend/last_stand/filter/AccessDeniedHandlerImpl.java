package com.backend.last_stand.filter;

import com.alibaba.fastjson.JSON;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description 授权异常拦截，在用户没有权限的时候回给前端提示
 * 访问被拒绝,这个类在用户权限不足的时候，会返回一个Json给前端{"code" : 403, "msg" : "权限不足"}
 * &#064;date 2023/5/25 18:17
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException){
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "权限不足");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}

