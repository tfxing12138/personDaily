package com.tfxing.persondaily.service.impl;

import com.tfxing.persondaily.dao.QuestionMapper;
import com.tfxing.persondaily.entity.po.Question;
import com.tfxing.persondaily.service.TestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author :tanfuxing
 * @date :2023/2/7
 * @description :
 */
@Service
public class TestServiceImpl implements TestService {
    
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private QuestionMapper questionMapper;


    @Override
    public String testmq(String json) {
        rabbitTemplate.convertAndSend("directExchange","direct.key","hello world");
        return "true";
    }

    @Override
    public void testTran() {
        Question question = new Question();
        question.setDescription("测试事务 20230321");
        questionMapper.insert(question);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveQuestion(Question question) {
        question.setDescription("inner测试事务 20230321");
        questionMapper.insert(question);
        int i = 1/0;
    }
}
