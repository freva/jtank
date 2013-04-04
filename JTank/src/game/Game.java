package game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener {
	private Level level;
	
	public Game(Level level){
		this.level = level;
		addMouseListener(this);
	}

	
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(level.getBackgroundImage(), 0, 0, null);
        g.drawImage(level.getLevelImage(), 0, 0, null);
    }


	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	
	public void mouseReleased(MouseEvent arg0) {
		level.drawCircle(arg0.getX(), arg0.getY(), 50);
		repaint();
	}
}
