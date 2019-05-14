package ui;
import java.util.concurrent.TimeUnit;

import gifAnimation.Gif;
import meme.Meme;
import processing.core.*;
import web.MindReader;

/**
 * Represents a panel which displays an animated GIF 
 * @author  claire
 *
 */
public class GIFTester extends PApplet {
	
	Gif myAnimation;
	
	public GIFTester() {
		
	}
	
	public void setup() {
		
	    myAnimation = new Gif(this, "images/d30920c9d6a4013fd8a0b99decebfba4.gif");
	    myAnimation.play();
	}
	
	public void draw() {
		image(myAnimation, 10, 10);
	}
}
