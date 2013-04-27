package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.ImageIcon;

import game.Level;
import gui.Main;

public abstract class AbstractElementary {
	private float x, y, dx, dy, dt;
	private boolean isRemoved = false, isFalling=true;
	protected Polygon polyObject;
	protected Image image;
	protected float damping = 0.9f;
	
	public AbstractElementary(float x, float y, ObjectsData od){
		this.x = x;
		this.y = y;
		image = new ImageIcon(od.getImageURL()).getImage();
		polyObject = od.getPolyObject();
	}
	
	public void tick(int dt){
		this.dt = dt/100.0f;
		
		if(isFalling) accelerate(0, 3);		
		move();
		checkGroundCollision();
		
		if(x > Main.GAME_WIDTH+100 || x < -100 || y > Main.GAME_HEIGHT+100 || y < -100) this.isRemoved = true;
	}
	
	private void checkGroundCollision(){
		boolean[] collision = getCollisionSides();
		
		if(!isFalling || !(collision[0] || collision[1] || collision [2] || collision[3])) return;
		else do {
			setRelativePosition(-Math.signum(dx), -Math.signum(dy));
			collision = getCollisionSides();
		} while(collision[0] || collision[1] || collision [2] || collision[3]);
		
		
		if(dx+dy < 5) {
			setSpeed(0, 0);
			isFalling=false;
		} else multiplySpeed(-1*damping, -1*damping);
	}
	
	private boolean[] getCollisionSides() {
		boolean[] collision = new boolean[4];
		for(int i=0; i<4; i++) collision[i] = Level.getInstance().isGroundAt(getX()+polyObject.xpoints[i], getY()+polyObject.ypoints[i]);
		return collision;
	}
	
	public boolean collidesWith(AbstractElementary other, boolean checkTwoWay){
		if(checkTwoWay)
			if(other.collidesWith(this, false)) return true;

		for(int i = 0; i<this.polyObject.npoints; i++)
			if(other.intersectsAt(this.polyObject.xpoints[i], this.polyObject.ypoints[i])) return true;

		return false;
	}
	
	public boolean intersectsAt(int x, int y){
		return polyObject.contains(x, y);
	}
	
	public void setPosition(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public void setRelativePosition(float dx, float dy){
		this.x += dx;
		this.y += dy;
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
		return isRemoved;
	}
	
	public abstract void paint(Graphics g);
}
