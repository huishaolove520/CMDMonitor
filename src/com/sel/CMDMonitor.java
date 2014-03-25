package com.sel;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
			String input = ((JTextField) e.getSource()).getText().trim()
					.toLowerCase();
			String[] args = input.split(" ");
			if (args[0].matches("^.+\\.((exe)|(bat)|(txt)|(html))$")) {
				CMDCommand("cmd /c " + input);
			} else {
				switch (args[0]) {
				case "ls":
					CMDCommand("cmd /c dir");
					break;

				case "ps":
					CMDCommand("cmd /c tasklist");
					break;

				case "clean":
					CLEANCommand();
					break;

				case "create":
					CMDCommand("cmd /c echo>" + args[1]);
					break;

				case "delete":
					CMDCommand("cmd /c del " + args[1]);
					break;

				case "type":
				case "copy":
				case "mkdir":
				case "rmdir":
				case "move":
					CMDCommand("cmd /c " + input);
					break;

				case "exit":
					EXITCommand();
					break;

				case "help":
					HELPCommand();
					break;

				default:
					showWrongMessage();
					HELPCommand();
					break;
				}

			}
		}

	}

	private void CMDCommand(String command) {
		try {
			mainWin.setText(mainWin.getText() + markWin.getText()
					+ inputWin.getText() + "\n\n");
			inputWin.setText("");
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			String s;
			while ((s = bf.readLine()) != null) {
				mainWin.setText(mainWin.getText() + s + '\n');
			}
			process.destroy();
			mainWin.setCaretPosition(mainWin.getDocument().getLength());
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
		mainWin.setText(mainWin.getText() + markWin.getText()
				+ inputWin.getText() + "\n\n");
		String wrongMessage = "No this command !\n\n";
		mainWin.setText(mainWin.getText() + '\n' + wrongMessage);
		inputWin.setText("");
	}

	private void HELPCommand() {
		String helpInfo = "Please input the command below:\n\n"
				+ "ls : list the files and directories\n\n"
				+ "ps : show the running process\n\n"
				+ "create FileName : create a file\n\n"
				+ "delete FileName : delete a file\n\n"
				+ "type FileName : show the content of a file\n\n"
				+ "copy source destination : copy a file\n\n"
				+ "mkdir directoryName : create a directory\n\n"
				+ "rmdir directoryName : delete a directory\n\n"
				+ "clean : clean the content\n\n"
				+ "exit : exit the program\n\n"
				+ "help : the help information\n\n";
		mainWin.setText(mainWin.getText() + '\n' + helpInfo);
		inputWin.setText("");
	}

}
