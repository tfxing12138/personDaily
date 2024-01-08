package com.tfxing.threeD;

import com.tfxing.threeD.entity.Line;
import com.tfxing.threeD.entity.Point;
import com.tfxing.threeD.entity.Surface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JPanel implements ActionListener {
    private List<Surface> surfaceList;

    private double angle;

    public Main(List<Surface> surfaceList) {
        this.surfaceList = surfaceList;

        Timer timer = new Timer(50, this); // 创建定时器，每隔50毫秒触发一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Surface surface : this.surfaceList) {
            for (Line line : surface.getLines()) {
                Point a = line.getA();
                Point b = line.getB();
                g.drawLine(a.getX(),a.getY(),b.getX(),b.getY());
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint(); // 重绘线条
    }

    /**
     * 点 -> 线, 线->面, 面->体
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animated Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Main(getSurfaces()));
        frame.setVisible(true);

        frame.setBounds(500,500,400, 400);
    }

    private static List<Surface> getSurfaces() {
        List<Surface> surfaces = new ArrayList<>();


        Surface surface = createSurface();
        Surface surface1 = createSurface1();


        surfaces.add(surface);

        return surfaces;
    }

    private static Surface createSurface() {
        Point a1 = new Point(0, 0, 40);
        Point a2 = new Point(20, 0, 40);
        Line top = new Line(a1,a2);

        Point b1 = new Point(0, 0, 40);
        Point b2 = new Point(0, 20, 40);
        Line left = new Line(b1,b2);

        Point c1 = new Point(0, 20, 40);
        Point c2 = new Point(20, 20, 40);
        Line bottom = new Line(c1,c2);

        Point d1 = new Point(20, 20, 40);
        Point d2 = new Point(20, 0, 40);
        Line right = new Line(d1,d2);

        Surface surface = new Surface(Arrays.asList(top,left,bottom,right));

        return surface;
    }


    private static Surface createSurface1() {
        Point a1 = new Point(0, 0, 40);
        Point a2 = new Point(20, 0, 40);
        Line top = new Line(a1,a2);

        Point b1 = new Point(0, 0, 40);
        Point b2 = new Point(0, 20, 40);
        Line left = new Line(b1,b2);

        Point c1 = new Point(0, 20, 40);
        Point c2 = new Point(20, 20, 40);
        Line bottom = new Line(c1,c2);

        Point d1 = new Point(20, 20, 40);
        Point d2 = new Point(20, 0, 40);
        Line right = new Line(d1,d2);

        Surface surface = new Surface(Arrays.asList(top,left,bottom,right));

        return surface;
    }
}
