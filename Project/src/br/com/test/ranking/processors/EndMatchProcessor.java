package br.com.test.ranking.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.test.ranking.beans.Action;
import br.com.test.ranking.beans.Award;
import br.com.test.ranking.beans.EndMatch;
import br.com.test.ranking.beans.ImortalAward;
import br.com.test.ranking.beans.Match;
import br.com.test.ranking.beans.Player;

public class EndMatchProcessor extends Processor{

	private KillProcessor killProcessor;
	private List<Match> matches = new ArrayList<Match>();
	
	public EndMatchProcessor( KillProcessor killProcessor ){
		this.killProcessor = killProcessor;
	}
	
	public void process(Action action) {
		
		EndMatch end = (EndMatch)action;
		
		Match match = this.killProcessor.getCurrentMath();
		Collections.sort( match.getPlayers() );
		Collections.sort(match.getPlayers().get(0).getWeapons());
		
		processImortalAward(match);
		
		match.setEndTime( end.getTime() );
		this.matches.add(match);
		this.killProcessor.setCurrentMath( null );
	}

	private void processImortalAward( Match match ){
		Player winner = match.getPlayers().get( 0 );
		
		if( winner.getDeathCount() == 0 ){
			if(winner.getAwards() == null ){
				winner.setAwards(new ArrayList<Award>());
			}
			winner.getAwards().add( new ImortalAward(match));
		}
	}
	
	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

}
