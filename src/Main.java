import ui.*;
import java.awt.Dimension;
import javax.swing.JFrame;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main {

	public static void main(String args[]) {
		ChatWindow window = new ChatWindow();
		window.setBounds(300, 300, 700, 300);
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setVisible(true);
		GIFDisplay drawing = new GIFDisplay();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window2 = (JFrame) canvas.getFrame();
		window2.setSize(400, 300);
		window2.setMinimumSize(new Dimension(100, 100));
		window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window2.setResizable(true);

		window2.setVisible(true);

	}

}
