package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.constant.JobOrderStateNodeConstant;
import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.PersonFather;
import com.tfxing.persondaily.entity.po.PersonSon;
import com.tfxing.persondaily.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@RestController
@RequestMapping("/test")
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

    private final HashMap<String, List<String>> dataMap = new HashMap<>();
    @PostConstruct
    public void initMapData() {
        dataMap.put("one", Arrays.asList("a","b"));
        dataMap.put("two", Arrays.asList("c","d"));
        dataMap.put("three", Arrays.asList("e","f"));
        dataMap.put("three", Arrays.asList("e","f"));
        dataMap.put("three", Arrays.asList("e","f"));
        //添加git提交内容  sfs
    }

    @PostMapping("/getMapData")
    public List<String> getMapData(String key) {
        return dataMap.get(key);
    }

    @GetMapping("/testInitMap")
    public List<String> testInitMap(String key) {
        return JobOrderStateNodeConstant.getJobOrderStateRuleMapByKey(key);
    }
    
}
