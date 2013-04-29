package objects;

import gui.Main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Tank extends AbstractElementary {
	private float deg = 0;
	private Image crosshair, sign, heart;
	private int maxSpeed = 6, cooldown = 300, hitpoints = 150, crosshairDist = 80, crosshairX = 64, crosshairY = -16, currentWeapon = 0;
	private long lastShot = 0, lastShift = 0;
	private String username;
	private boolean moved = false;
	private ArrayList<WeaponInventory> weapons = new ArrayList<WeaponInventory>();
	
	public Tank(float x, float y){
		super(x, y, DataObject.TANK);
		collisionDamping=0.4f;
		setSpeed(20, 0);
		username = "Limon";
		
		weapons.add(new WeaponInventory(-1, DataWeapon.BULLET));
		weapons.add(new WeaponInventory(5, DataWeapon.GRENADE));
		
		try {
			crosshair = ImageIO.read(Main.class.getResource("res/objects/crosshair.png"));
			sign = ImageIO.read(Main.class.getResource("res/sign.png"));
			heart = ImageIO.read(Main.class.getResource("res/heart.png"));
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	public void rotate(float deg){
		this.deg += deg;
		crosshairX = (int) (Math.cos(this.deg)*crosshairDist)-16;
		crosshairY = (int) (Math.sin(this.deg)*crosshairDist)-16;
	}
	
	public void accelMove(float dSpeed){
		if(Math.abs(dx+dSpeed) > maxSpeed) return;
		dx += dSpeed;
		moved = true;
	}
	
	public void fireWeapon(int strength) {
		if(System.currentTimeMillis() - lastShot < cooldown) return;
		
		strength = strength>>4;
		if(strength < 10) strength = 10;
		if(strength > 90) strength = 90;
		
		weapons.get(currentWeapon).fire(x, y, deg, strength);
		lastShot = System.currentTimeMillis();
		
		if(!weapons.get(currentWeapon).isMore()) {
			weapons.remove(currentWeapon);
			currentWeapon = (currentWeapon+1)%weapons.size();
		}
	}
	
	public void nextWeapon(){
		if(System.currentTimeMillis() - lastShift < cooldown) return;
		currentWeapon = (currentWeapon+1)%weapons.size();
		lastShift = System.currentTimeMillis();
	}
	
	@Override
	protected void checkGroundCollision() {
		if(moved){
			float tempY = y;
			boolean[] collision = getCollisionSides();
			
			for(int i=0; i<5 && (collision[0] || collision[1] || collision [2] || collision[3]); i++, y--) collision = getCollisionSides();
			
			if((collision[0] || collision[1] || collision [2] || collision[3])) {
				y=tempY;
				super.checkGroundCollision();	
			}
			moved = false;
		} else super.checkGroundCollision();
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		int offsetX = this.getX()-49, offsetY = this.getY()-65;
		g.drawImage(sign, offsetX, offsetY, null);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(username, offsetX+5, offsetY+17);
		g.drawImage(heart, offsetX+4, offsetY+21, null);
		g.drawString("" + hitpoints, offsetX+19, offsetY+36);
		g.drawImage(weapons.get(currentWeapon).getImageIcon(), offsetX+55, offsetY+21, null);
		g.drawString(weapons.get(currentWeapon).getAmountString(), offsetX+74, offsetY+36);
		
		g.drawImage(crosshair, this.getX() + crosshairX, this.getY() + crosshairY, null);
	}
}
