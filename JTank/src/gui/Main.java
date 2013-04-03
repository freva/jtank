package gui;

import game.Game;
import game.Level;
import game.LevelImage;
import gui.menus.AbstractMenu;
import gui.menus.MenuMain;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Main extends JFrame{
	public static Main main;
	public static final int GAME_WIDTH = 1280, GAME_HEIGHT = 720;
	
	private static String version = "r4";
	private AbstractMenu menuMain;
	private JLayeredPane lp;
	
	public static void main(String[] args) {
		main = new Main();
		
		main.setTitle("JTanks " + version);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		main.setSize(GAME_WIDTH, GAME_HEIGHT);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.getContentPane().setBackground(Color.gray);
		
		main.init();
	}
	
	public void init(){
		lp = new JLayeredPane();
		menuMain = new MenuMain();
		menuMain.setVisible(true);
		
		lp.add(menuMain, 1);
		
		main.getContentPane().add(lp);
	}
	
	public void hideMenu(){
		lp.setVisible(false);
	}
}
