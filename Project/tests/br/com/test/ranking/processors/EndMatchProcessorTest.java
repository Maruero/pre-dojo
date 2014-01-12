package br.com.test.ranking.processors;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.test.ranking.beans.EndMatch;
import br.com.test.ranking.beans.ImortalAward;
import br.com.test.ranking.beans.Match;
import br.com.test.ranking.beans.Player;
import br.com.test.ranking.beans.Weapon;

public class EndMatchProcessorTest {

	private Player winner;
	private EndMatchProcessor processor;
	private Match currentMatch; 
	
	@Before
	public void setUp(){
		KillProcessor killProcessor = new KillProcessor();
		processor = new EndMatchProcessor(killProcessor);
		
		winner = new Player( "Maruero" );
		winner.setWeapons( new ArrayList<Weapon>());
		
		currentMatch = new Match("match", new Date());
		currentMatch.getPlayers().add( winner );
		
		killProcessor.setCurrentMath(currentMatch);
	}
	
	@Test
	public void process_winnerWithDeath_winnerWithoutImortalAward() {
		
		//configurando
		winner.setDeathCount(1L);
		EndMatch endMatch = new EndMatch("match", new Date());
		
		//verificando
		assertTrue(currentMatch.getEndTime( ) == null);
		
		//testando
		processor.process(endMatch);
		
		//verificando
		assertTrue( currentMatch.getEndTime( ) != null);
		assertTrue( currentMatch.getPlayers().get( 0 ).getAwards() == null || processor.getMatches().get( 0 ).getPlayers().get( 0 ).getAwards().size() == 0 );
		
		assertTrue( processor.getMatches().get( 0 ) == currentMatch );

	}
	
	@Test
	public void process_winnerWithoutDeath_winnerWithImortalAward() {
		
		//configurando
		winner.setDeathCount(0L);
		EndMatch endMatch = new EndMatch("match", new Date());
		
		//verificando
		assertTrue(currentMatch.getEndTime( ) == null);
		
		//testando
		processor.process(endMatch);
		
		//verificando
		assertTrue( currentMatch.getEndTime( ) != null);
		assertTrue( currentMatch.getPlayers().get( 0 ).getAwards().size() == 1 );
		assertTrue( currentMatch.getPlayers().get( 0 ).getAwards().get( 0 ) instanceof ImortalAward );
		
		assertTrue( processor.getMatches().get( 0 ) == currentMatch );

	}

}
