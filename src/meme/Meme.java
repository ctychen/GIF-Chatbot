package meme;

import gifAnimation.*;
import processing.core.*;
import ui.GIFDisplay;

/**
 * Represents a meme object, which has a GIF image and properties for analysing it
 * @author claire
 *
 */
public class Meme {

	private Gif gif;
	private String url;
	private String weburl;

	public Meme() {

	}

	public Meme(String filename, PApplet p) {
		url = filename;
		try {
			gif = new Gif(p, filename);
		} catch(NullPointerException e) {
			System.out.println("Null pointer exception at: " + filename);
			System.out.println(e);
		}
		gif.play();
	}

	public void draw(PApplet g) {
		// System.out.println("Drawing GIF");
		if (GIFDisplay.scaling)
			g.image(gif, 0, 0, g.width, g.height);
		else
			g.image(gif, 0, 0);
	}

	public Gif getGIF() {
		return this.gif;
	}
	
	public String getURL() {
		return weburl;
	}
	
	public void setWebURL(String s) {
		weburl = s;
	}

}
