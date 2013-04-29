package gui.animation;

import gui.Main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum DataAnimation {
	EXPLOSION_SMALL("explosionSmall.png", 10, 32, 32),
	EXPLOSION_MEDIUM("explosionMedium.png", 10, 47, 57);
	
	private BufferedImage[] frames;
	private int numFrames, width, height;
	private DataAnimation(String filename, int numFrames, int width, int height){
		BufferedImage bi = null;
		this.numFrames = numFrames;
		this.width = width;
		this.height = height;
		
		frames = new BufferedImage[numFrames];
		
		try {
			bi = ImageIO.read(Main.class.getResource("res/animation/" + filename));
		} catch (IOException e) { e.printStackTrace(); }

		for(int i=0; i<numFrames; i++){
			frames[i] = bi.getSubimage(width*i, 0, width, height);
		}
	}
	
	public BufferedImage getFrame(int frameNum){
		return frames[frameNum];
	}
	
	public int getNumFrames(){
		return numFrames;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
