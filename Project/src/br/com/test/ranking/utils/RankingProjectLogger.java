package br.com.test.ranking.utils;

import org.apache.log4j.Logger;

public class RankingProjectLogger {

	private static Logger logger = Logger.getLogger( RankingProjectLogger.class );
	
	public static void log( String message ){
		logger.info(message);
	}
	
	public static void log( String message , Throwable th ){
		logger.error(message, th);
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		RankingProjectLogger.logger = logger;
	}
	
}
