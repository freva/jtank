package game.controls;

import game.Game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PlayerControls extends AbstractAction {
	int key;
	
	public PlayerControls(int key){
		this.key = key;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(key==1){
			Game.getPlayer().rotate(0.2f);
		}
		
	}
}
