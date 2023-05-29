package com.backend.last_stand.filter;

import com.backend.last_stand.entity.EnhancedUser;
import com.backend.last_stand.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.backend.last_stand.util.RedisCache;

import java.io.IOException;
import java.util.Objects;

/**
 * @author chenhong
 * @version 1.0
 * @description 用来认证token中的jwt并且解读信息
 * @date 2023/5/25 16:47
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    /**
     * redis工具类
     */
    @Autowired
    private RedisCache redisCache;

    /**
     * 解析token是否合法，从redis中解析出uid,通过uid从redis中获取json对象
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //如果不存在这个token，直接放行
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析token
        String userid;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }

        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        EnhancedUser enhancedUser = redisCache.getCacheObject(redisKey);
//        System.out.println(enhancedUser);


        if(Objects.isNull(enhancedUser)){
            throw new RuntimeException("用户未登录");
        }

        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken
                (enhancedUser,null, enhancedUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}

