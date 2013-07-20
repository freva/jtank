package objects;

import game.Game;
import game.Level;
import gui.animation.Animation;

public class WeaponElementary extends AbstractElementary {
	private long endTime;
	private DataWeapon dw;
	private String elementID;
	
	public WeaponElementary(float x, float y, float deg, float speed, DataWeapon dw) {
		super(x+(28+dw.getPolyObject().xpoints[2])*(float) Math.cos(deg), y+(13+dw.getPolyObject().ypoints[2])*(float) Math.sin(deg), dw);
		setSpeed((float) Math.cos(deg)*speed, (float) Math.sin(deg)*speed);

		this.dw = dw;
		this.endTime = System.currentTimeMillis() + dw.getLifeTime();
		this.elementID = Game.getInstance().getPlayer().getElementID() + (System.currentTimeMillis()%10000);
	}
	
	public WeaponElementary(float x, float y, float dx, float dy, DataWeapon dw, String elementID){
		super(x, y, dw);
		setSpeed(dx, dy);
		
		this.dw = dw;
		this.endTime = System.currentTimeMillis() + dw.getLifeTime() + 5000;
		this.elementID = elementID;
	}

	public void tick(int dt) {
		super.tick(dt);
		checkTankCollision();
	}

	public void explode() {
		Game.getInstance().removeElement(this);
		Game.getInstance().addAnimation(new Animation(getX(), getY(), dw.getAnimation()));
		Level.getInstance().drawCircle(getX(), getY(), dw.getPower());
		Game.getInstance().getPlayer().doDamage(this);
	}
	
	protected void checkGroundCollision() {
		if(dw.getLifeTime() != 0) {
			super.checkGroundCollision();
			
			if(System.currentTimeMillis() > endTime) Game.getInstance().explodeElement(this);
		} else {
			boolean[] collision = getCollisionSides();

			for (boolean aCollision : collision)
				if (aCollision) {
					Game.getInstance().explodeElement(this);
					return;
				}
		}
	}

	private void checkTankCollision() {
		if(! Game.getInstance().isOwnedByMe(this)) return;

		for(AbstractElementary ae : Game.getInstance().getElements())
			if(ae instanceof Tank){
				Tank t = (Tank) ae;
				if(Math.abs(t.getX()-getX()) > getMaxDamage() || Math.abs(t.getY()-getY()) > getMaxDamage()) continue;

				if(dw.getLifeTime() == 0) {
					if(t.collidesWith(this, true)) Game.getInstance().explodeElement(this);
				} else {
					boolean[] collisions = new boolean[polyObject.npoints];
					boolean doesCollide = false;
					for(int i=0; i<polyObject.npoints; i++) {
						collisions[i] = Game.getInstance().getPlayer().intersectsAt(polyObject.xpoints[i], polyObject.ypoints[i]);
						doesCollide = doesCollide || collisions[i];
					}

					if(doesCollide) adjustSpeed(collisions);
				}
			}
	}

	public String getElementID() {
		return elementID;
	}

	public int getMaxDamage(){
		return dw.getPower()<<2;
	}
}
