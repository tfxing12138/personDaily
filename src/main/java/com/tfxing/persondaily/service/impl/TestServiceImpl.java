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

    @Override
    public String testTran3(Integer num) {
        return num/10+"";
    }

    /**
     * 'a   bcdsa  fad  '
     * @param str
     * @return
     */
    @Override
    public int lengthOfLastWord(String str) {
        char[] chars = str.toCharArray();

        boolean firstChar = false;

        int num = 0;

        for (int i = chars.length-1; i >= 0 ; i--) {

            char aChar = chars[i];
            if(!Character.isWhitespace(aChar)) {
                firstChar = true;
            }

            if(!firstChar) {
                continue;
            }

            if(Character.isWhitespace(aChar)) {
                break;
            }

            num++;
        }

        return num;
    }

    /**
     * 132.afda   fa13141  saf
     * @param str
     * @return
     */
    @Override
    public int myAtoi(String str) {
        char[] chars = str.toCharArray();

        String charStr = "";

        boolean firstDigit = false;

        boolean isMins = false;

        for (int i = chars.length-1; i >= 0; i--) {
            char aChar = chars[i];

            if(Character.isDigit(aChar)) {
                firstDigit = true;
            }

            if(!firstDigit) {
                continue;
            }

            if(!Character.isDigit(aChar)) {
                if('-' == aChar) {
                    isMins = true;
                }
                break;
            }

            charStr+=aChar;
        }

        char[] strChars = charStr.toCharArray();

        // 14131
        int num = 0;
        for (int i = strChars.length-1; i >= 0 ; i--) {
            int strChar = Integer.parseInt(String.valueOf(strChars[i]));

            double pow = Math.pow(10, i);
            num = (int) (num + (strChar*pow));
        }

        if(isMins) {
            num *= -1;
        }

        return num;
    }
}
