package analytics;

/**
 * This class represents the analysis of the piece of text of how negative/positive it is
 * 
 * @author zwang695
 *
 */

public class Analysis {
	
	double sentimentScore;
	String sentimentType;

	/**
	 * @return Sentiment Score of the text
	 */
	public double getSentimentScore() {
		return sentimentScore;
	}

	/**
	 * Sets the sentiment score 
	 * 
	 * @param sentimentScore a number of how positive/negative the text is
	 */
	public void setSentimentScore(double sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

	/**
	 * @return Sentiment Type of the score
	 */
	public String getSentimentType() {
		return sentimentType;
	}

	/**
	 * Sets the sentiment type
	 * @param sentimentType a word for how positive/negative the text is
	 */
	public void setSentimentType(String sentimentType) {
		this.sentimentType = sentimentType;
	}

}