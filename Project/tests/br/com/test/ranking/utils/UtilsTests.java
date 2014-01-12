package br.com.test.ranking.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DataParserTest.class, LogReaderTest.class,
		RankingProjectLoggerTest.class })
public class UtilsTests {

}
