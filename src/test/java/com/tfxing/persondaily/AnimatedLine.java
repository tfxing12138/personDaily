package com.tfxing.persondaily;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedLine extends JPanel implements ActionListener {
    private int x1, y1, x2, y2;

    private double angle;

    public AnimatedLine() {
        x1 = 100;
        y1 = 10;
        x2 = 200;
        y2 = 10;

        angle = 30;

        Timer timer = new Timer(50, this); // 创建定时器，每隔50毫秒触发一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1*10, x2, y2*10);

        g.drawLine(x1, y1, x1, y1*10);
        g.drawLine(x2, y2, x2, y2*10);

        double length = (x2-x1)*Math.cos(Math.toRadians(angle));

        double hight = (y2 * 10 - y1) * Math.sin(Math.toRadians(angle / 2));
        double hightTop = (y2 * 10 + y1) + hight / 2;
        double hightButtom = (y2 * 10 + y1) - hight / 2;

        g.drawLine((int)(x1+length), (int) hightTop, (int)(x1+length), (int) hightButtom);


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
