package com.tfxing.threeD.entity;

import lombok.Data;

import java.util.List;

@Data
public class Surface {

    private List<Line> lines;

    public Surface(List<Line> lines) {
        this.lines = lines;
    }
}
