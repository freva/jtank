package networking;

import game.Game;
import objects.DataWeapon;
import objects.Tank;
import objects.WeaponElementary;

public class ObjectUpdater {
	public static void interpretObject(String s){
		String[] data = s.split("@");
		
		//getElementID() + "@" + id + "@" + getX() + "@" + getY() + "@" + dx + "@" + dy;
		//super.toString() + "@" + deg + "@" + currentWeapon + "@" + weapons[currentWeapon].getAmount();
		
		if(Integer.parseInt(data[1]) == 0){
			Tank t = (Tank) Game.getElement(data[0]);
			if(t == null) {
				t = new Tank(Float.parseFloat(data[2]), Float.parseFloat(data[3]), data[0]);
				Game.addElement(t);
			} else t.setPosition(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
			
			t.setSpeed(Float.parseFloat(data[4]), Float.parseFloat(data[5]));
			t.setDeg(Float.parseFloat(data[6]));
			t.setCurrentWeapon(Integer.parseInt(data[7]));
			t.setAmmoForWeapon(Integer.parseInt(data[7]), Integer.parseInt(data[8]));
		} else {
			Game.addElement(new WeaponElementary(Float.parseFloat(data[2]), Float.parseFloat(data[3]), Float.parseFloat(data[4]), Float.parseFloat(data[5]), DataWeapon.getWeapon(Integer.parseInt(data[1])), data[0]));
		}
	}
}
