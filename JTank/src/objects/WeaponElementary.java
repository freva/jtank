package objects;

import game.Game;
import game.Level;

public class WeaponElementary extends AbstractElementary {
	private int lifeTime, power;
	private long starTime;
	
	public WeaponElementary(float x, float y, float deg, float speed, DataWeapon dw) {
		super(x, y, dw);
		setSpeed((float) Math.cos(deg)*speed, (float) Math.sin(deg)*speed);
		
		this.lifeTime = dw.getLifeTime();
		this.power = dw.getPower();
		this.starTime = System.currentTimeMillis();
	}

	protected void explode() {
		Game.removeElement(this);
		Level.getInstance().drawCircle(getX(), getY(), power);
	}
	
	protected void checkGroundCollision() {
		if(lifeTime != 0) {
			super.checkGroundCollision();
			
			if(System.currentTimeMillis()-starTime > lifeTime) explode();
		} else {
			boolean[] collision = getCollisionSides();
			
			for(int i=0; i<collision.length; i++) 
				if(collision[i]) {
					this.explode();
					return;
				}
		}
	}
}
