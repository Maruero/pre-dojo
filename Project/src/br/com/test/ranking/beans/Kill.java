package br.com.test.ranking.beans;

import java.util.Date;

public class Kill extends Action{

	private Player killer;
	private Player killed;
	private String weapon;
	
	public Kill(){
		
	}
	
	public Kill( Date time , Player killer, Player killed, String weapon ){
		super(time);
		this.killer = killer;
		this.killed = killed;
		this.weapon = weapon;
	}
	
	public Player getKiller() {
		return killer;
	}
	public void setKiller(Player killer) {
		this.killer = killer;
	}
	public Player getKilled() {
		return killed;
	}
	public void setKilled(Player killed) {
		this.killed = killed;
	}
	public String getWeapon() {
		return weapon;
	}
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	
	
	
}
