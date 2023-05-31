package com.backend.last_stand.config;



import com.backend.last_stand.entity.User;
import com.backend.last_stand.filter.JwtAuthenticationTokenFilter;
import com.backend.last_stand.filter.LoginFilter;
import com.backend.last_stand.service.impl.RememberMeServiceImpl;
import com.backend.last_stand.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
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
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;


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
    private AuthenticationManager authenticationManager;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //使用token,关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext，通过前后端互相传递json数据来进行操作
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                .rememberMe()
                .rememberMeServices(rememberMeServices())  // 设置自动登录使用哪个 rememberMeServices
                .tokenValiditySeconds(3600)//一个小时过期
                .userDetailsService(userDetailsService)  // 登录的时候交给什么对象
                .tokenRepository(getToken())

                // jwt认证
                .and()
                .addFilterBefore(JwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                // 对于登录接口 允许匿名访问
                .requestMatchers("/user/login").permitAll()
                .requestMatchers("/user/hello").permitAll()
                .requestMatchers("/user/register").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
                accessDeniedHandler(accessDeniedHandler);

        http.cors();


        // at: 用来某个 filter 替换过滤器链中哪个 filter
        // before: 放在过滤器链中哪个 filter 之前
        // after: 放在过滤器链中那个 filter 之后
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


    /**
     * remember_me配置 基于数据库实现持久化令牌
     * @return
     */
    @Bean
    public PersistentTokenRepository getToken(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        //第一次启动项目时,设置为true,只要表已经存在,就设置为false,或者删除这句话
        repository.setCreateTableOnStartup(false);
        return repository;
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new RememberMeServiceImpl(UUID.randomUUID().toString(), userDetailsService, new InMemoryTokenRepositoryImpl());
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/doLogin");//指定认证 url
        loginFilter.setUsernameParameter("uname");//指定接收json 用户名 key
        loginFilter.setPasswordParameter("passwd");//指定接收 json 密码 key
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setRememberMeServices(rememberMeServices()); //设置认证成功时使用自定义rememberMeService
        //认证成功处理
        loginFilter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", "登录成功");
            result.put("用户信息", authentication.getPrincipal());
            resp.setContentType("application/json;charset=UTF-8");
            resp.setStatus(HttpStatus.OK.value());
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        //认证失败处理
        loginFilter.setAuthenticationFailureHandler((req, resp, ex) -> {
            Map<String, Object> result = new HashMap<String, Object>();
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


