package org.example;

import javax.swing.*;

public class Main {

    public static JFrame frame;

    public static void main(String[] args) {

        frame = new JFrame("Zaloguj sie");
        frame.setContentPane(new Login().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //frame.setBounds(100, 100, 450, 300);
        frame.setBounds(100, 100, 800, 600);
        frame.setVisible(true);
    }
}