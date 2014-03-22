package com.sel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class CMDMonitor extends JFrame {

	private JTextPane mainWin;
	private JTextPane inputWin;

	public CMDMonitor() {
		mainWin = new JTextPane();
		mainWin.setBackground(Color.black);
		mainWin.setEditable(false);
		mainWin.setForeground(Color.white);
		add(mainWin, BorderLayout.CENTER);
		inputWin = new JTextPane();
		inputWin.setBackground(Color.black);
		inputWin.setForeground(Color.white);
        inputWin.setText("$Shell>");
		inputWin.addKeyListener(new InputLinstener());
		add(inputWin, BorderLayout.SOUTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class InputLinstener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			mainWin.setText(e.getKeyText(e.getKeyCode()));
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		

	}

}
