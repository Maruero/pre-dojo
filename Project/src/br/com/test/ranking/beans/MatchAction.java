package br.com.test.ranking.beans;

import java.util.Date;

public abstract class MatchAction extends Action{

	private String identifier;
	
	public MatchAction(){
		
	}
	
	public MatchAction(String identifier, Date time){
		super(time);
		this.identifier = identifier;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
