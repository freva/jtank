package game;

import game.controls.PlayerControls;
import gui.Main;
import gui.animation.Animation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import gui.chat.Chat;
import objects.Tank;
import objects.AbstractElementary;

public class Game extends JPanel implements Runnable {
	protected static Tank player;
	private static ConcurrentHashMap<String, AbstractElementary> objects = new ConcurrentHashMap<String, AbstractElementary>();
	private static ArrayList<Animation> animations = new ArrayList<Animation>();
	private StringBuilder allUpdates = new StringBuilder();
	private PlayerControls input = new PlayerControls();
	private JProgressBar progress;
	private static Game game;
	
	public Game(String nickname){	
		player = new Tank((int) (Main.GAME_WIDTH*Math.random()), 100, nickname);
		objects.put(nickname, player);

		progress = new JProgressBar(JProgressBar.VERTICAL, 0, 80);
		this.add(progress);
		this.add(Chat.getInstance());
		game = this;

		Thread t = new Thread(this);
		t.start();
	}
	
	public void run(){
		long startTime, startFPSTime = 0;
		int dTime = 1;
		Main.mainFrame.addKeyListener(input);
		
		while(true){
			startTime = System.currentTimeMillis();
			input.checkInput();
			for(AbstractElementary ae : objects.values()) ae.tick(dTime);

			if(System.currentTimeMillis()-startFPSTime > 15) {
				startFPSTime = System.currentTimeMillis();
				repaint();
			}

			if(System.currentTimeMillis()-startTime < 1){
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) { e.printStackTrace(); }
			}

			dTime = (int) (System.currentTimeMillis()-startTime);
		}
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Level.getInstance().getBackgroundImage(), 0, 0, null);
        g.drawImage(Level.getInstance().getLevelImage(), 0, 0, null);
        
        for(AbstractElementary ae : objects.values()) ae.paint(g);
        for(int i=0; i<animations.size(); i++) animations.get(i).drawFrame(g);

        int timeDown = player.trimStrength(input.getTimeKeyDown(32))-10;
        progress.setValue(timeDown);
        progress.setLocation(1240, 550);
    }
    
    public Tank getPlayer(){
    	return player;
	}
    
    public void addElement(AbstractElementary ae){
    	objects.put(ae.getElementID(), ae);
		if(isOwnedByMe(ae)) addUpdate("1£" + ae.toString());
    }
    
    public void removeElement(AbstractElementary ae){
    	objects.remove(ae.getElementID());
    }
    
	public void explodeElement(AbstractElementary ae) {
		ae.explode();
		if(isOwnedByMe(ae)) addUpdate("2£" + ae.toString());
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

	public void addUpdate(String update) {
		allUpdates.append(update).append("%");
	}

	public String getUpdates() {
		String temp = allUpdates.toString();
		allUpdates = new StringBuilder();
		return temp;
	}

	public boolean isOwnedByMe(AbstractElementary ae){
		return ae.getElementID().indexOf(player.getUsername()) == 0;
	}
    
    public static Game getInstance() {
    	return game;
    }
}
