package objects;

import gui.Main;
import gui.animation.DataAnimation;

import java.awt.Image;
import java.awt.Polygon;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum DataWeapon implements DataInterface {
	BULLET(1, "bullet.png", "bulletIcon.png", new int[]{-2, 2, 2, -2}, new int[]{-2, -2, 2, 2}, 15, 0, DataAnimation.EXPLOSION_SMALL),
	GRENADE(2, "grenade.png", "grenade.png",  new int[]{-6, 5, 5, -6}, new int[]{-7, -7, 7, 7}, 30, 5000, DataAnimation.EXPLOSION_MEDIUM);
	
	private Image image, imageIcon;
	private Polygon polyObject;
	private int power, lifeTime, id;
	private DataAnimation da;
	private static DataWeapon[] weapons = new DataWeapon[] {null, DataWeapon.BULLET, DataWeapon.GRENADE};
	private DataWeapon(int id, String filename, String filenameIcon, int[] dimensionsX, int[] dimensionsY, int power, int lifeTime, DataAnimation da){
		try {
			image = ImageIO.read(Main.class.getResource("res/objects/" + filename));
			imageIcon = ImageIO.read(Main.class.getResource("res/objects/" + filenameIcon));
		} catch (IOException e) { e.printStackTrace(); }
		
		polyObject = new Polygon(dimensionsX, dimensionsY, dimensionsX.length);
		this.power = power;
		this.lifeTime = lifeTime;
		this.da = da;
		this.id = id;
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
	
	public static DataWeapon getWeapon(int id){
		if(id < 1 || id >= weapons.length) return null;
		else return weapons[id];
	}
}
