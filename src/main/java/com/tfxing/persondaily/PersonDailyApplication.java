package com.tfxing.persondaily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class PersonDailyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonDailyApplication.class, args);
        System.out.println("项目启动成功"+new Date());
    }

}

