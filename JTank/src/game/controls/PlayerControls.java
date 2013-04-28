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
		// 1: UpArrow, 2: DownArrow, 3: LeftArrow, 4: RightArrow
		switch(key){
		case 1: Game.getPlayer().rotate(0.125f);
		break;
		
		case 2: Game.getPlayer().rotate(-0.125f);
		break;
		
		case 3: Game.getPlayer().accelMove(-3);
		break;
		
		case 4: Game.getPlayer().accelMove(3);
		break;
		
		case 5: Game.getPlayer().fireWeapon();
		break;
		}		
	}
}
