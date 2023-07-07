package com.tfxing.persondaily.entity.strategy;

import lombok.Data;

@Data
public class ExcelMergeIndex {
    // 左上角坐标
    private Point topLeftPoint;

    // 右下角坐标
    private Point bottomRightPoint;

    /**
     * 构造期
     * @param tlr 左上单元格的行坐标
     * @param tlc 左上单元格的列坐标
     * @param brr 右下单元格的行坐标
     * @param brc 右下单元格的列坐标
     */
    public ExcelMergeIndex(Integer tlr,Integer tlc,Integer brr,Integer brc) {
        this.topLeftPoint = new Point(tlr, tlc);
        this.bottomRightPoint = new Point(brr,brc);
    }

    /**
     * 构造器
     * @param topLeftPoint 左上单元格位置
     * @param bottomRightPoint 右下单元格位置
     */
    public ExcelMergeIndex(Point topLeftPoint, Point bottomRightPoint) {
        this.topLeftPoint = topLeftPoint;
        this.bottomRightPoint = bottomRightPoint;
    }
}
