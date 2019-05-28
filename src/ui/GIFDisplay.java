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
	private PImage[] shelbys = new PImage[6];
	public static int shelbyIndex = 2;
	public static boolean scaling = true;
	
	public GIFDisplay() {
		
	}
	
	public void setup() {
		for (int i = 0; i < shelbys.length; i++)
			shelbys[i] = loadImage("assets" + MindReader.fileSep + "shelby" + i + ".png");
	}
	
	public void draw() {
		background(255);
		if (shelbyIndex == 1 || shelbyIndex == 3 || shelbyIndex == 5) {
			image(shelbys[shelbyIndex], 0, 0, width, height);
			translate(width*0.5f, height*0.5f);
			rotate(-0.02f*(float)Math.PI);
			rotate((float) Math.random()*(float)Math.PI*0.02f);
			scale((float)Math.random()*0.2f+1.2f);
			translate(-width*0.4f, -height*0.5f);
			
		}
		if (shelbyIndex != -1)
			image(shelbys[shelbyIndex], 0, 0, width, height);
		if (shelbyIndex == 1 || shelbyIndex == 3) {
			textSize(width*(float)Math.random()*0.03f+0.05f*width);
			translate(-width*0.1f, -height*0.1f);
			fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			text("Command Mode", width*0.2f+(int)(Math.random()*10), height-30-(int)(Math.random()*10));
			
		}
		
		if (shelbyIndex == 5) {
			textSize(width*(float)Math.random()*0.03f+0.05f*width);
			translate(-width*0.1f, -height*0.1f);
			fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
			text("Listening", width*0.2f+(int)(Math.random()*10), height-30-(int)(Math.random()*10));
			
		}
		if (shelbyIndex != 1 && shelbyIndex != 0 && shelbyIndex != 3 && shelbyIndex != 5) {
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
