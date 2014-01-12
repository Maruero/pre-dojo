package br.com.test.ranking.processors;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BeginMatchProcessorTest.class, EndMatchProcessorTest.class,
		KillProcessorTest.class })
public class ProcessorTests {

}
