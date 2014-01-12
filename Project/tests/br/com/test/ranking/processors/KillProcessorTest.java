package br.com.test.ranking.processors;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.test.ranking.beans.FastestAward;
import br.com.test.ranking.beans.Kill;
import br.com.test.ranking.beans.Match;
import br.com.test.ranking.beans.Player;
import br.com.test.ranking.beans.Weapon;
import br.com.test.ranking.utils.LogReader;

public class KillProcessorTest {

	private KillProcessor processor;
	private Player maruero;
	private Player john;
	
	@Before
	public void setUp(){
		processor = new KillProcessor();
		
		Match match = new Match("match" , new Date());
		processor.setCurrentMath(match);
		
		maruero = new Player("Maruero");
		john = new Player( "John");
		
	}
	
	@Test
	public void processKill_MarueroKillJohn_resultsMarueryOneKill() throws Exception {
		
		//configurando
		Kill kill = new Kill(new Date(), maruero, john, "M15");
		
		//testando
		execute( "processKill" , kill );
		
		//verificando
		assertTrue(processor.getCurrentMath().getPlayers().get( 0 ).getKills().get( 0 ) == kill);
		assertTrue(processor.getCurrentMath().getPlayers().get( 0 ).getWeapons().get( 0 ).getName().equals( "M15" ));
		assertTrue(processor.getCurrentMath().getPlayers().get( 0 ).getWeapons().get( 0 ).getKillCount() == 1L);
		assertTrue(processor.getCurrentMath().getPlayers().get( 0 ).getKillsInARow().size() == 1);
		assertTrue(processor.getCurrentMath().getPlayers().get( 0 ).getKillsInARow().get( 0 ) == kill);
		
	}
	
	@Test
	public void processKill_MarueroKillJohn_resultsMarueryFiveKillsWithoutFastestAward() throws Exception {
		
		//configurando
		maruero.setKills( new ArrayList<Kill>() );
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 0 ), maruero , john , "M15"));
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 1 ), maruero , john , "M15"));
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 2 ), maruero , john , "M15"));
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 3 ), maruero , john , "M15"));
		maruero.setWeapons( new ArrayList<Weapon>() );
		maruero.getWeapons().add( new Weapon("M15"));
		maruero.getWeapons().get(0).setKillCount( 4L );
		maruero.setKillsInARow( new ArrayList<Kill>() );
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 0 ), maruero , john , "M15"));
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 1 ), maruero , john , "M15"));
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 2 ), maruero , john , "M15"));
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 3 ), maruero , john , "M15"));
		
		Kill kill = new Kill(new Date(), maruero , john , "M15");
		
		
		//testando
		execute( "processKill" , kill );
		
		//verificando
		assertTrue(maruero.getKillsInARow().size() == 5);
		assertTrue(maruero.getKills().size() == 5);
		assertTrue(maruero.getAwards() == null || maruero.getAwards().size() == 0 );
		
	}
	
	@Test
	public void processKill_MarueroKillJohn_resultsMarueryFiveKillsWithFastestAward() throws Exception {
		
		//configurando
		maruero.setKills( new ArrayList<Kill>() );
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 0 ), maruero , john , "M15"));
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 1 ), maruero , john , "M15"));
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 2 ), maruero , john , "M15"));
		maruero.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 3 ), maruero , john , "M15"));
		maruero.setWeapons( new ArrayList<Weapon>() );
		maruero.getWeapons().add( new Weapon("M15"));
		maruero.getWeapons().get(0).setKillCount( 4L );
		maruero.setKillsInARow( new ArrayList<Kill>() );
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 0 ), maruero , john , "M15"));
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 1 ), maruero , john , "M15"));
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 2 ), maruero , john , "M15"));
		maruero.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 3 ), maruero , john , "M15"));
		
		Kill kill = new Kill(new Date(114, 0 , 12 , 10, 20 , 4 ), maruero , john , "M15");
		
		
		//testando
		execute( "processKill" , kill );
		
		//verificando
		assertTrue(maruero.getKillsInARow().size() == 5);
		assertTrue(maruero.getKills().size() == 5);
		assertTrue(maruero.getAwards() == null || maruero.getAwards().size() == 1 );
		assertTrue(maruero.getAwards() == null || maruero.getAwards().get( 0 ) instanceof FastestAward );
		
	}
	
	@Test
	public void processDeath_MarueroKillJohn_resultsJohnWithOneDeathAndKillsInARowCleared() throws Exception {
		
		//configurando
		john.setKills( new ArrayList<Kill>() );
		john.getKills().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 0 ), john, maruero , "M15"));
		john.setKillsInARow( new ArrayList<Kill>() );
		john.getKillsInARow().add( new Kill(new Date(114, 0 , 12 , 10, 20 , 3 ), john, maruero , "M15"));
		
		Kill kill = new Kill(new Date(114, 0 , 12 , 10, 20 , 4 ), maruero , john , "M15");
		
		//testando
		execute( "processDeath" , kill );
		
		//verificando
		assertTrue(john.getDeathCount() == 1L);
		assertTrue(john.getKillsInARow().size() == 0);
	}
	
	private void execute( String methodStr , Kill kill ) throws Exception{
		Method method = KillProcessor.class.getDeclaredMethod(methodStr, Kill.class);
		method.setAccessible(true);
		Object obj = method.invoke(processor, kill);
	}

}
