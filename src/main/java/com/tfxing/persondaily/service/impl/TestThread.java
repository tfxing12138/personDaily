package com.tfxing.persondaily.service.impl;

import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.User;

/**
 * @author :tanfuxing
 * @date :2023/2/13
 * @description :
 */
public class TestThread {

    public static void main(String[] args) {
        User user = new User(100L,"tfxing");
        new Thread(() -> {
            while (user.getId() > 0) {
                sub(user);
            }
        }).start();

        new Thread(() -> {
            while (user.getId() > 0) {
                sub(user);
            }
        }).start();
    }
    
    synchronized static void sub(User user) {
        user.setId(user.getId()-1);        
        System.out.println("two:----->"+user.getId());
    }
}
