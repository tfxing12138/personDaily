package com.tfxing.persondaily.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.Backoff;

import java.io.UnsupportedEncodingException;


/**
 * @author :tanfuxing
 * @date :2023/2/6
 * @description :
 */
@Configuration
public class RabbitSimpleConfig {
    
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("119.29.187.18",5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("testhost");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
    
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange("directExchange");
    }
    
    @Bean
    public Queue queue() {
        return new Queue("testQueue",true);
    }
    
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with("direct.key");
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}
