package gui;

import gui.menus.AbstractMenu;
import gui.menus.MenuMain;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Main{
	public static Main main;
	public static JFrame mainFrame;
	public static final int GAME_WIDTH = 1280, GAME_HEIGHT = 720, maxNumPlayers=10;
	
	private static String version = "r22";
	private AbstractMenu menuMain;
	private JLayeredPane lp;
	
	public static void main(String[] args) {
		main = new Main();
		main.init();
	}
	
	public void init(){
		menuMain = new MenuMain();
		lp = new JLayeredPane();
		lp.add(menuMain, new Integer(0));

		mainFrame = new JFrame();
		mainFrame.add(lp);
		mainFrame.setTitle("JTanks " + version);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.getContentPane().setBackground(Color.gray);
		mainFrame.pack();
		mainFrame.setSize(GAME_WIDTH+mainFrame.getInsets().left+mainFrame.getInsets().right, GAME_HEIGHT+mainFrame.getInsets().top+mainFrame.getInsets().bottom);
		mainFrame.setLocationRelativeTo(null);
	}
	
	public void hideMenu(){
		lp.setVisible(false);
	}
	
	public void addMenuToForeground(AbstractMenu am){
		lp.add(am, new Integer(lp.highestLayer()+1));
	}
	
	public void removeTopMenu() {
		lp.remove(new Integer(lp.highestLayer()-1));
		lp.repaint();
	}
}
