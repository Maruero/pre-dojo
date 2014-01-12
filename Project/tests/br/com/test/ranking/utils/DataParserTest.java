package br.com.test.ranking.utils;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.test.ranking.exceptions.InfoException;

public class DataParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void readDate_specificDate_conversionOk() throws InfoException {
		//configurando
		@SuppressWarnings("deprecation")
		Date date = new Date( 114 , 0 , 12 , 10 , 34 , 15 );
		String dateStr = "12/01/2014 10:34:15";
		
		//testando
		Date result = DateParser.readDate(dateStr);
		
		//verificando
		assertEquals( result,  date );
	}
	
	@Test(expected = InfoException.class)
	public final void readDate_invalidDate_throwInfoExcepsion() throws InfoException {
		//configurando
		@SuppressWarnings("deprecation")
		String dateStr = "01/12 10:34:15";
		
		//testando
		Date result = DateParser.readDate(dateStr);
	}

	@Test
	public final void format_specificDate_conversionOk() {
		
		//configurando
		@SuppressWarnings("deprecation")
		Date date = new Date( 114 , 0 , 12 , 10 , 34 , 15 );
		String dateStr = "12/01/2014 10:34:15";
		
		//testando
		String result = DateParser.format(date);
		
		//verificando
		assertEquals( result,  dateStr );
	}

}
