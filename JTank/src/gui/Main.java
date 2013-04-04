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
	
	private static String version = "r5";
	private AbstractMenu menuMain;
	private JLayeredPane lp;
	
	public static void main(String[] args) {
		main = new Main();		
		main.init();
	}
	
	public void init(){
		mainFrame = new JFrame();
		mainFrame.setTitle("JTanks " + version);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		mainFrame.setSize(GAME_WIDTH, GAME_HEIGHT);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.gray);
		
		
		lp = new JLayeredPane();
		menuMain = new MenuMain();

		lp.add(menuMain, 1);
		mainFrame.getContentPane().add(lp);
	}
	
	public void hideMenu(){
		lp.setVisible(false);
	}
}
