package game;

import gui.Main;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Level {
	public BufferedImage levelBuffered, backgroundBuffered;
	private static Level level;
	
	public static void setInstance(LevelData levelData){
		if(level == null) level = new Level();
		try {
			level.levelBuffered = ImageIO.read(levelData.getLevelURL());
			level.backgroundBuffered = ImageIO.read(levelData.getBackgroundURL());
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void setInstance(byte[] bgData, byte[] levelData){
		if(level == null) level = new Level();
		try {
			level.backgroundBuffered = ImageIO.read(new ByteArrayInputStream(bgData));
			level.levelBuffered = ImageIO.read(new ByteArrayInputStream(levelData));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static Level getInstance(){
		return level;
	}
	
	public void drawCircle(int x, int y, int radius){
		Graphics2D g = levelBuffered.createGraphics();
		try {
		    g.setComposite(AlphaComposite.Clear);
		    g.fillOval(x-radius, y-radius, radius<<1, radius<<1);
		} finally {
		    g.dispose();
		}
	}
	
	public boolean isGroundAt(int x, int y){
		if(x<0 || y<0 || x>=Main.GAME_WIDTH || y>=Main.GAME_HEIGHT) return false;
		return (levelBuffered.getRGB(x, y)>>24) != 0;
	}
	
	public byte[] getLevelByteArray() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] temp = null;
		try {
			ImageIO.write(level.levelBuffered, "png", baos);
			baos.flush();
			temp = baos.toByteArray();
			baos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return temp;
	}
	
	public byte[] getBackgroundByteArray() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] temp = null;
		try {
			ImageIO.write(level.backgroundBuffered, "png", baos);
			baos.flush();
			temp = baos.toByteArray();
			baos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return temp;
	}

	public BufferedImage getBackgroundImage(){
		return level.backgroundBuffered;
	}
	
	public BufferedImage getLevelImage(){
		return level.levelBuffered;
	}
	
	private Level(){}
}
