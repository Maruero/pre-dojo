package br.com.test.ranking.processors;

import java.util.ArrayList;

import br.com.test.ranking.beans.Action;
import br.com.test.ranking.beans.Award;
import br.com.test.ranking.beans.FastestAward;
import br.com.test.ranking.beans.Kill;
import br.com.test.ranking.beans.Match;
import br.com.test.ranking.beans.Player;
import br.com.test.ranking.beans.Weapon;

public class KillProcessor extends Processor{
	
	private Match currentMath;
	private static final int MINUTE_DIVISOR = 1000 * 60;
	
	public void process( Action action ){
		
		Kill kill = (Kill)action;
		
		processKill(kill);
		processDeath(kill);
		
	}
	
	private void processDeath( Kill kill ){
		
		Player killed = null;
		
		if( !currentMath.getPlayers().contains( kill.getKilled() ) ){
			currentMath.getPlayers().add( kill.getKilled() );
		}
		killed = new ArrayList<Player>( currentMath.getPlayers() ).get( new ArrayList<Player>( currentMath.getPlayers() ).indexOf( kill.getKilled() ) );
		
		
		killed.setDeathCount( killed.getDeathCount() +1 );
		if(killed.getKillsInARow() != null ){
			killed.getKillsInARow().clear();
		}
	}
	
	private void processKill( Kill kill ){

		Player killer = null;
		
		if( !currentMath.getPlayers().contains( kill.getKiller() ) ){
			currentMath.getPlayers().add( kill.getKiller() );
		}
		killer = new ArrayList<Player>( currentMath.getPlayers() ).get( new ArrayList<Player>( currentMath.getPlayers() ).indexOf( kill.getKiller() ) );
		kill.setKiller(killer);
		
		if(killer.getKills() == null ){
			killer.setKills( new ArrayList<Kill>() );
			killer.setKillsInARow(new ArrayList<Kill>());
			killer.setWeapons( new ArrayList<Weapon>() );
			killer.setFirstKillOfLastMinutes(kill);
		}
		
		killer.getKills().add(kill);
		
		Weapon weapon = new Weapon(kill.getWeapon());
		if(!killer.getWeapons().contains( weapon ) ){
			killer.getWeapons().add( weapon );
		}
		weapon = killer.getWeapons().get( killer.getWeapons().indexOf( weapon ));
		weapon.setKillCount( weapon.getKillCount() +1 );
		
		killer.getKillsInARow().add( kill );
		if( killer.getMaxKillsInARow() == null || killer.getKillsInARow().size() > killer.getMaxKillsInARow().size() ){
			killer.setMaxKillsInARow( killer.getKillsInARow() );
		}
		
		processFastestAward(kill);
		
	}
	
	private void processFastestAward( Kill kill ){
		Player killer = kill.getKiller();
		
		if( killer.getFirstKillOfLastMinutes() == null ){
			killer.setFirstKillOfLastMinutes( kill );
			return;
		}
		
		long differenceInMinutes = 0;
		do{
			differenceInMinutes = (int) Math.floor((kill.getTime().getTime() / (double)MINUTE_DIVISOR) - (killer.getFirstKillOfLastMinutes().getTime().getTime() / (double)MINUTE_DIVISOR));
			
			if(differenceInMinutes > 0 ){
				int posi = killer.getKills().indexOf( killer.getFirstKillOfLastMinutes() );
				killer.setFirstKillOfLastMinutes( killer.getKills().get( posi +1 ));
			}
			
		}while( differenceInMinutes > 0);
		
		if( killer.getKills().indexOf( killer.getFirstKillOfLastMinutes() ) == killer.getKills().size() -5 ){
			
			if( killer.getAwards() == null ){
				killer.setAwards( new ArrayList<Award>() );
			}
			FastestAward award = new FastestAward( currentMath );
			award.setKills( new ArrayList<Kill>( killer.getKills().subList(killer.getKills().indexOf( killer.getFirstKillOfLastMinutes() ), killer.getKills().size())) );
			killer.getAwards().add( award );
			
			killer.setFirstKillOfLastMinutes( null );
		}
	}

	public Match getCurrentMath() {
		return currentMath;
	}

	public void setCurrentMath(Match currentMath) {
		this.currentMath = currentMath;
	}

}
