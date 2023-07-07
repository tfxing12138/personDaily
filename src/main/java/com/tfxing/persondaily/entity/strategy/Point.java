package com.tfxing.persondaily.entity.strategy;

import lombok.Data;

@Data
public class Point {

    // 行坐标
    private Integer row;

    // 列坐标
    private Integer column;

    public Point(Integer row,Integer column) {
        this.row = row;
        this.column = column;
    }
}
