package com.tfxing.persondaily.entity.po;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author :tanfuxing
 * @date :2023/2/1
 * @description :
 */
@Data
@Component
public class Person extends PersonFather{

    private Long id;

    private String personName;

    private Date birth;
    
    public static User user;

    public Person(Long id, String personName) {
        this.id = id;
        this.personName = personName;
    }

    public Person() {}
    
}
