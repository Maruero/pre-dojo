package br.com.test.ranking.processors;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.test.ranking.beans.BeginMatch;

public class BeginMatchProcessorTest {

	private KillProcessor killProcessor;
	private BeginMatchProcessor processor;
	
	@Before
	public void setUp(){
		killProcessor = new KillProcessor();
		processor = new BeginMatchProcessor(killProcessor);
	}
	
	@Test
	public void process_withBeginAction_configuresKillCorrectly() {
		//configurando
		BeginMatch match = new BeginMatch("Match", new Date());
		
		//testando
		processor.process(match);
		
		//verificando
		assertTrue( killProcessor.getCurrentMath().getIdentifier().equals( match.getIdentifier() ) );
		
	}

}
