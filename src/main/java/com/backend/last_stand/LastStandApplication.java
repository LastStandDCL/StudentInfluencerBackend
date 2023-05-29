package com.backend.last_stand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.backend.last_stand.entity")
@MapperScan("com.backend.last_stand.mapper")
public class LastStandApplication {

    public static void main(String[] args) {
        SpringApplication.run(LastStandApplication.class, args);
    }

}
