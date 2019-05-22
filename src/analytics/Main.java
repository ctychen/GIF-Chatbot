package analytics;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {

		String text = "Those who find ugly meanings in beautiful things are corrupt without being charming.";
		
		Analyzer sentimentAnalyzer = new Analyzer();
		Analysis sentimentResult = sentimentAnalyzer.getResult(text);

		System.out.println("Sentiment Score: " + sentimentResult.getSentimentScore());
		System.out.println("Sentiment Type: " + sentimentResult.getSentimentType());

	}
	
}
