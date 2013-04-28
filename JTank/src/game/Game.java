package game;

import game.controls.PlayerControls;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import objects.Tank;
import objects.AbstractElementary;

public class Game extends JPanel implements Runnable {
	private static Tank player;
	private static ArrayList<AbstractElementary> objects = new ArrayList<AbstractElementary>();
	private int dTime = 1, minFPS = 30;
	private InputMap inputMap = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
    private ActionMap actionMap = this.getActionMap();
	
	public Game(Level level){	
		player = new Tank(350, 100);
		objects.add(player);
		
		Thread t = new Thread(this);
		t.start();
		
		bindGameKeys();
	}
	
	public void run(){
		long startTime = System.currentTimeMillis();
		
		while(true){
			startTime = System.currentTimeMillis();
			
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
	
	private void bindGameKeys(){
		inputMap.put(KeyStroke.getKeyStroke("UP"), "UpArrow");
		inputMap.put(KeyStroke.getKeyStroke("DOWN"), "DownArrow");
		inputMap.put(KeyStroke.getKeyStroke("LEFT"), "LeftArrow");
		inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "RightArrow");
		inputMap.put(KeyStroke.getKeyStroke("SPACE"), "Space");
		
		actionMap.put("UpArrow", new PlayerControls(1));
		actionMap.put("DownArrow", new PlayerControls(2));
		actionMap.put("LeftArrow", new PlayerControls(3));
		actionMap.put("RightArrow", new PlayerControls(4));
		actionMap.put("Space", new PlayerControls(5));
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Level.getInstance().getBackgroundImage(), 0, 0, null);
        g.drawImage(Level.getInstance().getLevelImage(), 0, 0, null);
        
        for(AbstractElementary ae : objects) ae.paint(g);
        
        g.drawString("FPS: " + 1000/dTime, 10, 10);
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
}
