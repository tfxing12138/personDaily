package com.tfxing.persondaily.service;

import com.tfxing.persondaily.entity.po.Question;

/**
 * @author :tanfuxing
 * @date :2023/2/7
 * @description :
 */
public interface TestService {
    String testmq(String json);

    void testTran();

    void saveQuestion(Question question);

    void testTran2();

    String testTran3(Integer num);

    int lengthOfLastWord(String str);

    int myAtoi(String str);

    void testTransfer();
}
