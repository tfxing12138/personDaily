package com.tfxing.persondaily;

import javax.swing.*;
import java.awt.*;

public class DrawLineExample extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(10, 10, 100, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Line Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.add(new DrawLineExample());
        frame.setVisible(true);
    }
}
