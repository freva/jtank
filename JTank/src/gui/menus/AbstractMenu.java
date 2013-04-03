package gui.menus;

import java.awt.Color;

import gui.Main;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class AbstractMenu extends JPanel {
	protected int width=500, height=500;
	
	public AbstractMenu(){
		this.setBounds((Main.GAME_WIDTH-width)>>1, (Main.GAME_HEIGHT-height)>>1, width, height);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 5)); 

	}
}
