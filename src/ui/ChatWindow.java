package ui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import web.MindReader;
import web.Tenor;

/**
 * Represents a window for user text input and handles getting input
 * @author claire, carl
 *
 */
public class ChatWindow extends JFrame implements ActionListener, KeyListener {
	private static JTextField userText;
	private Tenor fancyTenor;
	private JOptionPane pane;
	private String input;
	private JDialog d;

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
			if (input.contains("/")) {
				checkCommands(input.substring(1));
			} else {
				GIFDisplay.memeWebURL = fancyTenor.getGIFURL(fancyTenor.search(input)); // This is where the magic happens
				GIFDisplay.memeURL = fancyTenor.getGIFFromLocal(GIFDisplay.memeWebURL, 600); // Magic also happens here as well
				
			}
		}
		else if (e.getKeyCode() == 47) {
			GIFDisplay.shelbyIndex = 1;
		}
	}
	
	private void checkCommands(String input) {
		if (input.equalsIgnoreCase("get_url")) {
			
			StringSelection stringSelection = new StringSelection(GIFDisplay.getURL());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			changeButtons();
			pane = new JOptionPane("GIF URL copied to clipboard");
			d = pane.createDialog(null, "Present for you");
			d.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
			d.setVisible(true);
			
		} else if (input.equalsIgnoreCase("refresh")) {
			
			GIFDisplay.memeURL = GIFDisplay.getURL();
			changeButtons();
			pane = new JOptionPane("GIF successfully refreshed");
			d = pane.createDialog(null, "Thanks for the refresher");
			d.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
			d.setVisible(true);
			
		} else if (input.equalsIgnoreCase("trash")) {
			
			try {
				changeButtons();
				pane = new JOptionPane("Input a TTL. Any file older than the TTL will be deleted. The TTL is in seconds.");
				d = pane.createDialog(null, "Input TTL");
				pane.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
				input = JOptionPane.showInputDialog(pane);
				int ttl = Integer.parseInt(input);
				int t1 = MindReader.folderClean("tenorJSON", ttl);
				int t2 = MindReader.folderClean("images", ttl);
				changeButtons();
				pane = new JOptionPane("Successfullly deleted " + t1 + " JSON files and " + t2 + " GIF files");
				d = pane.createDialog(null, "Success");
				d.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
				d.setVisible(true);
			} catch (NumberFormatException e1) {
				changeButtons();
				System.out.println("TTL must be an integer");
				pane = new JOptionPane("TTL must be an integer");
				d = pane.createDialog(null, "REEEEEE");
				d.setLocation((int)(Math.random()*1200),(int)(Math.random()*600));
				d.setVisible(true);
			} catch (SecurityException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		userText.setText("");
		GIFDisplay.shelbyIndex = 0;
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
	
	private void changeButtons() {
		int i = (int)(10*Math.random());
		if (1 == i) {
			UIManager.put("OptionPane.cancelButtonText", "nope");
			UIManager.put("OptionPane.okButtonText", "yup");
		} else if (2 == i) {
			UIManager.put("OptionPane.cancelButtonText", "nah");
			UIManager.put("OptionPane.okButtonText", "yeah");
		} else if (3 == i) {
			UIManager.put("OptionPane.cancelButtonText", "plz no");
			UIManager.put("OptionPane.okButtonText", "sure");
		} else if (4 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Idiotic");
			UIManager.put("OptionPane.okButtonText", "Dank");
		} else if (5 == i) {
			UIManager.put("OptionPane.cancelButtonText", "absolutely not");
			UIManager.put("OptionPane.okButtonText", "absolutely");
		} else if (6 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Nuo");
			UIManager.put("OptionPane.okButtonText", "Yeet");
		} else if (7 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Why");
			UIManager.put("OptionPane.okButtonText", "Why not");
		} else if (7 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Spooky");
			UIManager.put("OptionPane.okButtonText", "Fancy");
		} else if (8 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Not cool");
			UIManager.put("OptionPane.okButtonText", "Guavy");
		} else if (9 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Not fancy");
			UIManager.put("OptionPane.okButtonText", "Extremely Fancy");
		} else if (10 == i) {
			UIManager.put("OptionPane.cancelButtonText", "Terrifying");
			UIManager.put("OptionPane.okButtonText", "Amazing");
		} else {
			UIManager.put("OptionPane.cancelButtonText", "Cancel");
			UIManager.put("OptionPane.okButtonText", "Yoink");
		}
	}

}
