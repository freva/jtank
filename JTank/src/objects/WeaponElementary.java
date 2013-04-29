package objects;

import game.Game;
import game.Level;
import gui.animation.Animation;

public class WeaponElementary extends AbstractElementary {
	private long starTime;
	private DataWeapon dw;
	
	public WeaponElementary(float x, float y, float deg, float speed, DataWeapon dw) {
		super(x, y, dw);
		setSpeed((float) Math.cos(deg)*speed, (float) Math.sin(deg)*speed);

		this.dw = dw;
		this.starTime = System.currentTimeMillis();
	}

	protected void explode() {
		Game.removeElement(this);
		Game.addAnimation(new Animation(getX(), getY(), dw.getAnimation()));
		Level.getInstance().drawCircle(getX(), getY(), dw.getPower());
	}
	
	protected void checkGroundCollision() {
		if(dw.getLifeTime() != 0) {
			super.checkGroundCollision();
			
			if(System.currentTimeMillis()-starTime > dw.getLifeTime()) explode();
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
