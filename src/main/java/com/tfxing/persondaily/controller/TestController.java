package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.PersonFather;
import com.tfxing.persondaily.entity.po.PersonSon;
import com.tfxing.persondaily.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@RestController
@RequestMapping("/helloWorld")
public class TestController {
    
    @Resource
    private TestService testService;
    
    @GetMapping("/test")
    public String helloWorld() {
        List<? extends Person> list = new ArrayList<>();
//        list.add(new PersonSon());
        
        List<? super Person> pList = new ArrayList<>();
//        pList.add(new PersonFather());
        return "hello world";
    }
    
    @PostMapping("/testmq")
    public String testmq(@RequestParam String json) {
        return testService.testmq(json);
    } 
    
}
