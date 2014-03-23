package com.sel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class CMDMonitor extends JFrame {

	private JTextPane mainWin;
	private JTextField inputWin;
	private JTextPane markWin;
	private JPanel botPanel;
	private JScrollPane mainScollPane;

	public CMDMonitor() {
		mainWin = new JTextPane();
		mainWin.setBackground(Color.black);
		mainWin.setEditable(false);
		mainWin.setForeground(Color.white);
		mainScollPane = new JScrollPane(mainWin);
		mainScollPane.setBorder(null);
		add(mainScollPane, BorderLayout.CENTER);
		markWin = new JTextPane();
		markWin.setBackground(Color.black);
		markWin.setForeground(Color.white);
		markWin.setText("$Shell>");
		markWin.setEditable(false);
		markWin.setSize(20, 10);
		inputWin = new JTextField();
		inputWin.setBorder(null);
		inputWin.setBackground(Color.black);
		inputWin.setForeground(Color.white);
		inputWin.setCaretColor(Color.white);
		botPanel = new JPanel(new BorderLayout());
		botPanel.add(markWin, BorderLayout.WEST);
		botPanel.add(inputWin, BorderLayout.CENTER);
		inputWin.addActionListener(new InputLinstener());
		add(botPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		setSize(600, 400);
		setLocation(300, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputWin.requestFocus();
	}

	class InputLinstener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String input = ((JTextField) e.getSource()).getText().trim();

			switch (input) {
			case "ls":
			case "LS":
                CMDCommand("cmd /c dir");
				break;

			case "ps":
			case "PS":
				CMDCommand("cmd /c tasklist");
				break;
			
			case "clean":
			case "CLEAN":
				CLEANCommand();
				break;
				
			case "exit":
			case "EXIT":
				EXITCommand();
				break;
				
			case "help":
			case "HELP":
				HELPCommand();
				break;
				
			default:
				showWrongMessage();
				HELPCommand();
				break;
			}

		}

	}
	
	private void CMDCommand(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader bf = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while((s = bf.readLine()) != null) {
				mainWin.setText(mainWin.getText() + s + '\n');
			}
			mainWin.setText(mainWin.getText() + markWin.getText() + inputWin.getText() + '\n');
			mainWin.setCaretPosition(mainWin.getDocument().getLength());
			inputWin.setText("");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	private void CLEANCommand() {
		mainWin.setText("");
		inputWin.setText("");
	}
	
	private void EXITCommand() {
		System.exit(0);
	}
	
	private void showWrongMessage() {
		String wrongMessage = "No this command!\n\n";
		mainWin.setText(mainWin.getText() + '\n' + wrongMessage);
		inputWin.setText("");
	}
	
	private void HELPCommand() {
		String helpInfo = "Please input the command below:\n\n" 
				        + "ls : list the files and directories\n\n"
				        + "ps : show the running process\n\n"
				        + "clean : clean the content\n\n"
				        + "exit : exit the program\n\n"
				        + "help : the help information\n";
		mainWin.setText(mainWin.getText() + '\n' + helpInfo);
		inputWin.setText("");
	}

}

