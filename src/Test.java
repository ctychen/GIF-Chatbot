import ui.*;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Test {

	public static void main(String args[]) {
		GIFTester drawing = new GIFTester();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window2 = (JFrame) canvas.getFrame();
		window2.setSize(400, 300);
		window2.setMinimumSize(new Dimension(100, 100));
		window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window2.setResizable(true);

		window2.setVisible(true);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window2.setLocation(screenSize.width/2-window2.getSize().width/2, screenSize.height/2-window2.getSize().height/2-window2.getSize().height);

	}

}
