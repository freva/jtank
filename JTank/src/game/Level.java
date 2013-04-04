package game;

import gui.Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Level {
	private ImageIcon levelImage, backgroundImage;
	private BufferedImage levelBuffered;
	private byte[] levelImageData;
	
	public Level(LevelData levelData){
		try {
			levelBuffered = ImageIO.read(levelData.getLevelFilename());
		} catch (IOException e) { e.printStackTrace(); }

		levelImageData = ((DataBufferByte) levelBuffered.getRaster().getDataBuffer()).getData();
		levelImage = new ImageIcon(levelData.getLevelFilename());
		backgroundImage = new ImageIcon(levelData.getBackgroundFilename());
	}
	
	public void drawCircle(int x, int y, int radius){
		int start = Main.GAME_WIDTH*(y-radius)+(x-radius), diam=radius<<1;
		BufferedImage img = new BufferedImage(radius<<1, radius<<1, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.white);
		g.fillOval(0, 0, diam, diam);
		g.drawImage(img, null, 0, 0);
		
		byte[] temp = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();		
		for(int i=0; i<diam; i++, start+=Main.GAME_WIDTH)
			for(int j=0; j<diam; j++)
				if(temp[diam*i+j] != 0) levelImageData[start+j]=-1;

	    updateLevelImage(levelBuffered);
	}

	private void updateLevelImage(BufferedImage img){
		levelImageData = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		levelImage = new ImageIcon(img);
	}
	
	public byte[] getLevelData(){
		return levelImageData;
	}
	
	public Image getBackgroundImage(){
		return backgroundImage.getImage();
	}
	
	public Image getLevelImage(){
		return levelImage.getImage();
	}
}
