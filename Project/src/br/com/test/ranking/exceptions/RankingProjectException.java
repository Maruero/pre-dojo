package br.com.test.ranking.exceptions;

public class RankingProjectException extends Exception {

	private static final long serialVersionUID = 9120285609159688730L;

	public RankingProjectException( String message ){
		super(message);
	}
	
	public RankingProjectException( String messagem, Throwable th ){
		super(messagem, th);
	}
}
