package analytics;

import java.util.Properties;

import org.ejml.simple.SimpleMatrix;

import edu.stanford.nlp.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.parser.lexparser.*;

/**
 * Analyzes the text and returns an Analysis of the text
 *
 */
public class Analyzer {
	
	private Properties props;
	private StanfordCoreNLP pipeline;
	
	public Analyzer() {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public Analysis getResult(String text) {

		Analysis result = new Analysis();

		if (text != null && text.length() > 0) {
			
			Annotation annotation = pipeline.process(text);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
				String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

				result.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
				result.setSentimentType(sentimentType);
			}

		}


		return result;
	}
	
	
}