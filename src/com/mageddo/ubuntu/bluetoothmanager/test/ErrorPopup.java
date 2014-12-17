package com.mageddo.ubuntu.bluetoothmanager.test;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErrorPopup extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4599004219258850L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ErrorPopup().init();

	}

	private JTextArea textError;
	private JButton buttonCopyError;
	private JButton buttonSendToSupport;

	private void init() {
		initializeVariables();
		run();
		setTitle("Erro encontrado");
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(new Dimension(250, 150));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	private void initializeVariables() {
		textError = new JTextArea();
		textError.setEditable(false);
		buttonCopyError = new JButton("copiar erro");
		buttonSendToSupport = new JButton("enviar para o suporte");
	}

	private void run() {
		setLayout(new BorderLayout());
		JPanel botoes = new JPanel();
		botoes.setLayout(new BorderLayout());
		add(new JScrollPane(textError), BorderLayout.CENTER);
		botoes.add(buttonCopyError, BorderLayout.EAST);
		botoes.add(buttonSendToSupport, BorderLayout.WEST);
		add(botoes, BorderLayout.SOUTH);
	}

}
