package game;

import game.controls.PlayerControls;
import gui.animation.Animation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

import objects.Tank;
import objects.AbstractElementary;

public class Game extends JPanel implements Runnable {
	private static Tank player;
	private static ArrayList<AbstractElementary> objects = new ArrayList<AbstractElementary>();
	private static ArrayList<Animation> animations = new ArrayList<Animation>();
	private int dTime = 1, minFPS = 30;
	private PlayerControls input = new PlayerControls();
	
	public Game(){	
		player = new Tank(550, 100);
		objects.add(player);
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		bindGameKeys();
		
		while(true){
			startTime = System.currentTimeMillis();
			
			input.checkInput();
	        for(int j=0; j<objects.size(); j++) objects.get(j).tick(dTime);
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
	
	private void bindGameKeys() {
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(input);
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Level.getInstance().getBackgroundImage(), 0, 0, null);
        g.drawImage(Level.getInstance().getLevelImage(), 0, 0, null);
        
        for(AbstractElementary ae : objects) ae.paint(g);
        for(int i=0; i<animations.size(); i++) animations.get(i).drawFrame(g);
    
        g.drawString("FPS: " + 1000/dTime, 10, 15);
    }
    
    public static Tank getPlayer(){
    	return player;
    }
    
    public static void addElement(AbstractElementary ae){
    	objects.add(ae);
    }
    
    public static void removeElement(AbstractElementary ae){
    	objects.remove(ae);
    }
    
    public static void addAnimation(Animation ani){
    	animations.add(ani);
    }
    
    public static void removeAnimation(Animation ani){
    	animations.remove(ani);
    }
}
