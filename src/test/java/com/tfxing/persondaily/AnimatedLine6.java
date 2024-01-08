package com.tfxing.persondaily;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class AnimatedLine6 extends JPanel implements ActionListener {
    private java.util.List<Point> pointList;

    private double angle;

    public AnimatedLine6(java.util.List<Point> pointList) {
        this.pointList = pointList;

        Timer timer = new Timer(50, this); // 创建定时器，每隔50毫秒触发一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawLine(x1, y1, x2, y2); // (100,10) (200,10)

        Map<Integer, Point> pointMap = pointList.stream().collect(Collectors.toMap(item -> item.getP(), value -> value));

        for (int i = 0; i < pointList.size(); i++) {
            Point pointStart = pointMap.get(i);
            Point pointEnd = null;
            if(i == pointList.size() - 1) {
                pointEnd = pointMap.get(0);
            } else {
                pointEnd = pointMap.get(i+1);
            }

            g.drawLine(pointStart.getX(),pointStart.getY(),pointEnd.getX(),pointEnd.getY());
        }

    }

    public void actionPerformed(ActionEvent e) {
        // 更新角度大小
        if(angle < 90) {
            angle++;
        } else if(angle >= 90) {
            angle = 0;
        }

        repaint(); // 重绘线条
    }

    public static void main(String[] args) {
        Point a = new Point(100, 100, 0);
        Point b = new Point(50, 50, 1);
        Point c = new Point(200, 200, 2);
        Point d = new Point(100, 200, 3);
        Point e = new Point(100, 200, 4);
        java.util.List<Point> list = Arrays.asList(a,b,c,d,e);

        JFrame frame = new JFrame("Animated Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AnimatedLine6(list));
        frame.setVisible(true);

        frame.setBounds(500,500,400, 400);
    }

    @Data
    static class Point {
        private int x,y,p;

        public Point(int x, int y, int p) {
            this.x = x;
            this.y = y;
            this.p = p;
        }
    }
}
