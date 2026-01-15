package com.stech.mybatisplus;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.stech.mybatisplus.mapper")
public class MybatisplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
        log.info("springbootmp启动成功~~~~^…^~~~~^…^~~~~~^…^~~~~");
    }

}
