package analytics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import actions.Recorder;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.decoder.adaptation.Stats;
import edu.cmu.sphinx.decoder.adaptation.Transform;
import edu.cmu.sphinx.result.WordResult;
import web.MindReader;

/**
 * Based on: http://www.java-gaming.org/index.php?topic=36723.0
 * Also based on: https://github.com/cmusphinx/sphinx4/blob/master/sphinx4-samples/src/main/java/edu/cmu/sphinx/demo/transcriber/TranscriberDemo.java
 * 
 * @author cchen351, clerdorf786
 *
 */

public class SpeechToText {
	Configuration config;
	Recorder nsa;

	public SpeechToText() {
		config = new Configuration();
		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
	}

	public String listen() {
		nsa = new Recorder();
		return "assets" + MindReader.fileSep + "recAudio.wav";
	}

	public String getTextFromWav(String filename) {
		try {
			//transcribe(filename);
			//filename = "assets" + MindReader.fileSep  + "testAudio.wav";
			StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
			InputStream stream = new FileInputStream(new File(filename));
			stream.skip(44);
			recognizer.startRecognition(stream);
			SpeechResult result;
			String finalResult = "";
			while ((result = recognizer.getResult()) != null) {
				finalResult += result.getHypothesis() + " ";
				
			}
			System.out.println("Final Hypothesis: " + finalResult);
			recognizer.stopRecognition();
			return finalResult;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Transcribes a continuous audio file which might have multiple statements in
	 * it
	 * 
	 * @throws Exception
	 */
	public static void transcribe(String input) throws Exception {
		System.out.println("Loading models...");

		Configuration configuration = new Configuration();

		// Load model from the jar
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");

		// You can also load model from folder:
		// configuration.setAcousticModelPath("file:en-us");

		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
		
		//InputStream stream = SpeechToText.class.getClassLoader()
		//		.getResourceAsStream("/assets/testAudio.wav");
		InputStream stream = new FileInputStream(new File(input));
		if (stream == null)
			System.out.println("Stream is null");
		//stream.skip(44);
		
		// Simple recognition with geric model
		recognizer.startRecognition(stream);
		SpeechResult result;
		while ((result = recognizer.getResult()) != null) {

			System.out.format("Hypothesis: %s\n", result.getHypothesis());

			System.out.println("List of recognized words and their times:");
			for (WordResult r : result.getWords()) {
				System.out.println(r);
			}

			System.out.println("Best 3 hypothesis:");
			for (String s : result.getNbest(3))
				System.out.println(s);

		}
		recognizer.stopRecognition();

		// Live adaptation to speaker with speaker profiles

		stream = new FileInputStream(new File(input));
		//stream.skip(44);

		// Stats class is used to collect speaker-specific data
		Stats stats = recognizer.createStats(1);
		recognizer.startRecognition(stream);
		while ((result = recognizer.getResult()) != null) {
			stats.collect(result);
		}
		recognizer.stopRecognition();

		// Transform represents the speech profile
		Transform transform = stats.createTransform();
		recognizer.setTransform(transform);

		// Decode again with updated transform
		stream = new FileInputStream(new File(input));
		//stream.skip(44);
		recognizer.startRecognition(stream);
		while ((result = recognizer.getResult()) != null) {
			System.out.format("Hypothesis: %s\n", result.getHypothesis());
		}
		recognizer.stopRecognition();

	}

}
