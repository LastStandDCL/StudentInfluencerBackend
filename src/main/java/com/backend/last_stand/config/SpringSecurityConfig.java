package com.backend.last_stand.config;



import com.backend.last_stand.entity.EnhancedUser;
import com.backend.last_stand.entity.ResponseResult;
import com.backend.last_stand.entity.User;
import com.backend.last_stand.filter.JwtAuthenticationTokenFilter;
import com.backend.last_stand.filter.LoginFilter;
import com.backend.last_stand.service.impl.RememberMeServiceImpl;
import com.backend.last_stand.service.impl.UserDetailsServiceImpl;
import com.backend.last_stand.util.JwtUtils;
import com.backend.last_stand.util.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * @author chenhong
 * @version 1.0
 * @description Springsecurity配置类
 * @date 2023/5/21 21:52
 */
@Configuration
@EnableWebSecurity//开启springSecurity过滤器
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用方法级别的权限认证
@RequiredArgsConstructor
public class SpringSecurityConfig {
    @Autowired
    private RedisCache redisCache;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PersistentTokenRepositoryImpl repository;


    /** 将自定义JwtAuthenticationFilter注入
     *
     */
    @Autowired
    private final JwtAuthenticationTokenFilter JwtAuthenticationTokenFilter;


    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 访问权限不足时候的处理器
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //使用token,关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext，通过前后端互相传递json数据来进行操作
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .rememberMe()
                // 指定在登录时“记住我”的 HTTP 参数，默认为 remember-me
                .rememberMeParameter("remember-me")
                .rememberMeServices(rememberMeServices())  // 设置自动登录使用哪个 rememberMeServices
                .tokenValiditySeconds(3600 * 24)//1天过期
                .userDetailsService(userDetailsService)
                .tokenRepository(repository)

                .and()
                .authorizeHttpRequests()
                // 对于登录接口 允许匿名访问
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/hello").permitAll()
                .requestMatchers("/user/register").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()


                // 配置过滤器
                // at: 用来某个 filter 替换过滤器链中哪个 filter
                // before: 放在过滤器链中哪个 filter 之前
                // after: 放在过滤器链中那个 filter 之后
                // jwt认证
                .and()
                .addFilterBefore(JwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);



        // 异常拦截和处理
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
                accessDeniedHandler(accessDeniedHandler);

        //处理跨域
        http.cors();

        return http.build();

    }


    @Bean
    public RememberMeServices rememberMeServices() {

//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        //指定数据源
//        jdbcTokenRepository.setDataSource(dataSource);
//        //使用rememberMeServices时第一次需要手动创建表结构，数据库直接使用security即可，启动服务进行登录后，会存储此次登录认证信息
//        jdbcTokenRepository.setCreateTableOnStartup(true);

        return new RememberMeServiceImpl(UUID.randomUUID().toString(), userDetailsService, repository);
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/user/login"); // 指定认证 url
        loginFilter.setUsernameParameter("userName"); // 指定接收json 用户名 key
        loginFilter.setPasswordParameter("password"); // 指定接收 json 密码 key

        loginFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));

        loginFilter.setRememberMeServices(rememberMeServices());  //设置认证成功时使用自定义rememberMeService

        //认证成功处理
        loginFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            //从authenticate中获取EnhancedUser对象
            EnhancedUser enhancedUser = (EnhancedUser) authentication.getPrincipal();

            //使用userid生成token
            String userId = enhancedUser.getUser().getId().toString();
            //使用jwt工具类来生成token
            String jwt = JwtUtils.createJWT(userId);

            //authenticate存入redis
            redisCache.setCacheObject("login:"+userId, enhancedUser);
            System.out.println("将用户信息存入redis");

            //把token响应给前端

            Map<String, Object> result = new HashMap<String, Object>();
            result.put("code" , "200");
            result.put("msg", "登录成功");
            result.put("token",jwt);//将token放入map然后返回给前端
//            result.put("用户信息", authentication.getPrincipal());
            resp.setContentType("application/json;charset=UTF-8");
            resp.setStatus(HttpStatus.OK.value());
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler((req, resp, ex) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("code" , "400");
            result.put("msg", "登录失败: " + ex.getMessage());
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resp.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        return loginFilter;
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        configuration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    /**
     * 设置密码加密方式,替换掉默认的加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}


