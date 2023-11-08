package com.tfxing.persondaily;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedLine extends JPanel implements ActionListener {
    private int x1, y1, x2, y2;

    public AnimatedLine() {
        x1 = 100;
        y1 = 10;
        x2 = 200;
        y2 = 10;
        Timer timer = new Timer(50, this); // 创建定时器，每隔50毫秒触发一次
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(x1, y1, x2, y2);
    }

    public void actionPerformed(ActionEvent e) {
        // 更新线条的位置
        y1++;
        y2++;
        if (x2 > getWidth() || y2 > getHeight()) {
            x1 = 10;
            y1 = 10;
            x2 = 100;
            y2 = 10;
        }
        repaint(); // 重绘线条
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Animated Line");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new AnimatedLine());
        frame.setVisible(true);

        frame.setBounds(400,300,400, 300);
    }
}
