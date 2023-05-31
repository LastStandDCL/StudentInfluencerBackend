package com.backend.last_stand.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author chenhong
 * @version 1.0
 * @description 记住我配置
 * @date 2023/5/31 13:23
 */
@Configuration
public class RememberMeConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository getToken(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        //第一次启动项目时,设置为true,只要表已经存在,就设置为false,或者删除这句话
        repository.setCreateTableOnStartup(false);
        return repository;
    }
}

