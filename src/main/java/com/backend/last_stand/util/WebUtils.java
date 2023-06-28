package com.backend.last_stand.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author chenhong
 * @version 1.0
 * &#064;description 渲染字符串用的工具类
 * &#064;date 2023/5/23 17:28
 */


public class WebUtils {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
