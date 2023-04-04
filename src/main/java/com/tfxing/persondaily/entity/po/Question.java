package com.tfxing.persondaily.entity.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Data
public class Question {
    
    private Long id;
    
    //问题描述
    @ExcelProperty("描述")
    private String description;
    
    //上级答案id
    @TableField(exist = false)
    private Long parentAnswerId;
    
}
