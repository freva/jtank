package gui.animation;

import game.Game;

import java.awt.Graphics;

public class Animation {
	private long startTime;
	private DataAnimation da;
	private int x, y;
	
	public Animation(int x, int y, DataAnimation da){
		this.da = da;
		this.x = x-(da.getWidth()>>1);
		this.y = y-(da.getHeight()>>1);
		
		startTime = System.currentTimeMillis();
	}
	
	public void drawFrame(Graphics g){
		int curFrame = (int) (System.currentTimeMillis()-startTime)>>7;
		if(curFrame == da.getNumFrames()) {
			Game.getInstance().removeAnimation(this);
			return;
		}
		
		g.drawImage(da.getFrame(curFrame), x, y, null);
	}
}
