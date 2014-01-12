package br.com.test.ranking.processors;

import br.com.test.ranking.beans.Action;
import br.com.test.ranking.beans.BeginMatch;
import br.com.test.ranking.beans.Match;

public class BeginMatchProcessor extends Processor{

	private KillProcessor killProcessor;
	
	
	public BeginMatchProcessor( KillProcessor killProcessor ){
		this.killProcessor = killProcessor;
	}
	
	public void process(Action action) {
		
		BeginMatch begin = (BeginMatch)action;
		
		Match match = new Match(begin.getIdentifier(), begin.getTime() );
		this.killProcessor.setCurrentMath(match);
		
		
	}

	
	
}
