package com.mageddo.ubuntu.bluetoothmanager.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TestJPopup {

    protected void initUI() {
        JFrame frame = new JFrame(TestJPopup.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel leftLabel = new JLabel("Left");
        frame.add(leftLabel, BorderLayout.WEST);
        final JButton buttonToHit = new JButton("Hit me");
        buttonToHit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(buttonToHit, "You hit the button successfully");
            }
        });
        frame.add(buttonToHit);
        frame.setSize(500, 400);
        frame.setVisible(true);
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JLabel("<html>A Custom<br>component<br>made to<br> simulate <br>your custom component</html>"),
                BorderLayout.NORTH);
        JTextField textfield = new JTextField(30);
        popupMenu.add(textfield);
        popupMenu.setFocusable(false);
        popupMenu.setVisible(true);
        popupMenu.show(leftLabel, 20, 20);
        // Let's force the focus to be in a component in the popupMenu
        textfield.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new TestJPopup().initUI();
            }
        });
    }
}