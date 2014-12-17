package com.mageddo.ubuntu.bluetoothmanager;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public final class Main extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3911427675950870001L;
	private static final String EMAIL_SUPORTE = "edigitalb@gmail.com";	

	private JButton buttonEnable;
	private JButton buttonDisable;
	private JButton buttonAbout;
	private BoxErro boxErro;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main m = new Main();
		m.init();
	}

	/**
	 * Set defaults configurations 
	 */
	private void init() {
		initializeVariables();
		run();
		setResizable(false);
		setLocationRelativeTo(null);
		setSize(new Dimension(350, 70));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	/**
	 * Instancia as variáveis padrão
	 */
	private void initializeVariables() {
		buttonAbout = new JButton("Sobre...");
		buttonEnable = new JButton("Ligar");
		buttonDisable = new JButton("Desligar");
		
	}

	private void run() {
		setLayout(new GridLayout());
		setTitle("Bluetooth Manager - Mageddo");
		add(buttonEnable);
		add(buttonDisable);
		add(buttonAbout);

		/**
		 * Informações do desenvolvedor
		 */
		buttonAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Main.this, String.format("Copyright 2014 - Mageddo \nContato: %s\nwww.mageddo.com", EMAIL_SUPORTE));
				
			}
		});
		
		/**
		 * Habilita o bluetooth
		 */
		buttonEnable.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				TODO EFS chama o cmd e salva um arquivo com a mensagem passada
				if(
					command("bash", "-c", "rfkill unblock bluetooth")
					&&
					command("bash", "-c", "gsettings set com.canonical.indicator.bluetooth visible true")
				){
					buttonEnable.setEnabled(false);
					buttonDisable.setEnabled(true);
				}				
			}
		});
		
		/**
		 * Desabilita o bluetooth
		 */
		buttonDisable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				TODO EFS chama o cmd e salva um arquivo com a mensagem passada
				if(
					command("bash", "-c", "rfkill block bluetooth")
					&&
					command("bash", "-c", "gsettings set com.canonical.indicator.bluetooth visible false")
				){

					buttonDisable.setEnabled(false);
					buttonEnable.setEnabled(true);
				}
			}
		});
		
		
	}
	
	
	/**
	 * Executa os comandos passados e caso de erro retorna false e chama o popup com o erro
	 * @param command
	 * @return
	 */
	private boolean command(String ... command) {
		try {
//			TODO EFS EXAMPLE
//			Process pc = new ProcessBuilder("cmd", "/c","echo logs.log > d:/logs.log").start();
			Process pc = new ProcessBuilder(command).start();
			String erro = getMessage(pc.getErrorStream());
			
			if(erro != null)
				throw new IOException(erro);

			return true;
		} catch (IOException e1) {
			erro("Não foi possível desativar o bluetooth", e1);
			return false;
		}
	}
	
	/**
	 * Retorna o texto dentro do stream se tiver
	 * @param in
	 * @return
	 */
	public String getMessage(InputStream in){
		Scanner scanner = new Scanner(in);
		if(!scanner.hasNext())
			return null;
		
		StringBuilder msgErro = new StringBuilder();
		while(scanner.hasNext()){
			msgErro.append(scanner.nextLine());
			msgErro.append("\n");
		}
		return msgErro.toString();
	}
	
	public void erro(String msg, Exception e){
		if(boxErro == null)
			boxErro = new BoxErro().init();
		boxErro.load(msg, e);
		boxErro.setVisible(true);
	}

	private final class BoxErro extends JFrame{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4599004219258850L;

		private JTextArea textError;
		private JButton buttonCopyError;
		private JButton buttonSendToSupport;
		private JScrollPane scrollText;

		public BoxErro init() {
			initializeVariables();
			run();
			setTitle("Erro encontrado");
			setResizable(false);
			setLocationRelativeTo(null);
			setSize(new Dimension(300, 300));
			setDefaultCloseOperation(HIDE_ON_CLOSE);
			return this;
		}

		private void initializeVariables() {
			textError = new JTextArea();
			textError.setEditable(false);
			buttonCopyError = new JButton("copiar erro");
			buttonSendToSupport = new JButton("enviar automaticamente");
		}

		public void load(String msg, Exception e) {
			StringBuilder str = new StringBuilder();
			str.append(msg);
			str.append("\nDetalhes do erro:\n");

			for(Throwable t = e; t != null; t = t.getCause()){
				str.append(t.getMessage());
				str.append("\n");
//				TODO EFS ver se tem a necessidade de colocar
				for(StackTraceElement se: t.getStackTrace()){
					str.append(se.toString());
					str.append("\n");
				}
				str.append("\n");
			}
			textError.setText(str.toString());
//			TODO EFS ver porque nao faz scroll
//			System.out.println(scrollText.getVerticalScrollBar().getMinimum());
//			scrollText.getVerticalScrollBar().setValue(scrollText.getVerticalScrollBar().getMaximum());

		}
		
		private void run() {
			setLayout(new BorderLayout());
			JPanel botoes = new JPanel();
			botoes.setLayout(new BorderLayout());
			scrollText = new JScrollPane(textError);
			JTextPane label = new JTextPane();
			label.setContentType("text/html"); // let the text pane know this is what you want
			label.setEditable(false); // as before
			label.setBackground(null); // this is the same as a JLabel
			label.setBorder(null); //
			label.setCursor(new Cursor(Cursor.TEXT_CURSOR));
//			String pattern = 
//				"<html><div style='padding:3px;'>Não conseguimos ativar/desativar o bluetooth na sua máquina,"+
//				"nos ajude a resolver este problema apenas clicando em <b style='color:#000'>enviar automaticamente</b> ou em <b style='color:#000'>copiar erro</b> e enviando texto copiado"+
//				" para <b style='color:#000'>%s<b></div><h3>Erro:</h3></html>";
			String pattern = 
					"<html><div style='padding:3px;'>Não conseguimos ativar/desativar o bluetooth na sua máquina,"+
							"nos ajude a resolver este problema apenas clicando em copiar erro</b> e enviando texto copiado"+
							" para <b style='color:#000'>%s</b> com o assunto 'Erro Bluetooth Manager'</div><h3>Erro:</h3></html>";
			
			label.setText(
				String.format(pattern, EMAIL_SUPORTE)
			);
			add(label, BorderLayout.NORTH);
			add(scrollText, BorderLayout.CENTER);
			botoes.add(buttonCopyError, BorderLayout.EAST);
//			TODO EFS adicionar depois quando ativar a funcionalidade
//			botoes.add(buttonSendToSupport, BorderLayout.WEST);
			add(botoes, BorderLayout.SOUTH);
			
			/**
			 * evento que copia o erro
			 */
			buttonCopyError.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO EFS copiar erro
					StringSelection selection = new StringSelection(textError.getText());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				    clipboard.setContents(selection, selection);
				    JOptionPane.showMessageDialog(
			    		BoxErro.this,
			    		"Copiado para area de transferência, cole no seu email usando o botão direito do mouse > colar",
			    		"Mensagem",
			    		JOptionPane.INFORMATION_MESSAGE
		    		);
				}
			});
			
			/**
			 * Evento que envia o erro para o desenvolvedor
			 */
			buttonSendToSupport.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO EFS enviar para o suporte
					JOptionPane.showMessageDialog(
						BoxErro.this,
						"Obrigado pelo seu feedback, tentaremos resolver o problema o mais rápido possível",
						"Mensagem",
						JOptionPane.INFORMATION_MESSAGE
					);
				}
			});
		}
	}
}