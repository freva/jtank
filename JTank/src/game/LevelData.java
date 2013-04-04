package game;

public enum LevelData {
	KingKong("KingKong.png", "skies_bg.png"), 
	Mantis("Mantis.png", "skies_bg.png"), 
	Medieval("Medieval.png", "skies_bg.png");
	
	private String levelFilename, backgroundFilename;
	LevelData(String levelFilename, String backgroundFilename){
		this.levelFilename = levelFilename;
		this.backgroundFilename = backgroundFilename;
	}
	
	public String getLevelFilename() {
		return "res/lvl/" + levelFilename;
	}
	public String getBackgroundFilename() {
		return "res/lvl/" + backgroundFilename;
	}
}
