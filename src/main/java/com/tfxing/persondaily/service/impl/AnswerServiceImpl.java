package com.tfxing.persondaily.service.impl;

import com.tfxing.persondaily.dao.AnswerMapper;
import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public void testForEach() {
        List<String> list = Arrays.asList("A","B");
        List<Answer> answerList = answerMapper.testForEach(list);
        System.out.println(answerList.toString());
    }
}
