package game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import objects.Tank;
import objects.AbstractElementary;

public class Game extends JPanel implements MouseListener, Runnable {
	private Level level;
	private Tank player;
	private ArrayList<AbstractElementary> objects = new ArrayList<AbstractElementary>();
	private int dTime = 1, minFPS = 30;
	
	public Game(Level level){
		this.level = level;
		addMouseListener(this);
		
		player = new Tank();
		objects.add(player);
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		
		while(true){
			startTime = System.currentTimeMillis();
			
	        for(AbstractElementary ae : objects) ae.tick(dTime);
	        repaint();
	        
			if(System.currentTimeMillis()-startTime < minFPS){
				try {
					Thread.sleep(minFPS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

	        dTime = (int) (System.currentTimeMillis()-startTime);
		}
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(level.getBackgroundImage(), 0, 0, null);
        g.drawImage(level.getLevelImage(), 0, 0, null);
        
        for(AbstractElementary ae : objects) ae.paint(g);
        
        g.drawString("FPS: " + 1000/dTime, 10, 10);
    }

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	
	public void mouseReleased(MouseEvent arg0) {
		level.drawCircle(arg0.getX(), arg0.getY(), 50);
		repaint();
	}
}
