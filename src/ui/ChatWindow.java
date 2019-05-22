package ui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.IOException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;

import analytics.SpeechToText;
import web.JSONTools;
import web.MindReader;
import web.Tenor;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Represents a window for user text input and handles getting input
 * 
 * @author claire, carl
 *
 */
public class ChatWindow extends JFrame implements ActionListener, KeyListener {
	private static JTextField userText;
	private Tenor fancyTenor;
	private JOptionPane pane;
	private String input;
	private JDialog d;
	private JButton high, medium, low, off;
	private SpeechToText ear = new SpeechToText();

	public ChatWindow() {
		super("Chat");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("Chat here, or type /help to see the list of commands"));
		userText = new JTextField(3);
		userText.setHorizontalAlignment(JTextField.RIGHT);
		userText.addKeyListener(this);
		panel.add(userText);

		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		fancyTenor = new Tenor();
	}

	/**
	 * If the user presses "enter", the input will get parsed
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 10) { // Checks for 'return' key pressed
			String input = userText.getText().replaceAll(" ", "_");
			if (input.equalsIgnoreCase("osman"))
				input = "thumb";
			if (input.equalsIgnoreCase("carl"))
				input = "fancy";
			if (input.contains("/")) {
				checkCommands(input.substring(1));
			} else {
				String temp = fancyTenor.getGIFURL(fancyTenor.search(input)); // This is where the magic happens
				if (temp != null) {
					GIFDisplay.memeWebURL = temp;
					GIFDisplay.memeURL = fancyTenor.getGIFFromLocal(GIFDisplay.memeWebURL, 600); // Magic also happens
																									// here as well
					GIFDisplay.shelbyIndex = 2;
				} else {
					changeButtons();
					pane = new JOptionPane("No results");
					d = pane.createDialog(null, "Uh Oh");
					d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
					d.setVisible(true);
				}
			}
		} else if (e.getKeyCode() == 47) {
			if (Math.random()>0.5)
				GIFDisplay.shelbyIndex = 1;
			else
				GIFDisplay.shelbyIndex = 3;
		}
	}

	private void checkCommands(String input) {
		if (input.equalsIgnoreCase("record")) {
			userText.setText(ear.getTextFromWav(ear.listen()));
		} else if (input.equalsIgnoreCase("get_url")) {

			StringSelection stringSelection = new StringSelection(GIFDisplay.getURL());
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			changeButtons();
			pane = new JOptionPane("GIF URL copied to clipboard");
			d = pane.createDialog(null, "Present for you");
			d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
			d.setVisible(true);

		} else if (input.equalsIgnoreCase("refresh")) {

			GIFDisplay.memeURL = GIFDisplay.getURL();
			changeButtons();
			pane = new JOptionPane("GIF successfully refreshed");
			d = pane.createDialog(null, "Thanks for the refresher");
			d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
			d.setVisible(true);

		} else if (input.equalsIgnoreCase("set_filter")) {
			JPanel menu = new JPanel();
			menu.setBorder(new EmptyBorder(10, 10, 10, 10));
			menu.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTH;

			menu.add(new JLabel("<html><h1><i>Select a content filter level</i></h1><hr></html>"), gbc);

			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			high = new JButton("Keep it G");
			high.addActionListener(this);
			medium = new JButton("G and PG");
			medium.addActionListener(this);
			low = new JButton("G, PG, and PG-13");
			low.addActionListener(this);
			off = new JButton("G, PG, PG-13, and R (no nudity)");
			off.addActionListener(this);
			JPanel buttons = new JPanel(new GridBagLayout());
			buttons.add(high, gbc);
			buttons.add(medium, gbc);
			buttons.add(low, gbc);
			buttons.add(off, gbc);

			gbc.weighty = 1;
			menu.add(buttons, gbc);

			JFrame frame = new JFrame("Set Results Content Filter");
			frame.add(menu);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		} else if (input.equalsIgnoreCase("trash")) {

			try {
				changeButtons();
				pane = new JOptionPane(
						"Input a TTL. Any file older than the TTL will be deleted. The TTL is in seconds.");
				d = pane.createDialog(null, "Input TTL");
				pane.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				input = JOptionPane.showInputDialog(pane);
				int ttl = Integer.parseInt(input);
				int t1 = MindReader.folderClean("tenorJSON", ttl);
				int t2 = MindReader.folderClean("images", ttl);
				changeButtons();
				pane = new JOptionPane("Successfullly deleted " + t1 + " JSON files and " + t2 + " GIF files");
				d = pane.createDialog(null, "Success");
				d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				d.setVisible(true);
			} catch (NumberFormatException e1) {
				changeButtons();
				System.out.println("TTL must be an integer");
				pane = new JOptionPane("TTL must be an integer");
				d = pane.createDialog(null, "REEEEEE");
				d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				d.setVisible(true);
			} catch (SecurityException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} else if (input.equalsIgnoreCase("hide_shelby")) {
			GIFDisplay.shelbyIndex = -1;
			changeButtons();
			pane = new JOptionPane("Successfully hid Shelby (he will be back)");
			d = pane.createDialog(null, "Why did you do that?");
			d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
			d.setVisible(true);
		} else if (input.equalsIgnoreCase("resolution") || input.equalsIgnoreCase("res")) {
			try {
				changeButtons();
				pane = new JOptionPane(
						"Pick a number between 0 and 3 inclusive. 0 is nano, 1 is small, 2 is default, and 3 is full. \nWarning: Full resolution causes certain GIFs to not fully load");
				d = pane.createDialog(null, "Set Resolution");
				pane.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				input = JOptionPane.showInputDialog(pane);
				int res = Integer.parseInt(input);
				fancyTenor.setRes(res);
				changeButtons();
				pane = new JOptionPane("Successfully set the resolution to " + fancyTenor.getRes());
				d = pane.createDialog(null, "Success");
				d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				d.setVisible(true);
			} catch (NumberFormatException e) {
				changeButtons();
				System.out.println("Resolution must be an integer");
				pane = new JOptionPane("Resolution must be an integer");
				d = pane.createDialog(null, "REEEEEE");
				d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				d.setVisible(true);
			}
		} else if (input.equalsIgnoreCase("toggle_scaling") || input.equalsIgnoreCase("scale")) {
			GIFDisplay.scaling = !GIFDisplay.scaling;
			changeButtons();
			pane = new JOptionPane("Scaling set to " + GIFDisplay.scaling);
			d = pane.createDialog(null, "Toggled Scaling");
			d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
			d.setVisible(true);
		} else if (input.equalsIgnoreCase("result_limit") || input.equalsIgnoreCase("limit")) {
			try {
				changeButtons();
				pane = new JOptionPane("Set the result limit (Default is 10)");
				d = pane.createDialog(null, "Set Result Limit");
				pane.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				input = JOptionPane.showInputDialog(pane);
				int limit = Integer.parseInt(input);
				fancyTenor.setResultLimit(limit);
				changeButtons();
				pane = new JOptionPane("Successfully set the result limit to " + limit);
				d = pane.createDialog(null, "Success");
				d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				d.setVisible(true);
			} catch (NumberFormatException e) {
				changeButtons();
				System.out.println("Result limit must be an integer");
				pane = new JOptionPane("Result limit must be an integer");
				d = pane.createDialog(null, "REEEEEE");
				d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
				d.setVisible(true);
			}
		} else if (input.substring(0, 4).equalsIgnoreCase("play")) {
			input = input.substring(input.indexOf("_") + 1);
			try {
				openURL(JSONTools.getVideoLink(JSONTools
						.readJsonFromUrlAndPutItIntoAString("https://www.youtube.com/results?search_query=" + input)));
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (input.equalsIgnoreCase("help") || input.equals("?")) {
			changeButtons();
			pane = new JOptionPane(
					"Available Commands:\n/get url: copies gif url to clipboard\n/refresh: reload screen\n/trash: clear cache\n/hide shelby\n/resolution: adjust display\n/toggle scaling: scale display\n/set filter: choose content filtering level (G, PG, PG-13, mild R)\n/result limit: set result queue size\n/play (+ songname)\n/set filter\n/record\n/help");
			d = pane.createDialog(null, "Help Menu");
			d.setLocation((int) (Math.random() * 1200), (int) (Math.random() * 600));
			d.setVisible(true);
		}
		userText.setText("");
		if (GIFDisplay.shelbyIndex != -1) {
			if (Math.random() > 0.5)
				GIFDisplay.shelbyIndex = 0;
			else
				GIFDisplay.shelbyIndex = 4;
		}
	}

	/**
	 * Opens a given URL in the default browser
	 * 
	 * @param URL
	 */
	public void openURL(String URL) {
		System.out.println("Attempting to open " + URL);
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(URL));
			} catch (IOException | URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String getUserInput() {
		return userText.getText();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b = (JButton)arg0.getSource();
	    if (b==high)
	    	fancyTenor.setFilter("high");
	    else if (b == medium)
	      fancyTenor.setFilter("medium");
	    else if (b == low)
	      fancyTenor.setFilter("low");
	    else if (b == off)
	      fancyTenor.setFilter("off");
	    JOptionPane.showMessageDialog(null,
	            "Filter set", "Content filter has been set", JOptionPane.PLAIN_MESSAGE);
	    repaint();
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
		int i = (int) (10 * Math.random());
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
