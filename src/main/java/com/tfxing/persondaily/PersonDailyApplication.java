package com.tfxing.persondaily;

import org.assertj.core.util.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.tfxing.persondaily.dao")
public class PersonDailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonDailyApplication.class, args);
        System.out.println("项目启动成功"+ DateUtil.formatAsDatetime(DateUtil.now()));
    }

}

