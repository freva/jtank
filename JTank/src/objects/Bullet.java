package objects;

public class Bullet extends AbstractWeapon {
	
	public Bullet(float x, float y, float deg) {
		super(x, y, DataWeapon.BULLET);
		
		setSpeed((float) Math.cos(deg)*speed, (float) Math.sin(deg)*speed);
	}

	
	
}
