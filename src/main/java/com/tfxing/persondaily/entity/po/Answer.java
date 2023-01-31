package com.tfxing.persondaily.entity.po;

import lombok.Data;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Data
public class Answer {
    
    private Long id;
    
    //父级问题id
    private Long parentQuestionId;
    
    //答案描述
    private String description;
    
    //下一问题id
    private Long nextQuestionId;
    
}
