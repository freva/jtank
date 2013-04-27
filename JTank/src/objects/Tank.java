package objects;

import gui.Main;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;

public class Tank extends AbstractElementary {
	private float rotate = 0;
	private boolean rotateChange=false;
	private BufferedImage barrel, barrelTemp;
	
	public Tank(float x, float y){
		super(x, y, ObjectsData.TANK);
		damping=0.6f;
		setSpeed(10, 10);
		
		try {
			barrel = ImageIO.read(Main.class.getResource("res/objects/barrel.png"));
			barrelTemp = barrel;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void rotate(float deg){
		rotate += deg;
		rotateChange = true;
	}


	public void paint(Graphics g) {
		if(rotateChange) {
		    AffineTransform tx = new AffineTransform();
		    tx.rotate(rotate, barrel.getWidth(), barrel.getHeight());

		    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		    barrelTemp = op.filter(barrel, null);
		    rotateChange = false;
		}
		
		g.drawImage(image, this.getX()+polyObject.xpoints[3], this.getY()+polyObject.ypoints[0], null);
		g.drawImage(barrelTemp, this.getX()+polyObject.xpoints[3], this.getY()+polyObject.ypoints[0], null);
	}
}
