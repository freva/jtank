package objects;

import gui.Main;
import java.net.URL;

public enum ObjectsData {
	TANK("tank.png", new int[]{-26, -8, 7, 25}, new int[]{-12, 11, 11, -12});
	
	private int[] dimensionsX, dimensionsY;
	private URL imageURL;
	private ObjectsData(String filename, int[] dimensionsX, int[] dimensionsY){
		imageURL = Main.class.getResource("res/objects/" + filename);
		this.dimensionsX = dimensionsX;
		this.dimensionsY = dimensionsY;
	}
	
	public int[] getDimensionsX() {
		return dimensionsX;
	}
	public int[] getDimensionsY() {
		return dimensionsY;
	}
	public URL getImageURL() {
		return imageURL;
	}
}
