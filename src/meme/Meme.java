package meme;

import gifAnimation.*;
import processing.core.*;

/**
 * Represents a meme object, which has a GIF image and properties for analysing it
 * @author claire
 *
 */
public class Meme {

	private Gif gif;

	public Meme() {

	}

	public Meme(String filename, PApplet p) {
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
		g.image(gif, 0, 0, g.width, g.height);
	}

	public Gif getGIF() {
		return this.gif;
	}

}
