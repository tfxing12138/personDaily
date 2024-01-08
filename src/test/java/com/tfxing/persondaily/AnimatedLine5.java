package com.tfxing.persondaily;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AnimatedLine5 extends JPanel implements ActionListener {

    private java.util.List<Line> lineList;
    public AnimatedLine5(java.util.List<Line> lineList) {
        this.lineList = lineList;
        Timer timer = new Timer(50, this); // 创建定时器，每隔50毫秒触发一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawLine();
        for (Line line : lineList) {
            drawLine(g, line);
        }

    }

    private void drawLine(Graphics g, Line line) {
        Point pointA = line.getPointA();
        Point pointB = line.getPointB();
        g.drawLine(pointA.getX(), pointA.getY(),pointB.getX(), pointB.getY());
    }


    public void actionPerformed(ActionEvent e) {

        repaint(); // 重绘线条
    }

    public static void main(String[] args) {
        java.util.List<Line> lineList = new ArrayList<>();
        Line lineBottom = new Line(new Point(100, 100), new Point(200, 100));
        Line lineLeft = new Line(new Point(100, 100), new Point(100, 200));
        Line lineRight = new Line(new Point(200, 100), new Point(200, 200));
        Line lineTop = new Line(new Point(100, 200), new Point(200, 200));

        lineList.add(lineBottom);
        lineList.add(lineLeft);
        lineList.add(lineRight);
        lineList.add(lineTop);


        lineList.add(new Line(299, 301, 301, 301));
        lineList.add(new Line(301, 301, 301, 299));
        lineList.add(new Line(301, 299, 299, 299));
        lineList.add(new Line(299, 299, 299, 301));
        lineList.add(new Line(299, 301, 299, 399));
        lineList.add(new Line(301, 301, 301, 399));
        lineList.add(new Line(301, 299, 301, 199));
        lineList.add(new Line(299, 299, 299, 199));
        lineList.add(new Line(299, 399, 301, 399));
        lineList.add(new Line(299, 399, 299, 299));
        lineList.add(new Line(301, 399, 301, 299));
        lineList.add(new Line(301, 399, 299, 399));

        JFrame frame = new JFrame("Animated Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AnimatedLine5(lineList));
        frame.setVisible(true);

        frame.setBounds(500,500,400, 400);
    }

    @Data
    static class Point {
        private int x,y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Data
    static class Line {
        private Point pointA;

        private Point pointB;

        public Line(Point pointA, Point pointB) {
            this.pointA = pointA;
            this.pointB = pointB;
        }

        public Line(int x,int y,int x2,int y2) {
            Point point = new Point(x, y);
            Point point1 = new Point(x2, y2);
            this.pointA = point;
            this.pointB = point1;
        }
    }
}

