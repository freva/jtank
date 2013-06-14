package objects;

import game.Level;
import gui.Main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tank extends AbstractElementary {
	private float deg = 0;
	private Image crosshair, sign, heart;
	private int hitpoints = 150, crosshairDist = 80, crosshairX = 64, crosshairY = -16, currentWeapon = 0;
	private String username;
	private WeaponInventory[] weapons = new WeaponInventory[] {new WeaponInventory(-1, DataWeapon.BULLET), new WeaponInventory(5, DataWeapon.GRENADE)};
	
	public Tank(float x, float y, String username){
		super(x, y, DataObject.TANK);
		collisionDamping=0.4f;
		setSpeed(20, -20);
		this.username = username;
		
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
		if(isOnGround()) dx += dSpeed;
	}
	
	public void fireWeapon(int strength) {
		strength = trimStrength(strength);
		weapons[currentWeapon].fire(x, y, deg, strength);
		
		while(!weapons[currentWeapon].isMore()) currentWeapon = (currentWeapon+1)%weapons.length;
	}
	
	public int trimStrength(int strength){
		strength = strength>>4;
		if(strength < 10) strength = 10;
		if(strength > 90) strength = 90;
		return strength;
	}
	
	public void explode() {
		
	}
	
	public void nextWeapon(){
		do {
			currentWeapon = (currentWeapon+1)%weapons.length;
		} while(!weapons[currentWeapon].isMore());
	}
	
	@Override
	protected void checkGroundCollision() {
		if(isOnGround()){
			float tempY = y;
			boolean[] collision = getCollisionSides();
			
			for(int i=0; i<7 && (collision[0] || collision[1] || collision [2] || collision[3]); i++, y--) collision = getCollisionSides();
			
			if(collision[0] || collision[1] || collision [2] || collision[3]) {
				y=tempY;
				super.checkGroundCollision();	
			} else {
				dx *= groundFriction;
				y+=2;
				dy = 0;
			}
		} else super.checkGroundCollision();
	}
	
	public boolean isOnGround() {
		return Level.getInstance().isGroundAt(getX()+polyObject.xpoints[2], getY()+polyObject.ypoints[3]+5) || Level.getInstance().isGroundAt(getX()+polyObject.xpoints[3], getY()+polyObject.ypoints[3]+5);
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		int offsetX = this.getX()-49, offsetY = this.getY()-65;
		g.drawImage(sign, offsetX, offsetY, null);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(username, offsetX+5, offsetY+17);
		g.drawImage(heart, offsetX+4, offsetY+21, null);
		g.drawString("" + hitpoints, offsetX+19, offsetY+36);
		g.drawImage(weapons[currentWeapon].getImageIcon(), offsetX+55, offsetY+21, null);
		g.drawString(weapons[currentWeapon].getAmountString(), offsetX+74, offsetY+36);
		
		g.drawImage(crosshair, this.getX() + crosshairX, this.getY() + crosshairY, null);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setDeg(float deg){
		this.deg = deg;
		rotate(0);
	}
	
	public void setCurrentWeapon(int currentWeapon){
		this.currentWeapon = currentWeapon;
	}
	
	public void setAmmoForWeapon(int weaponID, int amount){
		weapons[weaponID].setAmount(amount);
	}
	
	public String toString(){
		return super.toString() + "@" + deg + "@" + currentWeapon + "@" + weapons[currentWeapon].getAmount();
	}
	
	public String getElementID() {
		return username;
	}
}
