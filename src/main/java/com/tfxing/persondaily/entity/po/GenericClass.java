package com.tfxing.persondaily.entity.po;

import lombok.Data;

import java.util.Date;

@Data
public class GenericClass {

    private String className;

    private Date createTime;

    public GenericClass() {}

    public GenericClass(boolean defaultValue) {
        this.className = "className";
        this.createTime = new Date();
    }

}
