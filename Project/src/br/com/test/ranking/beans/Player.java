package br.com.test.ranking.beans;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player>{
	
	private String name;
	private ArrayList<Kill> kills;
	private Long deathCount = 0L;
	private List<Kill> killsInARow;
	private List<Kill> maxKillsInARow;
	
	private ArrayList<Award> awards;
	private Kill firstKillOfLastMinutes;
	private List<Weapon> weapons;
	
	public Player( String name ){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Kill> getKills() {
		return kills;
	}
	public void setKills(ArrayList<Kill> kills) {
		this.kills = kills;
	}
	public Long getDeathCount() {
		return deathCount;
	}
	public void setDeathCount(Long deathCount) {
		this.deathCount = deathCount;
	}
	public List<Kill> getKillsInARow() {
		return killsInARow;
	}
	public void setKillsInARow(List<Kill> killsInARowCount) {
		this.killsInARow = killsInARowCount;
	}
	public ArrayList<Award> getAwards() {
		return awards;
	}
	public void setAwards(ArrayList<Award> awards) {
		this.awards = awards;
	}
	public Kill getFirstKillOfLastMinutes() {
		return firstKillOfLastMinutes;
	}
	public void setFirstKillOfLastMinutes(Kill firstKillOfLastMinutes) {
		this.firstKillOfLastMinutes = firstKillOfLastMinutes;
	}
	public List<Weapon> getWeapons() {
		return weapons;
	}
	public void setWeapons(List<Weapon> weapons) {
		this.weapons = weapons;
	}
	
	public boolean equals( Object another ){
		if( another == null || !(another instanceof Player)){
			return false;
		}
		
		return name.equals( ((Player)another).getName());
	}
	
	public int hashCode(){
		return name.hashCode();
	}

	public List<Kill> getMaxKillsInARow() {
		return maxKillsInARow;
	}

	public void setMaxKillsInARow(List<Kill> maxKillsInARowCount) {
		this.maxKillsInARow = maxKillsInARowCount;
	}
	
	public String toString(){
		return name + " - kills: " + (kills != null ? kills.size(): 0) + " - deaths: " + deathCount;
	}

	public int compareTo(Player player) {
		return (player.kills != null ? player.kills.size() : 0) - (kills != null ? kills.size(): 0);
	}

}
