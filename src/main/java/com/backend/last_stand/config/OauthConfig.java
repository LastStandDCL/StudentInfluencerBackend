package com.backend.last_stand.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml中定义的用于Oauth登陆的参数
 */
@Component
@ConfigurationProperties(prefix = "oauth")
@Data
public class OauthConfig {
    // 向第三方申请的登陆服务名
    private String applicationId;
    // 第三方给出的登陆服务密钥
    private String applicationSecret;
    // 第三方服务的地址，如果有多个可以改比如微信登陆，QQ登陆
    private String gitlabUrl;
    // 第三方要求提供的回调url
    private String redirectUri;
    private String initRedirectUri;
}