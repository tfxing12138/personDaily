package com.tfxing.persondaily.aop;

import com.tfxing.persondaily.dao.AnswerMapper;
import com.tfxing.persondaily.entity.po.Answer;
import com.tfxing.persondaily.utils.SpringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class ConditionAspectj {

    @Pointcut("@annotation(com.tfxing.persondaily.entity.annotation.Listen)")
    public void pointcut() {}

    @Around("pointcut()")
    @Transactional(rollbackFor = Exception.class)
    public void conditionAspectj(ProceedingJoinPoint point) throws Throwable {

        savePerson();

        try {
            point.proceed();
        } catch (Throwable e) {
            throw e;
        }
    }

    private void savePerson() {
        AnswerMapper answerMapper = SpringUtils.getBean(AnswerMapper.class);
        Answer answer = new Answer();
        answer.setDescription("test20230309");
        answerMapper.insert(answer);
    }

}
