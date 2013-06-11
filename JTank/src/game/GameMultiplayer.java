package game;

import objects.AbstractElementary;

public class GameMultiplayer extends Game {
	private boolean isHost;
	private static StringBuilder allUpdates = new StringBuilder();
	
	public GameMultiplayer(String nickname, boolean isHost) {
		super(nickname);
		
		this.isHost = isHost;
	}
	
	public static void addUpdate(String update) {
		allUpdates.append(update + "%");
	}
	
	public static String getUpdates() {
		String temp = allUpdates.toString();
		allUpdates = new StringBuilder();
		return temp;
	}

    public void addElement(AbstractElementary ae){
    	super.addElement(ae);
    	if(ae.getElementID().indexOf(player.getUsername()) == 0) addUpdate("1£" + ae.toString());
    }

	public void explodeElement(AbstractElementary ae) {
		if(ae.getElementID().indexOf(player.getUsername()) == -1) removeElement(ae);
		super.explodeElement(ae);
		addUpdate("2£" + ae.toString());
	}
}
