package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.ResponseInfo;
import com.tfxing.persondaily.entity.annotation.Listen;
import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.entity.po.Question;
import com.tfxing.persondaily.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionServiceImpl;
    
    @PostMapping("/addQuestion")
    public ResponseInfo<Boolean> addQuestion(@RequestBody Question question) {
        return ResponseInfo.<Boolean>success(questionServiceImpl.addQuestion(question));
    }
    
    @PostMapping("/addAnswer")
    @Listen
    public ResponseInfo<Boolean> addAnswer(@RequestBody Answer answer) {
        return ResponseInfo.<Boolean>success(questionServiceImpl.addAnswer(answer));
    }
}
