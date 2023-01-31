package com.tfxing.persondaily.service;

import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.entity.po.Question;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
public interface QuestionService {
    
    Boolean addQuestion(Question question);

    Boolean addAnswer(Answer answer);
}
