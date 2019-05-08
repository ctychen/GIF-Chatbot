package meme;

import gifAnimation.*;
import processing.core.*;

public class Meme {

	private Gif gif;

	public Meme() {

	}

	public Meme(String filename, PApplet p) {
		gif = new Gif(p, filename);
	}

}
