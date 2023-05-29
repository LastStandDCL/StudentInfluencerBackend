package com.backend.last_stand.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 这个类用于设置 MybatisPlus 的配置属性
 *
 * @author bowen 2631702650@qq.com
 * @since 2023/1/26
 */
@MapperScan("com.backend.last_stand.mapper")
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件。如果你不配置，分页插件将不生效
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 指定数据库方言为 MYSQL
        interceptor.addInnerInterceptor(
                new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}