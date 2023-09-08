package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.po.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public Boolean login(@RequestBody User user) {
        if("link".equals(user.getUserName()) && "link12".equals(user.getPassWord())) {
            return true;
        }

        return false;
    }

}
