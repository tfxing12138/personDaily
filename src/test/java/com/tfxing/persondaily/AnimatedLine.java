package com.tfxing.persondaily;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedLine extends JPanel implements ActionListener {
    private int x1, y1, x2, y2, x3, y3, x4, y4;

    private double angle;

    public AnimatedLine() {
        x1 = 100;
        y1 = 10;
        x2 = 200;
        y2 = 10;

        x3 = 100;
        y3 = 100;
        x4 = 200;
        y4 = 100;

        angle = 30;

        Timer timer = new Timer(50, this); // 创建定时器，每隔50毫秒触发一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(x1, y1, x2, y2); // (100,10) (200,10)
        g.drawLine(x3, y3, x4, y4); // (100,100) (200, 100)

        g.drawLine(x1, y1, x1, y3); // (100,10) (100,100)
        g.drawLine(x2, y2, x2, y4); // (200,10) (200, 100)

        double length = (x2-x1)*Math.cos(Math.toRadians(angle));

        double distance = (x2 - x1) * Math.sin(Math.toRadians(angle / 2)) * 2;

        double hight = (100 / (100 + distance)) * (y3-y1);

        double hightTop = (y3 / 2 + y1) + hight / 2;
        double hightButtom = (y3 / 2 + y1) - hight / 2;

        g.drawLine((int)(x1+length), (int) hightTop, (int)(x1+length), (int) hightButtom);
        g.drawLine(x3,y3,(int)(x1+length), (int) hightTop);
        g.drawLine(x1,y1, (int)(x1+length), (int) hightButtom);
//        g.drawLine((int)(x1+length), y1, (int)(x1+length), y3);


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
        JFrame frame = new JFrame("Animated Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AnimatedLine());
        frame.setVisible(true);

        frame.setBounds(500,500,400, 400);
    }
}
