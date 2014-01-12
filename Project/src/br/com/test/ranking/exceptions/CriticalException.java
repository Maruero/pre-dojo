package br.com.test.ranking.exceptions;

public class CriticalException extends RankingProjectException{

	private static final long serialVersionUID = 1L;

	public CriticalException( String messagem, Throwable th ){
		super(messagem, th);
	}
	
}
