package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JLabel;

public class ChatWindow extends JFrame implements ActionListener {
	private JTextField userText;

	public ChatWindow() {
		super("Chat");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("Chat here!"));
		userText = new JTextField(3);
		userText.setHorizontalAlignment(JTextField.RIGHT);
		panel.add(userText);

		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
	}

	public void setup() {
	}

	public void draw() {

	}

	public String getUserInput() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
