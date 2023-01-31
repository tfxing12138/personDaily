package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.ResponseInfo;
import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.entity.po.Question;
import com.tfxing.persondaily.service.QuestionService;
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
    
    @Resource
    private QuestionService questionService;
    
    @PostMapping("/addQuestion")
    public ResponseInfo<Boolean> addQuestion(@RequestBody Question question) {
        return ResponseInfo.<Boolean>success(questionService.addQuestion(question));
    }
    
    @PostMapping("/addAnswer")
    public ResponseInfo<Boolean> addAnswer(@RequestBody Answer answer) {
        return ResponseInfo.<Boolean>success(questionService.addAnswer(answer));
    }
}
