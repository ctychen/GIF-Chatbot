package analytics;

import ui.ChatWindow;

/**
 * Parses user input and looks for keywords, which will be used for analysis and by SmartBoi
 * @author ctchen
 *
 */
public class TextGetter {

	private String userInput;
	
	public void parseInput() {
		userInput = ChatWindow.getUserInput();
		
	}
	
	
}
