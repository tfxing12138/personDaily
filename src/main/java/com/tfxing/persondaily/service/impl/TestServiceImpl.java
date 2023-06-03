package com.tfxing.persondaily.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTran2() {
        Question question = new Question();
        question.setDescription("测试事务 2023041103");
        questionMapper.insert(question);
        System.out.println(question.getId());
        Question question2 = questionMapper.selectOne(new LambdaQueryWrapper<Question>().eq(Question::getDescription, "测试事务 2023041103"));
        System.out.println(question2.getId());
        Question question1 = new Question();
        question1.setDescription("测试事务 2023041104");
        questionMapper.insert(question1);
        System.out.println(question1.getId());
    }
}
