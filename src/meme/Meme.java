package meme;

import gifAnimation.*;
import processing.core.*;

public class Meme {

	private Gif gif;

	public Meme() {

	}

	public Meme(String filename, PApplet	 p) {
		gif = new Gif(p, filename);
		gif.play();
	}
	
	public void draw(PApplet g) {
		//System.out.println("Drawing GIF");
		g.image(gif, 0, 0);
	}

}
