package objects;

import gui.Main;
import gui.animation.DataAnimation;

import java.awt.Image;
import java.awt.Polygon;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum DataWeapon implements DataInterface {
	BULLET(1, "bullet.png", "bulletIcon.png", new int[]{-2, 2, 2, -2}, new int[]{-2, -2, 2, 2}, 15, 0, DataAnimation.EXPLOSION_SMALL, 0, null),
	GRENADE(2, "grenade.png", "grenade.png",  new int[]{-6, 5, 5, -6}, new int[]{-7, -7, 7, 7}, 30, 5000, DataAnimation.EXPLOSION_MEDIUM, 0, null);
	
	private Image image, imageIcon;
	private Polygon polyObject;
	private int power, lifeTime, id, separateNum;
	private DataAnimation da;
	private DataWeapon separateInto;
	private DataWeapon(int id, String filename, String filenameIcon, int[] dimensionsX, int[] dimensionsY, int power, int lifeTime, DataAnimation da, int separateNum, DataWeapon separateInto){
		try {
			image = ImageIO.read(Main.class.getResource("res/objects/" + filename));
			imageIcon = ImageIO.read(Main.class.getResource("res/objects/" + filenameIcon));
		} catch (IOException e) { e.printStackTrace(); }
		
		polyObject = new Polygon(dimensionsX, dimensionsY, dimensionsX.length);
		this.power = power;
		this.lifeTime = lifeTime;
		this.da = da;
		this.id = id;
		this.separateNum = separateNum;
		this.separateInto = separateInto;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getImageIcon() {
		return imageIcon;
	}
	
	public Polygon getPolyObject() {
		return polyObject;
	}

	public int getPower() {
		return power;
	}

	public int getLifeTime() {
		return lifeTime;
	}
	
	public DataAnimation getAnimation(){
		return da;
	}

	public int getID() {
		return id;
	}
	
	public int getSeparateNum() {
		return separateNum;
	}

	public DataWeapon getSeparateInto() {
		return separateInto;
	}

	public static DataWeapon getWeapon(int id){
		if(id < 1 || id > DataWeapon.values().length) return null;
		else return DataWeapon.values()[id-1];
	}
}
