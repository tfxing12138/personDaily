package com.tfxing.persondaily.entity.po;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* 部门表
* @TableName t_dept
*/
@Data
@TableName("t_dept")
public class TDept implements Serializable {

    private Long id;
    /**
    * 部门编号
    */
    @ExcelProperty({"部门信息","部门编号"})
    private String deptCode;
    /**
    * 部门名称
    */
    @ExcelProperty({"部门信息","部门名称"})
    private String deptName;
    /**
    * 是否删除（0：否，1：是）
    */
    private Integer isDel;
    /**
    * 创建时间
    */
    @ExcelProperty({"创建时间"})
    private Date createTime;



}
