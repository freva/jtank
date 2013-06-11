package game;

import game.controls.PlayerControls;
import gui.animation.Animation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import objects.Tank;
import objects.AbstractElementary;

public abstract class Game extends JPanel implements Runnable {
	protected static Tank player;
	private static ConcurrentHashMap<String, AbstractElementary> objects = new ConcurrentHashMap<String, AbstractElementary>();
	private static ArrayList<Animation> animations = new ArrayList<Animation>();
	private int dTime = 1, minFPS = 10;
	private PlayerControls input = new PlayerControls();
	private JProgressBar progress;
	private static Game game;
	
	public Game(String nickname){	
		player = new Tank(550, 100, nickname);
		objects.put(nickname, player);

		progress = new JProgressBar(JProgressBar.VERTICAL, 0, 80);
		this.add(progress);
		this.game = this;

		Thread t = new Thread(this);
		t.start();
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		bindGameKeys();
		
		while(true){
			startTime = System.currentTimeMillis();
			
			input.checkInput();
			for(AbstractElementary ae : objects.values()) ae.tick(dTime);
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
        
        for(AbstractElementary ae : objects.values()) ae.paint(g);
        for(int i=0; i<animations.size(); i++) animations.get(i).drawFrame(g);
    
        g.drawString("FPS: " + 1000/dTime, 10, 15);
        
        int timeDown = player.trimStrength((int) (System.currentTimeMillis()-input.getTimeKeyDown(32)))-10;
        progress.setValue(timeDown);
        progress.setLocation(20, 550);
    }
    
    public Tank getPlayer(){
    	return player;
    }
    
    public void setPlayer(Tank t) {
    	player = t;
    }
    
    public void addElement(AbstractElementary ae){
    	objects.put(ae.getElementID(), ae);
    }
    
    public void removeElement(AbstractElementary ae){
    	objects.remove(ae.getElementID());
    }
    
	public void explodeElement(AbstractElementary ae) {
		ae.explode();
	}
	
    public AbstractElementary getElement(String elementID){
    	return objects.get(elementID);
    }
    
    public Collection<AbstractElementary> getElements() {
    	return objects.values();
    }
    
    public void addAnimation(Animation ani){
    	animations.add(ani);
    }
    
    public void removeAnimation(Animation ani){
    	animations.remove(ani);
    }
    
    public static Game getInstance() {
    	return game;
    }
}
