package com.backend.last_stand;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author bowen 2631702650@qq.com
 * @since 2023/1/19
 */
@SpringBootTest
public class MybatisGenerator {
    // 数据源配置
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG
            = new DataSourceConfig.Builder(
            "jdbc:mysql://43.143.250.239:3306/bikebackend?serverTimezone=GMT%2B8",
            "bikebackend",
            "laststand")
            .typeConvert(new MySqlTypeConvert());
    private final String projectPath = System.getProperty("user.dir");

    @Test
    public void generatorCode() {
        FastAutoGenerator
                .create(DATA_SOURCE_CONFIG)
                //全局配置
                .globalConfig(builder -> {
                    builder.author("bowen") // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir() // 执行完毕不打开文件夹
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent("com.backend.last_stand") // 设置父包名
                            .controller("controller") //生成controller层
                            .entity("entity") //生成实体层
                            .service("service") //生成服务层
                            .mapper("mapper") //生成mapper层
                            ;
                    // .moduleName("mybatisplus");
                })
                //软件配置
                .strategyConfig(builder -> {
                    builder
                            .addInclude("sys_pending_record", "sys_application_material")
                            .addTablePrefix("sys_")// 设置过滤表前缀
                            .serviceBuilder() //开启service软件配置
                            .formatServiceFileName("%sService") //取消Service前的I
                            .controllerBuilder() //开启controller软件配置
                            .enableRestStyle() //配置restful风格
                            .enableHyphenStyle() //url中驼峰转连字符
                            .entityBuilder() //开启实体类配置
                            .enableLombok() //开启lombok
                            .enableChainModel(); //开启lombok链式操作

                })
                //模板配置
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                //执行
                .execute();
    }
}
