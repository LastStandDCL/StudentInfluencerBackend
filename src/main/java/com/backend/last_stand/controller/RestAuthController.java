package com.backend.last_stand.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.backend.last_stand.entity.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @author chenhong
 * @version 1.0
 * @description justoauth集成第三方gitee登录
 * @date 2023/6/13 19:41
 */
@RestController
@RequestMapping("/oauth")
public class RestAuthController {

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse login = authRequest.login(callback);
        JSONObject remoteData = JSONObject.parseObject(JSON.toJSONString(login));
        Object giteeUser = remoteData.get("data");//取出用户信息
        JSONObject user = JSONObject.parseObject(JSON.toJSONString(giteeUser));
        return new ResponseResult(200, "用户信息返回成功", user);
    }

    private AuthRequest getAuthRequest() {
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId("04c25384e2c99ac11c7a2aaf923744642d5c86343c9201b4d8c154d2f46b0b15")
                .clientSecret("6b4e9f4c96839b4663c548bd2652ab299a1dd1e8a353b86af144dee3c452613e")
                .redirectUri("http://localhost:8010/oauth/callback")
                .build());
    }
}

