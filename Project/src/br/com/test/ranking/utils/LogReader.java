package br.com.test.ranking.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.test.ranking.beans.Action;
import br.com.test.ranking.beans.BeginMatch;
import br.com.test.ranking.beans.EndMatch;
import br.com.test.ranking.beans.Kill;
import br.com.test.ranking.beans.Player;
import br.com.test.ranking.exceptions.CriticalException;
import br.com.test.ranking.exceptions.InfoException;
import br.com.test.ranking.exceptions.RankingProjectException;

public class LogReader {
	
	private static final String DEFAULT_IDENTIFIER = "XXX";
	private static final String BEGIN_MATCH_PATTERN = "XXX XXX - New match XXX has started";
	private static final String END_MATCH_PATTERN = "XXX XXX - Match XXX has ended";
	private static final String KILL_PATTERN = "XXX XXX - XXX killed XXX using XXX";
	
	public static List<Action> readActions( BufferedReader reader ) throws RankingProjectException{
		List<Action> actions = new ArrayList<Action>();
		
		try{
			if(!reader.ready()){
				throw new IOException("Stream não pronto para leitura.");
			}
		}catch(IOException ex){
			String message = "Problema ao tentar abrir o arquivo: ";
			RankingProjectLogger.log( message, ex );
			throw new CriticalException(message, ex);
		}
		
		try{
			String actionStr = null;
			while( (actionStr = reader.readLine()) != null ){
				Action action = getAction(actionStr);
				
				if(action == null){
					RankingProjectLogger.log( "Action ignorada: " + actionStr );
				}else{
					actions.add(action);
				}
			}
		}catch(IOException ex){
			String message = "Problema ao ler o arquivo: ";
			RankingProjectLogger.log( message, ex );
			throw new CriticalException(message, ex);
		}
		
		try{
			reader.close();
		}catch(IOException ex){
			String message = "Problema ao fechar o arquivo: ";
			RankingProjectLogger.log( message, ex );
			throw new CriticalException(message, ex);
		}
		
		return actions;
	}
	
	private static Action getAction( String actionStr){
		
		if( !actionStr.isEmpty() ){
			
			if( actionStr.contains( "New match" ) && actionStr.contains( "has started" )){
				return getBeginMatchAction(actionStr);
			}else if( actionStr.contains( "killed" ) && actionStr.contains( "using" )){
				return getKillAction(actionStr);
			}else if( actionStr.contains( "Match" ) && actionStr.contains( "has ended" )){
				return getEndMatchAction(actionStr);
			}
		}
		return null;
	}
	
	private static void validParse( String[] values, int validCount ) throws InfoException{
		if( values == null || values.length != validCount ){
			throw new InfoException("Valores retornados pelo parser não são válidos.");
		}
		
		for( String value : values ){
			if( value == null || value.isEmpty()){
				throw new InfoException("Valores retornados pelo parser não são válidos.");
			}
		}
	}
	
	private static BeginMatch getBeginMatchAction(String actionStr){
		try{
			
			String[] values = parse( BEGIN_MATCH_PATTERN , DEFAULT_IDENTIFIER , actionStr );
			
			validParse(values, 3);
			
			BeginMatch action = new BeginMatch();
			action.setTime(DateParser.readDate(values[0] + " " + values[1]));
			action.setIdentifier( values[2] );
		
			return action;
		}catch (InfoException ex) {
			RankingProjectLogger.log( "Problema lendo action de início de partida: " , ex );
			return null;
		}catch (Exception ex) {
			RankingProjectLogger.log( "Problema lendo action de início de partida: " , ex );
			return null;
		}
		
	}
	
	private static EndMatch getEndMatchAction(String actionStr){
		try{
			
			String[] values = parse( END_MATCH_PATTERN , DEFAULT_IDENTIFIER , actionStr );
			
			validParse(values, 3);
			
			EndMatch action = new EndMatch();
			action.setTime(DateParser.readDate(values[0] + " " + values[1]));
			action.setIdentifier( values[2] );
		
			return action;
		}catch (InfoException ex) {
			RankingProjectLogger.log( "Problema lendo action de início de partida: " , ex );
			return null;
		}catch (Exception ex) {
			RankingProjectLogger.log( "Problema lendo action de início de partida: " , ex );
			return null;
		}
	}
	
	private static Kill getKillAction(String actionStr){
		try{
			
			String[] values = parse( KILL_PATTERN , DEFAULT_IDENTIFIER , actionStr );
			
			validParse(values, 5);
			
			Kill action = new Kill();
			action.setTime(DateParser.readDate(values[0] + " " + values[1]));
			action.setKiller(new Player(values[2]));
			action.setKilled(new Player(values[3]));
			action.setWeapon(values[4]);
		
			return action;
		}catch (InfoException ex) {
			RankingProjectLogger.log( "Problema lendo action de kill: " , ex );
			return null;
		}catch (Exception ex) {
			RankingProjectLogger.log( "Problema lendo action de kill: " , ex );
			return null;
		}
	}
	
	private static String[] parse( String pattern, String identifier, String text){
		
		if( text.contains(identifier)){
			String newIdentifier = chooseAnotherId(text);
			pattern = pattern.replaceAll( identifier, newIdentifier);
			identifier = newIdentifier;
		}
		
		String[] patternPieces = pattern.split(identifier);
		
		for( String piece : patternPieces){
			text = StringUtils.remove( text , piece.trim());
		}
		
		return StringUtils.split(text);
	}
	
	private static String chooseAnotherId( String text ){
		String id = null;
		do{
			id = RandomStringUtils.random(3, true, true);
		}while( text.contains( id ));
		
		return id;
	}
	
}
