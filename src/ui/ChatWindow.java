package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JLabel;

import web.Tenor;

/**
 * Represents a window for user text input and handles getting input
 * @author claire, carl
 *
 */
public class ChatWindow extends JFrame implements ActionListener, KeyListener {
	private static JTextField userText;
	private Tenor fancyTenor;

	public ChatWindow() {
		super("Chat");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("Chat here!"));
		userText = new JTextField(3);
		userText.setHorizontalAlignment(JTextField.RIGHT);
		userText.addKeyListener(this);
		panel.add(userText);

		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		fancyTenor = new Tenor();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) { // Checks for 'return' key pressed
			String input = userText.getText().replaceAll(" ", "_");
			if (input.equalsIgnoreCase("get_url")) {
				userText.setText(GIFDisplay.getURL());
			} else if (input.equalsIgnoreCase("refresh")) {
				GIFDisplay.memeURL = GIFDisplay.getURL();
			} else
				GIFDisplay.memeURL = fancyTenor.getGIFFromLocal(fancyTenor.getGIFURL(fancyTenor.search(input)), 600); // This is where the magic happens
		}
	}

	public static String getUserInput() {
		return userText.getText();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
