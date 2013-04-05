package objects;

import gui.Main;

public abstract class AbstractElementary {
	private float x, y, dx, dy;
	private int dt;
	private boolean removed = false, falling=false;
	
	public AbstractElementary(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(int dt){
		this.dt = dt;
		accelerate(0, 0.1f);
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

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
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
}
