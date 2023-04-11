package com.tfxing.persondaily.entity.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Data
public class Question {

    @ExcelIgnore
    private Long id;
    
    //问题描述
    @ExcelProperty("问题描述")
    @ColumnWidth(25)
    private String description;
    
    //上级答案id
    @TableField(exist = false)
    @ExcelIgnore
    private Long parentAnswerId;
    
}
