package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/testForEach")
    public void testForEach() {
        answerService.testForEach();
    }

}
