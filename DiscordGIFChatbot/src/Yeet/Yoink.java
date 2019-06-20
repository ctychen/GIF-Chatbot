package Yeet;

import java.io.IOException;

import Discord.MessageListener;
import analytics.Analysis;
import analytics.Analyzer;
import web.MindReader;
import web.Tenor;

public class Yoink {
	
	private Tenor fancyTenor;
	private Analyzer sentimentAnalyzer;
	private Analysis sentimentResult;
	private String[] positivity = { "fancy", "happy", "awesome", "fun", "funny", "yeet", "yoink", "amazing", "excited",
			"yay", "wow" };
	private String[] negativity = { "depressing", "sad", "unhappy", "unfortunate", "terrible", "ouch", "oof", "unlucky",
			"not feeling so good" };
	
	public Yoink() {
		fancyTenor = new Tenor();
	}
	
	public String parse(String text) {
		
		if (text != null && text.length() > 0) { 
			
			String input = text.replaceAll(" ", "_");
			
			if (input.equalsIgnoreCase("osman"))
				input = "thumb";
			if (input.equalsIgnoreCase("carl"))
				input = "fancy";
			if (input.equalsIgnoreCase("anand")) {
				/*for (int i = 0; i < Math.random() * 100 + 40; i++) {
					openURL(memeURLs[(int) (Math.random() * memeURLs.length)]);
				}*/
				if (Math.random() > 0.3)
					input = "paranoid";
				else if (Math.random() > 0.3)
					input = "fbi";
				else
					input = "ww2";
			}
			if (input.contains("/")) {
				checkCommands(input.substring(1));
			} else {
				/*if (getWordCount(input) > 2) {
					for (int i = 0; i < input.length(); i++)
						if (input.charAt(i) == '_')
							input = input.substring(0, i) + " " + input.substring(i + 1);
					System.out.println("Long input, running sentiment analysis on " + input);
					sentimentAnalyzer = new Analyzer();
					sentimentResult = sentimentAnalyzer.getResult(input);
					switch ((int) sentimentResult.getSentimentScore()) { // If the user is in a good mood, we got to
																			// turn that smile upside down, if they are
																			// in a bad mood, then we gotta cheer them
																			// up
					case 0:
					case 1:
					case 2:
						input = positivity[(int) (Math.random() * positivity.length)];
						break;
					case 3:
					case 4:
						input = negativity[(int) (Math.random() * negativity.length)];
						break;
					}
					System.out.println("Sentiment Result = " + sentimentResult.getSentimentScore()
							+ ", new search term = " + input);

				}*/
				String temp = fancyTenor.getGIFURL(fancyTenor.search(input)); // This is where the magic happens
				if (temp != null) {
					MessageListener.memeWebURL = temp;
					MessageListener.memeURL = fancyTenor.getGIFFromLocal(MessageListener.memeWebURL, 600); // Magic also happens
										
				} else {
					return "Ruh roh, something went wrong";
				}
			}
		}
		return "ya-yeet";
	}

	private void checkCommands(String input) {
		
		if (input.equalsIgnoreCase("trash")) {

			try {
				int ttl = 0;
				int t1 = MindReader.folderClean("tenorJSON", ttl);
				int t2 = MindReader.folderClean("images", ttl);
			} catch (NumberFormatException e1) {
				
			} catch (SecurityException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} 
	}

	private int getWordCount(String s) {
		if (s.length() > 0) {

			for (int i = 0; i < s.length()-1; i++) {
				while (i+1 < s.length() && s.charAt(i) == '_' && s.charAt(i+1) == '_') {
					s = s.substring(0, i) + s.substring(i+1);
				}
			}
			for (int i = 0; i < s.length() - 1; i++) {
				while (s.charAt(i) == '_' && s.charAt(i + 1) == '_') {
					s = s.substring(0, i) + s.substring(i + 1);
				}
			}
			while (s.charAt(s.length()-1) == '_') {
				s = s.substring(0, s.length()-1);
			}
			int result = 1;
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '_')
					result++;
			}
			return result;
		}
		return 0;
	}

}
