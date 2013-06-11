package objects;

import game.Game;
import game.Level;
import gui.animation.Animation;

public class WeaponElementary extends AbstractElementary {
	private long startTime;
	private DataWeapon dw;
	private String elementID;
	private int timeToLive;
	
	public WeaponElementary(float x, float y, float deg, float speed, DataWeapon dw) {
		super(x, y, dw);
		setSpeed((float) Math.cos(deg)*speed, (float) Math.sin(deg)*speed);

		this.dw = dw;
		this.startTime = System.currentTimeMillis();
		this.elementID = Game.getInstance().getPlayer().getElementID() + (System.currentTimeMillis()%10000);
		this.timeToLive = dw.getLifeTime();
	}
	
	public WeaponElementary(float x, float y, float dx, float dy, DataWeapon dw, String elementID){
		super(x, y, dw);
		setSpeed(dx, dy);
		
		this.dw = dw;
		this.startTime = System.currentTimeMillis();
		this.elementID = elementID;
		this.timeToLive = dw.getLifeTime() + 5000;
	}

	public void explode() {
		Game.getInstance().removeElement(this);
		Game.getInstance().addAnimation(new Animation(getX(), getY(), dw.getAnimation()));
		Level.getInstance().drawCircle(getX(), getY(), dw.getPower());
	}
	
	protected void checkGroundCollision() {
		if(timeToLive != 0) {
			super.checkGroundCollision();
			
			if(System.currentTimeMillis()-startTime > timeToLive) Game.getInstance().explodeElement(this);
		} else {
			boolean[] collision = getCollisionSides();
			
			for(int i=0; i<collision.length; i++) 
				if(collision[i]) {
					Game.getInstance().explodeElement(this);
					return;
				}
		}
	}

	public String getElementID() {
		return elementID;
	}
}
