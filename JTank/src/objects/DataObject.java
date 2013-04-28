package objects;

import gui.Main;

import java.awt.Image;
import java.awt.Polygon;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum DataObject implements DataInterface {
	//top left, top right, bottom right, bottom left
	TANK("tank.png", new int[]{-8, 7, 25, -26}, new int[]{-12, -12, 11, 11});
	
	private Image image;
	private Polygon polyObject;
	private DataObject(String filename, int[] dimensionsX, int[] dimensionsY){
		try {
			image = ImageIO.read(Main.class.getResource("res/objects/" + filename));
		} catch (IOException e) { e.printStackTrace(); }
		
		polyObject = new Polygon(dimensionsX, dimensionsY, dimensionsX.length);
	}
	
	public Image getImage() {
		return image;
	}
	
	public Polygon getPolyObject() {
		return polyObject;
	}
}
