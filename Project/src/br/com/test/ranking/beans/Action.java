package br.com.test.ranking.beans;

import java.util.Date;

public abstract class Action {

	private Date time;
	public Action( Date time ){
		this.time = time;
	}
	
	public Action(){
		
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
