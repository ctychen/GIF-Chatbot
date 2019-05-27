package actions;

import java.io.File;
import java.util.UUID;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

import ui.GIFDisplay;
import web.MindReader;

/**
 * Recording audio as .wav files using the microphone
 *
 */

public class Recorder {
	TargetDataLine line;
	AudioFormat a = new AudioFormat(16000, 16, 1, true, false);

	public String start() {
		String filename = null;
		try {
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, a);
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(a);
			line.start();
			AudioInputStream ais = new AudioInputStream(line);
			// filename = "assets" + MindReader.fileSep + UUID.randomUUID() + ".wav";
			filename = "assets" + MindReader.fileSep + "recAudio.wav";
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
			filename = null;
		}
		if (Math.random() > 0.5)
			GIFDisplay.shelbyIndex = 0;
		else
			GIFDisplay.shelbyIndex = 4;
		return filename;
	}

	public Recorder() {
		GIFDisplay.shelbyIndex = 5;
		Thread stopper = new Thread(new Runnable() {
			public void run() {
				try {
					// Thread.sleep(Integer.parseInt(JOptionPane
					// .showInputDialog("How long do you want to record (seconds)")) * 1000);
					Thread.sleep(3500);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				line.stop();
				line.close();
			}
		});
		stopper.start();
		start();
	}
}