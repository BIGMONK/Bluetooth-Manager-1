package com.mageddo.ubuntu.bluetoothmanager.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CustomJOP extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2409287014497253856L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JTextArea area = new JTextArea("error"); 
		JButton buttonSendDeveloper = new JButton("Enviar para o suporte");
		JButton buttonCopyError = new JButton("Copiar");
		panel.add(area, BorderLayout.NORTH);

		panel.add(buttonCopyError, BorderLayout.EAST);
		panel.add(buttonSendDeveloper, BorderLayout.WEST);

		JOptionPane.showMessageDialog(null, panel, "Mensagem de erro", JOptionPane.ERROR_MESSAGE);

	}

}
