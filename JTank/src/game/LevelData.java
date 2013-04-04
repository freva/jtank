package game;

import java.net.URL;

import gui.Main;

public enum LevelData {
	KingKong("KingKong.png", "skies_bg.png"), 
	Mantis("Mantis.png", "skies_bg.png"), 
	Medieval("Medieval.png", "skies_bg.png");
	
	private String levelFilename, backgroundFilename;
	LevelData(String levelFilename, String backgroundFilename){
		this.levelFilename = levelFilename;
		this.backgroundFilename = backgroundFilename;
	}
	
	public URL getLevelFilename() {
		return Main.class.getResource("res/lvl/" + levelFilename);
	}
	public URL getBackgroundFilename() {
		return Main.class.getResource("res/lvl/" + backgroundFilename);
	}
}
