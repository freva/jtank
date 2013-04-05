package gui;

import gui.menus.AbstractMenu;
import gui.menus.MenuMain;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Main{
	public static Main main;
	public static JFrame mainFrame;
	public static final int GAME_WIDTH = 1280, GAME_HEIGHT = 720;
	
	private static String version = "r7";
	private AbstractMenu menuMain;
	private JLayeredPane lp;
	
	public static void main(String[] args) {
		main = new Main();
		main.init();
	}
	
	public void init(){
		menuMain = new MenuMain();
		lp = new JLayeredPane();
		lp.add(menuMain, 1);

		mainFrame = new JFrame();
		mainFrame.add(lp);
		mainFrame.setTitle("JTanks " + version);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setSize(GAME_WIDTH+6, GAME_HEIGHT+29);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.gray);
	}
	
	public void hideMenu(){
		lp.setVisible(false);
	}
}
