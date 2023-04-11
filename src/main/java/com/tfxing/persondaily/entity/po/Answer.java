package com.tfxing.persondaily.entity.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author :tanfuxing
 * @date :2023/1/31
 * @description :
 */
@Data
public class Answer {

    @ExcelIgnore
    private Long id;

    //父级问题id
    @ExcelIgnore
    private Long parentQuestionId;

    //答案描述
    @ExcelProperty("答案描述")
    @ColumnWidth(25)
    private String description;

    @ExcelIgnore
    //下一问题id
    private Long nextQuestionId;
    
}
