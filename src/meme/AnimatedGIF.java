package meme;

import processing.core.*;

public class AnimatedGIF {
	private PImage[] images;
	private int imagesCount;
	private int numFrames;

	/**
	 * Represents an animated GIF which can be displayed with Processing
	 * @param prefix what to call the image
	 * @param count number of images which will make up this GIF
	 * @param p PApplet with which to display this image
	 */
	public AnimatedGIF(String prefix, int count, PApplet p) {
		this.imagesCount = count;
		images = new PImage[this.imagesCount];
		for (int i = 0; i < this.imagesCount; i++) {
			String filename = prefix + PApplet.nf(i, 4) + ".gif";
			images[i] = p.loadImage(filename);
		}

	}

	/**
	 * Displays the GIF by loading in image frames
	 * @param x x-coordinate of where to display
	 * @param y y-coordinate of where to display
	 * @param p PApplet with which to display this image
	 */
	public void display(float x, float y, PApplet p) {
		numFrames = (numFrames + 1) % imagesCount;
		p.image(images[numFrames], x, y);
	}

	/**
	 *
	 * @return width of the GIF 
	 */
	public int getWidth() {
		return images[0].width;
	}

}
