package objects;

import java.awt.Graphics;

public class Tank extends AbstractElementary {
	public Tank(){
		super(100, 100, ObjectsData.TANK);
	}


	public void paint(Graphics g) {
		g.drawImage(image, this.getX(), this.getY(), null);		
	}
}
