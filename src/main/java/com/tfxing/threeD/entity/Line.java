package com.tfxing.threeD.entity;

import lombok.Data;

@Data
public class Line {

    private Point a;

    private Point b;

    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }
}
