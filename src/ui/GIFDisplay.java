package ui;
import meme.Meme;
import processing.core.*;
import web.MindReader;

/**
 * Represents a panel which displays an animated GIF 
 * @author  claire
 *
 */
public class GIFDisplay extends PApplet {

	private static Meme fancyMeme = null; 
	public static String memeURL = null;
	public static String memeWebURL = null;
	private PImage[] shelbys = new PImage[3];
	public static int shelbyIndex = 2;
	
	public GIFDisplay() {
		
	}
	
	public void setup() {
		for (int i = 0; i < shelbys.length; i++)
			shelbys[i] = loadImage("assets" + MindReader.fileSep + "shelby" + i + ".png");
	}
	
	public void draw() {
		background(255);
		if (shelbyIndex != -1)
			image(shelbys[shelbyIndex], 0, 0, width, height);
		if (shelbyIndex == 1) {
			textSize(32);
			fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			text("Command Mode", 70+(int)(Math.random()*10), height-30-(int)(Math.random()*10));
		}
		if (shelbyIndex != 1 && shelbyIndex != 0) {
			if (memeURL != null) {
				fancyMeme = new Meme(memeURL, this);
				fancyMeme.setWebURL(memeWebURL);
				memeURL = null;
			}
			if (fancyMeme != null) {
				fancyMeme.draw(this);
			}
		}
	}
	
	public static String getURL() {
		return fancyMeme.getURL();
	}
}
