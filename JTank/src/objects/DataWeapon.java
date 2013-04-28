package objects;

import gui.Main;

import java.awt.Polygon;
import java.net.URL;

public enum DataWeapon implements DataInterface {
	BULLET("bullet.png", new int[]{-2, 2, 2, -2}, new int[]{-2, -2, 2, 2}, 10, 50);
	
	private URL imageURL;
	private Polygon polyObject;
	private int power, speed;
	private DataWeapon(String filename, int[] dimensionsX, int[] dimensionsY, int power, int speed){
		imageURL = Main.class.getResource("res/objects/" + filename);
		polyObject = new Polygon(dimensionsX, dimensionsY, dimensionsX.length);
		this.power = power;
		this.speed = speed;
	}
	
	public URL getImageURL() {
		return imageURL;
	}
	
	public Polygon getPolyObject() {
		return polyObject;
	}

	public int getPower() {
		return power;
	}

	public int getSpeed() {
		return speed;
	}
}
