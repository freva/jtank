package objects;

import game.Game;
import game.Level;

public class AbstractWeapon extends AbstractElementary {
	protected int speed, power;
	
	public AbstractWeapon(float x, float y, DataWeapon dw) {
		super(x, y, dw);
		this.speed = dw.getSpeed();
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
