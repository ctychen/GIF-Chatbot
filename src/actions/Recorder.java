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

import web.MindReader;

public class Recorder {
   TargetDataLine line;
   AudioFormat a = new AudioFormat(16000, 8, 2, true, true);

   public String start() {
	  String filename = null;
      try {
         DataLine.Info info = new DataLine.Info(TargetDataLine.class, a);
         line = (TargetDataLine) AudioSystem.getLine(info);
         line.open(a);
         line.start();
         AudioInputStream ais = new AudioInputStream(line);
         //filename = "assets" + MindReader.fileSep + UUID.randomUUID() + ".wav";
         filename = "assets" + MindReader.fileSep + "recAudio.wav";
         AudioSystem.write(ais, AudioFileFormat.Type.WAVE,
               new File(filename));
      } catch (Exception e) {
         e.printStackTrace();
         filename = null;
      }
      return filename;
   }

   public Recorder() {
      Thread stopper = new Thread(new Runnable() {
         public void run() {
            try {
               //Thread.sleep(Integer.parseInt(JOptionPane
               //      .showInputDialog("How long do you want to record (seconds)")) * 1000);
            	Thread.sleep(5000);
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
            line.stop();
            line.close();
            JOptionPane.showMessageDialog(null, "Finished.");
         }
      });
      stopper.start();
      start();
   }
}
