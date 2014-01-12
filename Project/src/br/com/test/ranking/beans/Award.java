package br.com.test.ranking.beans;

public abstract class Award {

	private Match match;

	public Award( Match match ){
		this.match = match;
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	public String toString(){
		return this.getClass().getSimpleName() + " ganho na partida: " + match.getIdentifier();
	}
}
