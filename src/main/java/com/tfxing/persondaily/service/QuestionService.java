package com.tfxing.persondaily.service;

import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.entity.po.Person;
import com.tfxing.persondaily.entity.po.Question;

import java.util.List;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
public interface QuestionService {
    
    Boolean addQuestion(Question question);

    Boolean addAnswer(Answer answer);

    Boolean testEqNull();

}
