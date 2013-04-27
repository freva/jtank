package game;

import game.controls.PlayerControls;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import objects.Tank;
import objects.AbstractElementary;

public class Game extends JPanel implements MouseListener, Runnable {
	private static Tank player;
	private ArrayList<AbstractElementary> objects = new ArrayList<AbstractElementary>();
	private int dTime = 1, minFPS = 30;
	private InputMap inputMap = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
    private ActionMap actionMap = this.getActionMap();
	
	public Game(Level level){
		addMouseListener(this);
		
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
	
	private void bindGameKeys(){
		inputMap.put(KeyStroke.getKeyStroke("UP"), "UpArrow");
		actionMap.put("UpArrow", new PlayerControls(1));
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

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	
	public void mouseReleased(MouseEvent arg0) {
		Level.getInstance().drawCircle(arg0.getX(), arg0.getY(), 50);
		repaint();
	}
}
