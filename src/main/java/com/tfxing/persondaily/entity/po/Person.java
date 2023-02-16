package com.tfxing.persondaily.entity.po;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author :tanfuxing
 * @date :2023/2/1
 * @description :
 */
@Data
@Component
public class Person extends PersonFather{
    
    private String personName;
    
    public static User user;
    
}
