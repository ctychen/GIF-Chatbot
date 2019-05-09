package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JLabel;

import web.Tenor;

public class ChatWindow extends JFrame implements ActionListener, KeyListener {
	private JTextField userText;
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

	public void setup() {
	}

	public void draw() {

	}
	
	public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 10) { //Checks for 'return' key pressed
        	GIFDisplay.memeURL = fancyTenor.getGIFURL(fancyTenor.search(userText.getText())); //This is where the magic happens
        }
    }

	public String getUserInput() {
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
