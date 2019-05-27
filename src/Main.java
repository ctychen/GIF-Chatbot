import ui.*;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main {

	public static void main(String args[]) {
		//System.out.println("YOINK");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		GIFDisplay drawing = new GIFDisplay();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window2 = (JFrame) canvas.getFrame();
		window2.setSize((int)((4f/3)*screenSize.getHeight()), (int)screenSize.getHeight());
		window2.setMinimumSize(new Dimension((int)((4f/3)*screenSize.getHeight()), (int)screenSize.getHeight()));
		window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window2.setResizable(true);
		window2.setLocation(0, 0);
		window2.setSize((int)((4f/3)*screenSize.getHeight()), (int)screenSize.getHeight());

		window2.setVisible(true);
		ChatWindow window = new ChatWindow();
		window.setBounds((int)((4f/3)*screenSize.getHeight()), 0, (int)screenSize.getWidth()-(int)((4f/3)*screenSize.getHeight()), (int)((4f/3)*screenSize.getHeight()));
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window2.setSize((int)((4f/3)*screenSize.getHeight()), (int)screenSize.getHeight());
		//window.setLocation(0, 330);
		window.setVisible(true);
	}

}
