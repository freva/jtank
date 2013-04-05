package objects;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import gui.Main;

public abstract class AbstractElementary {
	private float x, y, dx, dy, dt;
	private boolean removed = false, falling=false;
	protected Image image;
	
	public AbstractElementary(float x, float y, ObjectsData od){
		this.x = x;
		this.y = y;
		image = new ImageIcon(ObjectsData.TANK.getImageURL()).getImage();
	}
	
	public void tick(int dt){
		this.dt = dt/100.0f;
		accelerate(0, 3);
		move();
		
		if(x > Main.GAME_WIDTH+100 || x < -100 || y > Main.GAME_HEIGHT+100 || y < -100) this.removed = true;
	}
	
	public void setSpeed(float dx, float dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setRelativeSpeed(float dx, float dy){
		this.dx += dx;
		this.dy += dy;
	}
	
	public void multiplySpeed(float mulX, float mulY){
		this.dx *= mulX;
		this.dy *= mulY;
	}
	
	private void move(){
		this.x += dx*dt;
		this.y += dy*dt;
	}
	
	public void accelerate(float ddx, float ddy){
		this.dx += ddx*dt;
		this.dy += ddy*dt;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setFalling(boolean falling){
		this.falling = falling;
	}
	
	public boolean isFalling() {
		return falling;
	}
	
	public abstract void paint(Graphics g);
}
