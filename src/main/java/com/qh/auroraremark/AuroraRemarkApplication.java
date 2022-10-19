package com.qh.auroraremark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qh.auroraremark.mapper")
public class AuroraRemarkApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraRemarkApplication.class, args);
    }

}
