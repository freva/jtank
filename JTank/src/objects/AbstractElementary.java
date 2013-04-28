package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.ImageIcon;

import game.Game;
import game.Level;
import gui.Main;

public abstract class AbstractElementary {
	protected float x, y, dx, dy, dt, collisionDamping = 0.9f, groundFriction = 0.6f;
	protected Polygon polyObject;
	protected Image image;
	
	public AbstractElementary(float x, float y, DataInterface di){
		this.x = x;
		this.y = y;
		image = new ImageIcon(di.getImageURL()).getImage();
		polyObject = di.getPolyObject();
	}
	
	public void tick(int dt){
		this.dt = dt/100.0f;
		
		accelerate(0, 3);		
		move();
		checkGroundCollision();
		
		if(x > Main.GAME_WIDTH+100 || x < -100 || y > Main.GAME_HEIGHT+100 || y < -100) Game.removeElement(this);
	}
	
	protected void checkGroundCollision(){
		boolean[] collision = getCollisionSides(), temp=collision.clone();
		
		if(!(collision[0] || collision[1] || collision [2] || collision[3])) return;
		float max = Math.max(Math.abs(dx), Math.abs(dy));
		float ddx = (dx/max), ddy = (dy/max);
		do {
			setRelativePosition(-ddx, -ddy);
			collision = getCollisionSides();
		} while(collision[0] || collision[1] || collision [2] || collision[3]);
		
		int xMul=0, yMul=0;
		for(int i=0; i<4; i++){
			if(temp[i]){
				xMul += Math.signum(polyObject.xpoints[i]);
				yMul += Math.signum(polyObject.ypoints[i]);
			}
		}
		
		if(xMul==0) xMul = 1;
		else xMul = (int) -Math.signum(xMul * dx);
		
		if(yMul==0) yMul = 1;
		else yMul = (int) -Math.signum(yMul * dy);

		
		if(xMul==1 && dy < 10) multiplySpeed(groundFriction, groundFriction);
		else multiplySpeed(xMul*collisionDamping, yMul*collisionDamping);
		//else setSpeed(0, 0);
		
	}
	
	protected boolean[] getCollisionSides() {
		boolean[] collision = new boolean[4];
		for(int i=0; i<collision.length; i++) collision[i] = Level.getInstance().isGroundAt(getX()+polyObject.xpoints[i], getY()+polyObject.ypoints[i]);
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
	
	public void paint(Graphics g) {
		g.drawImage(image, this.getX()+polyObject.xpoints[3], this.getY()+polyObject.ypoints[0], null);
	}
}
