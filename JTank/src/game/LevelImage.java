package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public enum LevelImage {
	KingKong("KingKong.png"), Mantis("Mantis.png"), Medieval("Medieval.png");
	
	private ImageIcon background, level;
	private BufferedImage levelBuffered;
	LevelImage(String filename){
		try {
			BufferedImage rgbBufferedImage = ImageIO.read(new File("res/lvl/" + filename));
			levelBuffered = new BufferedImage(rgbBufferedImage.getWidth(), rgbBufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			levelBuffered.getGraphics().drawImage(rgbBufferedImage, 0, 0, rgbBufferedImage.getWidth(), rgbBufferedImage.getHeight(), Color.white, null);
		} catch (IOException e) { e.printStackTrace(); }

		level = new ImageIcon("res/lvl/" + filename);
		background = new ImageIcon("res/lvl/skies_bg.png");
	}
	
	public BufferedImage getLevelBuffered() {
		return levelBuffered;
	}
	
	public ImageIcon getBackground() {
		return background;
	}
	
	public ImageIcon getLevel(){
		return level;
	}
}
