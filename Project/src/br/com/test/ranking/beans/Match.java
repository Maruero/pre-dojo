package br.com.test.ranking.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Match {

	private String identifier;
	private List<Player> players = new ArrayList<Player>();
	private Date beginTime;
	private Date endTime;
	
	public Match( String identifier , Date beginTime ){
		this.identifier = identifier;
		this.beginTime = beginTime;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
