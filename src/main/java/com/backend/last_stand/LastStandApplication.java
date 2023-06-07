package com.backend.last_stand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.backend.last_stand.entity")
@MapperScan("com.backend.last_stand.mapper")
@EnableScheduling//支持定期任务
public class LastStandApplication {

    public static void main(String[] args) {
        SpringApplication.run(LastStandApplication.class, args);
    }

}
