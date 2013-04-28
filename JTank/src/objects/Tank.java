package objects;

import game.Game;
import gui.Main;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tank extends AbstractElementary {
	private float deg = 0;
	private Image crosshair;
	private int maxSpeed = 8, cooldown = 200, crosshairDist = 80, crosshairX, crosshairY;
	private long lastShot = 0;
	
	public Tank(float x, float y){
		super(x, y, DataObject.TANK);
		collisionDamping=0.6f;
		rotate(0);
		setSpeed(10, 10);
		
		try {
			crosshair = ImageIO.read(Main.class.getResource("res/objects/crosshair.png"));
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
	
	public void fireWeapon() {
		if(System.currentTimeMillis() - lastShot < cooldown) return;
		Game.addElement(new Bullet(x, y, deg));
		lastShot = System.currentTimeMillis();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(crosshair, this.getX() + crosshairX, this.getY() + crosshairY, null);
	}
}
