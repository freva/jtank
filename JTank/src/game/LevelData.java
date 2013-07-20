package game;

import java.net.URL;

import gui.Main;

public enum LevelData {
	KingKong("KingKong.png", "skies_bg.png"), 
	Mantis("Mantis.png", "skies_bg.png"), 
	Medieval("Medieval.png", "skies_bg.png"),
	FlatLand("FlatLand.png", "skies_bg.png");
	
	private URL levelFileURL, backgroundFileURL, levelPreviewURL;
	LevelData(String levelFileURL, String backgroundFileURL){
		this.levelFileURL = Main.class.getResource("res/lvl/" + levelFileURL);
		this.backgroundFileURL = Main.class.getResource("res/lvl/" + backgroundFileURL);
		this.levelPreviewURL = Main.class.getResource("res/lvl/preview_" + levelFileURL);
	}
	
	public URL getLevelURL() {
		return levelFileURL;
	}
	public URL getBackgroundURL() {
		return backgroundFileURL;
	}

	public URL getLevelPreviewURL() {
		return levelPreviewURL;
	}
}
