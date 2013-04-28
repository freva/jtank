package objects;

import game.Game;
import game.Level;

public class Weapon extends AbstractElementary {
	protected int lifeTime, power;
	
	public Weapon(float x, float y, float deg, float speed, DataWeapon dw) {
		super(x, y, dw);
		setSpeed((float) Math.cos(deg)*speed, (float) Math.sin(deg)*speed);
		
		this.lifeTime = dw.getLifeTime();
		this.power = dw.getPower();
	}

	private void explode() {
		Game.removeElement(this);
		Level.getInstance().drawCircle(getX(), getY(), power);
	}
	
	@Override
	protected void checkGroundCollision() {
		boolean[] collision = getCollisionSides();
		
		for(int i=0; i<collision.length; i++) 
			if(collision[i]) {
				this.explode();
				return;
			}
	}
}
