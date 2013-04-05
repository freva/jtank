package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

import javax.swing.ImageIcon;

import game.Game;
import gui.Main;

public abstract class AbstractElementary {
	private float x, y, dx, dy, dt;
	private boolean removed = false, falling=false;
	protected Polygon polyObject;
	protected Image image;
	protected float damping = 0.6f;
	
	public AbstractElementary(float x, float y, ObjectsData od){
		this.x = x;
		this.y = y;
		image = new ImageIcon(od.getImageURL()).getImage();
		polyObject = od.getPolyObject();
	}
	
	public void tick(int dt){
		this.dt = dt/100.0f;
		accelerate(0, 3);		
		move();
		
		checkGroundCollision();
		
		if(x > Main.GAME_WIDTH+100 || x < -100 || y > Main.GAME_HEIGHT+100 || y < -100) this.removed = true;
	}
	
	public void checkGroundCollision(){
		boolean[] collision = new boolean[4];
		boolean[] side = new boolean[4];
		
		for(int i=0; i<4; i++)
			collision[i] = Game.getLevel().isGroundAt(getX()+polyObject.xpoints[i], getY()+polyObject.ypoints[i]);
		
		for(int i=0; i<4; i++)
			side[i] = !(collision[i] || collision[(i+1)%4]);
		
	
		float xMul=1/damping, yMul=1/damping, dx=0, dy=0;
		if((side[0] && !side[2]) || (!side[0] && side[2])){
			yMul=-1;
			dy = -this.dy;
		}
		if((side[1] && !side[3]) || (!side[1] && side[3])){
			xMul=-1;
			dx = -this.dx;
		}

		setPosition(x+dx, y+dy);
		multiplySpeed(xMul*damping, yMul*damping);
		if(dx < 5) dx=0;
		if(dy < 5) dy=0;
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
