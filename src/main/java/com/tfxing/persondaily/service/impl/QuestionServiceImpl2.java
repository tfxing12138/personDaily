package com.tfxing.persondaily.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tfxing.persondaily.dao.AnswerMapper;
import com.tfxing.persondaily.dao.QuestionMapper;
import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.entity.po.Question;
import com.tfxing.persondaily.service.QuestionService;
import com.tfxing.persondaily.utils.CommonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Service
public class QuestionServiceImpl2 implements QuestionService {
    
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private AnswerMapper answerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addQuestion(Question question) {
        questionMapper.insert(question);
        
        //更新选项nextQuestionId
        updateAnswerNextQuestionId(question);
        
        return true;
    }

    /**
     * 更新选项nextQuestionId
     * @param question
     */
    private void updateAnswerNextQuestionId(Question question) {
        if(CommonUtils.isExist(question.getParentAnswerId())) {
            answerMapper.update(null,new LambdaUpdateWrapper<Answer>()
                    .eq(Answer::getId, question.getParentAnswerId())
                    .set(Answer::getNextQuestionId, question.getId()));
        }
    }

    @Override
    public Boolean addAnswer(Answer answer) {
        return answerMapper.insert(answer) > 0;
    }

    @Override
    public Boolean testEqNull() {
        return null;
    }
}
