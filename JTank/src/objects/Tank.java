package objects;

import game.Game;
import gui.Main;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tank extends AbstractElementary {
	private float deg = 0;
	private Image crosshair, sign, heart;
	private int maxSpeed = 8, cooldown = 300, hitpoints = 150, crosshairDist = 80, crosshairX, crosshairY;
	private long lastShot = 0;
	private DataWeapon currentWeapon = DataWeapon.GRENADE;
	private String username;
	
	public Tank(float x, float y){
		super(x, y, DataObject.TANK);
		collisionDamping=0.6f;
		rotate(0);
		setSpeed(10, 10);
		username = "Limon";
		
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
		dx += dSpeed;
		if(Math.abs(dy) < 3) dy = -2;
		if(dx > maxSpeed) dx = maxSpeed;
	}
	
	public void fireWeapon(int strength) {
		if(System.currentTimeMillis() - lastShot < cooldown) return;
		
		strength = strength>>4;
		if(strength < 10) strength = 10;
		if(strength > 60) strength = 90;
		
		Game.addElement(new Weapon(x, y, deg, strength, currentWeapon));
		lastShot = System.currentTimeMillis();
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		int offsetX = this.getX()-49, offsetY = this.getY()-65;
		g.drawImage(sign, offsetX, offsetY, null);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString(username, offsetX+5, offsetY+17);
		g.drawImage(heart, offsetX+4, offsetY+21, null);
		g.drawString("" + hitpoints, offsetX+19, offsetY+36);
		g.drawImage(currentWeapon.getImageIcon(), offsetX+55, offsetY+21, null);
		g.drawString("50", offsetX+74, offsetY+36);
		
		g.drawImage(crosshair, this.getX() + crosshairX, this.getY() + crosshairY, null);

	}
}
