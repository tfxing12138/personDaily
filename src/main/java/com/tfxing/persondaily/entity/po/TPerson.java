package com.tfxing.persondaily.entity.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
* 
* @TableName t_person
*/
@Data
@TableName("t_person")
public class TPerson implements Serializable {

    @ExcelIgnore
    private Long id;

    /**
    * 人员姓名
    */
    @ExcelProperty({"one","人员信息","姓名"})
    private String personName;
    /**
    * 年龄
    */
    @ExcelProperty({"one","人员信息","年龄"})
    private Integer age;
    /**
    * 性别(1:男,0:女)
    */
    @ExcelProperty({"one","人员信息","性别"})
    private Integer sex;
    /**
    * 身份证号码
    */
    @ExcelProperty({"one","身份证号码"})
    private String idCard;
    /**
    * 部门id(demo_dept.id)
    */
    @ExcelIgnore
    private Long deptId;
    /**
    * 手机号码
    */
    @ExcelProperty({"one","手机号码"})
    private String phoneNumber;
    /**
    * 人员状态（1:默认，2:暂定）
    */
    @ExcelIgnore
    private Integer personStatus;
    /**
    * 是否删除（0:未删除，1:已删除）
    */
    @ExcelIgnore
    private Integer isDel;
    /**
    * 领导id
    */
    @ExcelIgnore
    private Long leaderId;
    /**
    * 创建时间
    */
    @ExcelIgnore
    private Date createTime;
    /**
    * 更新时间
    */
    @ExcelIgnore
    private Date updateTime;


}
