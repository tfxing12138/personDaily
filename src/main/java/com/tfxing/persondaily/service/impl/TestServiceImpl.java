package com.tfxing.persondaily.service.impl;

import com.tfxing.persondaily.service.TestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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

    @Override
    public String testmq(String json) {
        rabbitTemplate.convertAndSend("directExchange","direct.key","hello world");
        return "true";
    }
}
