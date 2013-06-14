package game.controls;

import game.Game;
import gui.chat.Chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerControls implements KeyListener {
	private boolean[] pressed = new boolean[256];
	private boolean[] newAction = new boolean[256];
	private long[] duration = new long[256];
	private long[] cooldown = new long[256];
	private int cooldownWait = 25;
 	
	//Move left, angle up, move right, angle down, fire
	private int[] playerControls = new int[]{37, 38, 39, 40, 32, 16, 10};

	public void keyPressed(KeyEvent arg0) {
		if(!pressed[arg0.getKeyCode()]){
			pressed[arg0.getKeyCode()] = true;
			duration[arg0.getKeyCode()] = System.currentTimeMillis();
		}
		
		if(System.currentTimeMillis() > cooldown[arg0.getKeyCode()]) {
			newAction[arg0.getKeyCode()] = true;
			cooldown[arg0.getKeyCode()] = System.currentTimeMillis()+cooldownWait;
		} 
	}

	public void keyReleased(KeyEvent arg0) {
		if(pressed[arg0.getKeyCode()]) {
			duration[arg0.getKeyCode()] = System.currentTimeMillis() - duration[arg0.getKeyCode()];
			pressed[arg0.getKeyCode()] = false;
		}
	}

	public void keyTyped(KeyEvent arg0) { }
	
	public void checkInput(){
		//Continous pressing
		if(newAction[playerControls[0]]) Game.getInstance().getPlayer().accelMove(-3);
		if(newAction[playerControls[1]]) Game.getInstance().getPlayer().rotate(0.125f);
		if(newAction[playerControls[2]]) Game.getInstance().getPlayer().accelMove(3);
		if(newAction[playerControls[3]]) Game.getInstance().getPlayer().rotate(-0.125f);
		if(newAction[playerControls[5]]) Game.getInstance().getPlayer().nextWeapon();

		//Press and release
		if(!pressed[playerControls[4]] && duration[playerControls[4]]!=0) {
			Game.getInstance().getPlayer().fireWeapon((int) duration[playerControls[4]]);
			duration[playerControls[4]] = 0;
		}
		
		if(!pressed[playerControls[6]] && duration[playerControls[6]]!=0) {
			duration[playerControls[6]] = 0;
			Chat.changeChatBoxState();
		}
		
		for(int i=0; i<playerControls.length; i++) newAction[playerControls[i]] = false;
	}
	
	public boolean isMoving() {
		return pressed[playerControls[0]] || pressed[playerControls[2]];
	}
	
	public int getTimeKeyDown(int key){
		if(pressed[key]) return (int) (System.currentTimeMillis()-duration[key]);
		return 0;
	}
}
