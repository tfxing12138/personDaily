package com.tfxing.persondaily.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@RestController
@RequestMapping("/helloWorld")
public class TestController {
    
    @GetMapping("/test")
    public String helloWorld() {
        return "hello world";
    }
}
