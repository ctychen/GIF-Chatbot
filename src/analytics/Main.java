package analytics;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		String text = "Fun isn’t something one considers when balancing the universe. But this… does put a smile on my face.";
		
		Analyzer sentimentAnalyzer = new Analyzer();
		Analysis sentimentResult = sentimentAnalyzer.getResult(text);

		System.out.println("Sentiment Score: " + sentimentResult.getSentimentScore());
		System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());

	}
	
}