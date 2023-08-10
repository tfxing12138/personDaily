package com.tfxing.persondaily.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfxing.persondaily.entity.po.User;
import com.tfxing.persondaily.utils.TokenUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/login")
    public HashMap login(@RequestParam(value = "userName") String userName, @RequestParam(value = "passWord") String passWord) throws JsonProcessingException {
        //包装token
        User user = new User();
        user.setUserName(userName);
        user.setPassWord(passWord);
        String token= TokenUtils.sign(user);
        HashMap<String,Object> hs=new HashMap<>();
        hs.put("token",token);
        return hs;
    }

}
