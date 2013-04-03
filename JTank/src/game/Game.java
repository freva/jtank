package game;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Game extends JPanel {
	private Level level;
	
	public Game(Level level){
		this.level = level;
	}

	public void test(){
		
	}
	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(level.levelImage.getBackground().getImage(), 0, 0, null);
        g.drawImage(level.levelImage.getLevel().getImage(), 0, 0, null);
    }
}
