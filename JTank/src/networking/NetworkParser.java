package networking;

import game.Game;
import objects.AbstractElementary;
import objects.DataWeapon;
import objects.Tank;
import objects.WeaponElementary;

public class NetworkParser {
	public static void parseData(String s) {
		String[] data = s.split("%");
		for(int i=0; i<data.length; i++){
			String[] temp = data[i].split("£");
			
			if(temp[1].indexOf(Game.getInstance().getPlayer().getUsername()) == 0) continue;
			switch(Integer.parseInt(temp[0])){
			case 1:
				parseObject(temp[1]);
			break;
			case 2:
				parseDestroy(temp[1]);
			break;
			
			}
		}
	}
	
	
	public static void parseObject(String s){
		String[] data = s.split("@");
		//getElementID() + "@" + id + "@" + getX() + "@" + getY() + "@" + dx + "@" + dy;
		//super.toString() + "@" + deg + "@" + currentWeapon + "@" + weapons[currentWeapon].getAmount();
		
		if(Integer.parseInt(data[1]) == 0){
			Tank t = (Tank) Game.getInstance().getElement(data[0]);
			if(t == null) {
				t = new Tank(Float.parseFloat(data[2]), Float.parseFloat(data[3]), data[0]);
				Game.getInstance().addElement(t);
			} else t.setPosition(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
			
			t.setSpeed(Float.parseFloat(data[4]), Float.parseFloat(data[5]));
			t.setDeg(Float.parseFloat(data[6]));
			t.setCurrentWeapon(Integer.parseInt(data[7]));
			t.setAmmoForWeapon(Integer.parseInt(data[7]), Integer.parseInt(data[8]));
		} else {
			Game.getInstance().addElement(new WeaponElementary(Float.parseFloat(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]), DataWeapon.getWeapon(Integer.parseInt(data[1])), data[0]));
		}
	}
	
	public static void parseDestroy(String s){
		String[] data = s.split("@");
		
		AbstractElementary ae = Game.getInstance().getElement(data[0]);
		if(ae == null) return;
		
		ae.setPosition(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
		Game.getInstance().explodeElement(ae);
	}
}
