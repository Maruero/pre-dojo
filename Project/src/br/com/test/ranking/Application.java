package br.com.test.ranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.test.ranking.beans.Action;
import br.com.test.ranking.beans.Award;
import br.com.test.ranking.beans.BeginMatch;
import br.com.test.ranking.beans.EndMatch;
import br.com.test.ranking.beans.Kill;
import br.com.test.ranking.beans.Match;
import br.com.test.ranking.beans.Player;
import br.com.test.ranking.exceptions.CriticalException;
import br.com.test.ranking.exceptions.RankingProjectException;
import br.com.test.ranking.processors.BeginMatchProcessor;
import br.com.test.ranking.processors.EndMatchProcessor;
import br.com.test.ranking.processors.KillProcessor;
import br.com.test.ranking.processors.Processor;
import br.com.test.ranking.utils.DateParser;
import br.com.test.ranking.utils.LogReader;
import br.com.test.ranking.utils.RankingProjectLogger;

public class Application {

	public static void main(String[] args) throws RankingProjectException{
		
		if( args != null &&  args.length > 0 && !args[0].isEmpty() ){
			RankingProjectLogger.log( "Nova execução iniciada com parâmetro de entrada:");
			new Application().start(args[0]);
		}else{
			RankingProjectLogger.log( "Nova execução iniciada sem parâmetro de entrada:");
			new Application().start("exemplosDeEntrada\\match.log");
		}
		
		RankingProjectLogger.log( "Término\n");
	}
	
	public void start( String path ) throws RankingProjectException{
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader( path ));
		}catch(IOException ex){
			String message = "Problema ao tentar abrir o arquivo: ";
			RankingProjectLogger.log( message, ex );
			throw new CriticalException(message, ex);
		}
		
		List<Action> actions = LogReader.readActions(reader);
		Map<Class<? extends Action>, Processor> processors = buildProcessors();
		
		Processor processor = null;
		for( Action action : actions ){
			
			if(action == null){
				continue;
			}
			
			processor = processors.get(action.getClass());
			processor.process(action);
		}
		
		EndMatchProcessor endProcessor = null;
		if( processor instanceof EndMatchProcessor ){
			endProcessor = (EndMatchProcessor)processor;
		}else{
			
		}
		
		writeResult( endProcessor.getMatches() );
		
	}
	
	private void writeResult( List<Match> matches ){
		
		for( Match match : matches ){
			System.out.println( "---------------------------------------------------------------------------------");
			System.out.println( "Partida: " + match.getIdentifier() );
			System.out.println( "---------------------------------------------------------------------------------");
			System.out.println( " #### Vencedor #### " + match.getPlayers().get(0) );
			System.out.println( " - Arma favorita do vencedor: " + match.getPlayers().get(0).getWeapons().get( 0 ) );
			
			System.out.println( "Awards da partida:" );
			for( Player player : match.getPlayers() ){
				if( player.getAwards() != null && player.getAwards().size() > 0 ){
					System.out.println( " - Jogador: " + player.getName() + " obteve " + player.getAwards().size() + ", foram eles: " );
					for( Award award : player.getAwards() ){
						System.out.println( "	* " + award );
					}
				}
			}
			System.out.println( "Maiores sequencias da partida:" );
			for( Player player : match.getPlayers() ){
				System.out.println( " - Jogador: " + player.getName() );
				if( player.getMaxKillsInARow() != null && player.getMaxKillsInARow().size() > 0 ){
					System.out.println("		De " + DateParser.format(player.getMaxKillsInARow().get( 0 ).getTime()) + " até " + DateParser.format(player.getMaxKillsInARow().get( player.getMaxKillsInARow().size() -1 ).getTime()) + " - mortes: ");
					System.out.print("			");
					for( Kill kill : player.getMaxKillsInARow() ){
						System.out.print(kill.getKilled().getName() + ", ");
					}
					System.out.print("\n");
				}else{
					System.out.println("		Não matou ninguém");
				}
			}
			
			
			System.out.println( "" );
		}
		
		
	}
	
	private Map<Class<? extends Action>, Processor> buildProcessors(){
		Map<Class<? extends Action>, Processor> processors = new HashMap<Class<? extends Action>, Processor>();
		
		KillProcessor killProcessor = new KillProcessor();
		
		processors.put( Kill.class, killProcessor );
		processors.put( BeginMatch.class, new BeginMatchProcessor(killProcessor));
		processors.put( EndMatch.class, new EndMatchProcessor(killProcessor));
		
		return processors;
	}
	
}
