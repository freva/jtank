package objects;

import game.Game;

import java.awt.Image;

public class WeaponInventory {
	private int amount;
	private DataWeapon dw;
	
	public WeaponInventory(int amount, DataWeapon dw) {	
		this.amount = amount;
		this.dw = dw;
	}
	
	public void fire(float x, float y, float deg, int strength) {
		if(isMore()){
			Game.addElement(new WeaponElementary(x, y, deg, strength, dw));
			amount--;
		}
	}

	public boolean isMore() {
		return amount != 0;
	}
	
	public String getAmountString(){
		if(amount > 0) return "" + amount;
		else return "-";
	}
	
	public Image getImageIcon() {
		return dw.getImageIcon();
	}
}
