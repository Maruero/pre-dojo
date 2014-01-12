package br.com.test.ranking.beans;

public class Weapon implements Comparable<Weapon>{

	private String name;
	private Long killCount = 0L;
	
	public Weapon( String name ){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getKillCount() {
		return killCount;
	}

	public void setKillCount(Long killCount) {
		this.killCount = killCount;
	}

	public boolean equals( Object another ){
		if( another == null || !(another instanceof Weapon )){
			return false;
		}
		
		Weapon anotherWeapon = (Weapon)another;
		return name.equals(anotherWeapon.name);
	}
	
	public int hashCode(){
		return killCount.hashCode();
	}
	
	public int compareTo(Weapon another) {
		return another.killCount.compareTo(killCount);
	}
	
	public String toString(){
		return name + " - kills: " + killCount;
	}
	
	
}
