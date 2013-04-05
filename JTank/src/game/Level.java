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
			levelBuffered = ImageIO.read(levelData.getLevelURL());
		} catch (IOException e) { e.printStackTrace(); }

		levelImageData = ((DataBufferByte) levelBuffered.getRaster().getDataBuffer()).getData();
		levelImage = new ImageIcon(levelData.getLevelURL());
		backgroundImage = new ImageIcon(levelData.getBackgroundURL());
	}
	
	public void drawCircle(int x, int y, int radius){
		int diam=radius<<1;
		BufferedImage img = new BufferedImage(diam, diam, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.white);
		g.fillOval(0, 0, diam, diam);
		g.drawImage(img, null, 0, 0);
		g.dispose();
		
		byte[] temp = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		x -= radius;
		y -= radius;
		
		int width = Main.GAME_WIDTH-x, height = Main.GAME_HEIGHT-y, startX = 0, startY = 0;
		if(width > diam) width = diam;
		if(height > diam) height = diam;
		if(x < 0) startX = -x;
		if(y < 0) startY = -y;
	
		int current = Main.GAME_WIDTH*(y+startY)+x;
		for(int i=startY; i<height; i++, current+=Main.GAME_WIDTH)
			for(int j=startX; j<width; j++)
				if(temp[diam*i+j] != 0) levelImageData[current+j]=-1;
		
		levelImage = new ImageIcon(levelBuffered);
	}
	
	public boolean isGroundAt(int x, int y){
		return levelImageData[Main.GAME_WIDTH*y+x] != -1;
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
