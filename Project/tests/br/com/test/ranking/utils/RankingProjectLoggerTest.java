package br.com.test.ranking.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class RankingProjectLoggerTest {

	private static Logger logger;
	
	@BeforeClass
	public static void setUp() throws Exception {
		logger = mock( Logger.class );
		RankingProjectLogger.setLogger(logger);
	}

	@Test
	public final void log_message_loggerInfoCalled() {
		//testando
		RankingProjectLogger.log("Mensagem de texto");
		
		//verificando
		verify( logger ).info( "Mensagem de texto" );
	}
	
	@Test
	public final void log_messageAnTh_loggerErrorCalled() {
		//testando
		Exception ex = new Exception();
		RankingProjectLogger.log("Mensagem de texto", ex );
		
		//verificando
		verify( logger ).error( "Mensagem de texto" ,ex );
	}

}
