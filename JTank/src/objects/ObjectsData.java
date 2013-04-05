package objects;

import gui.Main;

import java.awt.Polygon;
import java.net.URL;

public enum ObjectsData {
	//top left, top right, bottom right, bottom left
	TANK("tank.png", new int[]{-8, 7, 25, -26}, new int[]{-12, -12, 11, 11});
	
	private URL imageURL;
	private Polygon polyObject;
	private ObjectsData(String filename, int[] dimensionsX, int[] dimensionsY){
		imageURL = Main.class.getResource("res/objects/" + filename);
		polyObject = new Polygon(dimensionsX, dimensionsY, dimensionsX.length);
	}
	
	public URL getImageURL() {
		return imageURL;
	}
	
	public Polygon getPolyObject() {
		return polyObject;
	}
}
