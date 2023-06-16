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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.sql.DataSource;
import java.io.File;
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
@EnableMethodSecurity//启用方法级别的权限认证
@RequiredArgsConstructor
public class SpringSecurityConfig {
    @Autowired
    private RedisCache redisCache;



    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private PersistentTokenRepositoryImpl repository;

    @Autowired
    private DataSource dataSource;  // 数据源



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
                .tokenValiditySeconds(3600 * 24)//设置过期时间,这里设置的时间是一天
                .rememberMeServices(rememberMeServices())  // 设置自动登录使用哪个 rememberMeServices



                .and()
                .authorizeHttpRequests()
                // 对于登录接口 允许匿名访问
                .requestMatchers("/**").permitAll()
                .requestMatchers("/user/login").permitAll()//1
                .requestMatchers("/user/register").permitAll()//1
                .requestMatchers("/file/upload").permitAll()//0
                .requestMatchers("/oauth/render").permitAll()//0
                .requestMatchers("/oauth/callback").permitAll()//0
                .requestMatchers("/managers/getAllRoles").permitAll()//1
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()


                // at: 用来某个 filter 替换过滤器链中哪个 filter
                // 配置过滤器
                // before: 放在过滤器链中哪个 filter 之前
                // after: 放在过滤器链中那个 filter 之后
                // jwt认证
                .and()
                .addFilterBefore(JwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);



        // 异常拦截和处理
        http.exceptionHandling()
                //配置认证失败异常处理器
                .authenticationEntryPoint(authenticationEntryPoint).
               // 配置无权限访问异常处理器
                accessDeniedHandler(accessDeniedHandler);

        //处理跨域
        http.cors();

        return http.build();

    }



    /**
     * 角色权限覆盖关系
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_super > ROLE_manager > ROLE_teacher > ROLE_main_student > ROLE_vice_student > ROLE_student");
        return hierarchy;
    }



    @Bean
    public RememberMeServices rememberMeServices() {
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        // 配置数据源
//        jdbcTokenRepository.setDataSource(dataSource);
//        // 第一次启动的时候可以使用以下语句自动建表（可以不用这句话，自己手动建表，源码中有语句的）
////        jdbcTokenRepository.setCreateTableOnStartup(true);
//        return new RememberMeServiceImpl(UUID.randomUUID().toString(), userDetailsService, jdbcTokenRepository);
        return new RememberMeServiceImpl(UUID.randomUUID().toString(), userDetailsService, repository);
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 指定认证 url
        loginFilter.setFilterProcessesUrl("/user/login");
        // 指定接收json 用户名 key
        loginFilter.setUsernameParameter("email");
        // 指定接收 json 密码 key
        loginFilter.setPasswordParameter("password");

        loginFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));

        //设置认证成功时使用自定义rememberMeService
        loginFilter.setRememberMeServices(rememberMeServices());

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
            result.put("userInfo", authentication.getPrincipal());
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
            resp.setStatus(HttpStatus.OK.value());
            resp.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            resp.getWriter().println(s);
        });
        return loginFilter;
    }


    /**
     * 处理跨域问题
     * @return
     */
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


