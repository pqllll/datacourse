package com;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@MapperScan("com.mapper")
@ServletComponentScan
@EnableTransactionManagement
@EnableCaching
public class zuoyeApp {

    public static void main(String[] args) {
        SpringApplication.run(zuoyeApp.class,args);
        log.info("项目启动");

    }
}
