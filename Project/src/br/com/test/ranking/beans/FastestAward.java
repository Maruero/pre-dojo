package br.com.test.ranking.beans;

import java.util.ArrayList;

import br.com.test.ranking.utils.DateParser;

public class FastestAward extends Award{

	private ArrayList<Kill> kills;

	public FastestAward( Match match ){
		super( match );
	}
	
	public ArrayList<Kill> getKills() {
		return kills;
	}

	public void setKills(ArrayList<Kill> kills) {
		this.kills = kills;
	}
	
	public String toString(){
		StringBuilder str = new StringBuilder(super.toString() + "\n");
		
		str.append("		De " + DateParser.format(kills.get( 0 ).getTime()) + " até " + DateParser.format(kills.get( kills.size() -1 ).getTime()) + " - mortes:");
		for( Kill kill : kills ){
			str.append(kill.getKilled().getName() + ", ");
		}
		str.delete(str.length()-1, str.length());
		
		return str.toString();
	}
	
}
