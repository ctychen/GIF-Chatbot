package analytics;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import actions.Recorder;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import web.MindReader;

//http://www.java-gaming.org/index.php?topic=36723.0

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
		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(config);
        InputStream stream = new FileInputStream(new File(filename));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
        	System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();
		} catch (Exception e) {
			System.out.println(e);
		}
        return null;
	}
}
