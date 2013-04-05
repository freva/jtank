package objects;

import java.awt.Graphics;

public class Tank extends AbstractElementary {
	public Tank(float x, float y){
		super(x, y, ObjectsData.TANK);
		damping=0.6f;
		setSpeed(10, 10);
	}


	public void paint(Graphics g) {
		g.drawImage(image, this.getX()+polyObject.xpoints[3], this.getY()+polyObject.ypoints[0], null);
		
		int[] xx = new int[4], yy = new int[4];
		for(int i=0; i<polyObject.npoints; i++){
			xx[i] = getX() + polyObject.xpoints[i];
			yy[i] = getY() + polyObject.ypoints[i];
		}
		g.drawPolygon(xx, yy, 4);
	}
}
