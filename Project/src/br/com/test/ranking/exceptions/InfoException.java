package br.com.test.ranking.exceptions;

public class InfoException extends RankingProjectException{

	private static final long serialVersionUID = 3119396787079862156L;

	
	public InfoException( String message ){
		super(message);
	}
		
	public InfoException( String message, Throwable th ){
		super(message, th);
	}
	
}
