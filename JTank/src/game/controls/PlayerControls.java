package game.controls;

import game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerControls implements KeyListener {
	private boolean[] pressed = new boolean[256];
	private long[] duration= new long[256];
	
	//Move left, angle up, move right, angle down, fire
	private int[] playerControls = new int[]{37, 38, 39, 40, 32, 16};

	public void keyPressed(KeyEvent arg0) {
		if(!pressed[arg0.getKeyCode()]){
			pressed[arg0.getKeyCode()] = true;
			duration[arg0.getKeyCode()] = System.currentTimeMillis();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		duration[arg0.getKeyCode()] = System.currentTimeMillis() - duration[arg0.getKeyCode()];
		pressed[arg0.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent arg0) { }
	
	public void checkInput(){
		if(pressed[playerControls[0]]) Game.getInstance().getPlayer().accelMove(-3);
		if(pressed[playerControls[1]]) Game.getInstance().getPlayer().rotate(0.03125f);
		if(pressed[playerControls[2]]) Game.getInstance().getPlayer().accelMove(3);
		if(pressed[playerControls[3]]) Game.getInstance().getPlayer().rotate(-0.03125f);
		if(!pressed[playerControls[4]] && duration[playerControls[4]]!=0) {
			Game.getInstance().getPlayer().fireWeapon((int) duration[playerControls[4]]);
			duration[playerControls[4]] = 0;
		}
		if(pressed[playerControls[5]]) Game.getInstance().getPlayer().nextWeapon();
	}
	
	public long getTimeKeyDown(int key){
		if(pressed[key]) return duration[key];
		return System.currentTimeMillis()+100;
	}
}
