package com.tfxing.persondaily.entity.po;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author :tanfuxing
 * @date :2023/2/1
 * @description :
 */
@Component
@Data
public class User {

    private Long id;
    
    private String userName;
    
    public User(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }
    
    public User(){}
    
    public static void staticMethod() {
        System.out.println("hello world static");
    }
    
}