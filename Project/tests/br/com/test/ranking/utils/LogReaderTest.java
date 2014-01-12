package br.com.test.ranking.utils;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import br.com.test.ranking.beans.Action;
import br.com.test.ranking.beans.BeginMatch;
import br.com.test.ranking.beans.EndMatch;
import br.com.test.ranking.beans.Kill;
import br.com.test.ranking.exceptions.CriticalException;

public class LogReaderTest {

	private BufferedReader reader;
	private Logger logger;

	@Before
	public void setUp() throws Exception {
		reader = mock( BufferedReader.class );
		
		logger = mock( Logger.class );
		RankingProjectLogger.setLogger(logger);
		
		when( reader.ready() ).thenReturn( true );
	}

	/*
	 * Por entender que os métodos de construção das actions dos logs serem os mais importantes, criei testes mesmo eles sendo privados.
	 */
	@Test
	public final void getBeginMatchAction_validStringAction_returnBeginAction() throws Exception{
		
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getBeginMatchAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "23/04/2013 15:34:22 - New match 11348965 has started");
		
		Assert.assertTrue( obj instanceof BeginMatch );
		
	}
	
	@Test
	public final void getBeginMatchAction_invalidStringAction_returnNullsAndLogIsCalled() throws Exception{
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getBeginMatchAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "wrong action");
		
		Assert.assertNull( obj );
		verify( logger ).error( anyString(), any( Throwable.class) );
	}
	
	@Test
	public final void getBeginMatchAction_AlmostValidStringAction_returnNullsAndLogIsCalled() throws Exception{
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getBeginMatchAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "23/04/2013 15:34:22 - New match 11348965 and 2 has started");
		
		Assert.assertNull( obj );
		verify( logger ).error( anyString(), any( Throwable.class) );
	}
	
	@Test
	public final void getEndMatchAction_validStringAction_returnEndAction() throws Exception{
		
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getEndMatchAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "23/04/2013 15:39:22 - Match 11348965 has ended");
		
		Assert.assertTrue( obj instanceof EndMatch );
		
	}
	
	@Test
	public final void getEndMatchAction_invalidStringAction_returnNullsAndLogIsCalled() throws Exception{
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getEndMatchAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "wrong action");
		
		Assert.assertNull( obj );
		verify( logger ).error( anyString(), any( Throwable.class) );
	}
	
	@Test
	public final void getEndMatchAction_almostValidStringAction_returnNullsAndLogIsCalled() throws Exception{
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getEndMatchAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "23/04/2013 - Match 11348965 has ended");
		
		Assert.assertNull( obj );
		verify( logger ).error( anyString(), any( Throwable.class) );
	}
	
	@Test
	public final void getKillAction_validStringAction_returnKillAction() throws Exception{
		
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getKillAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "23/04/2013 15:36:04 - Roman killed Nick using M16");
		
		Assert.assertTrue( obj instanceof Kill );
		
	}
	
	@Test
	public final void getKillAction_invalidStringAction_returnNullsAndLogIsCalled() throws Exception{
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getKillAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "wrong action");
		
		Assert.assertNull( obj );
		verify( logger ).error( anyString(), any( Throwable.class) );
	}
	
	@Test
	public final void getKillAction_almostValidStringAction_returnNullsAndLogIsCalled() throws Exception{
		//Testando método private via reflection
		Method method = LogReader.class.getDeclaredMethod("getKillAction", String.class);
		method.setAccessible(true);
		Object obj = method.invoke(null, "23/04/2013 15:36:04 - Roman killed Nick");
		
		Assert.assertNull( obj );
		verify( logger ).error( anyString(), any( Throwable.class) );
	}
	
	@Test( expected = CriticalException.class )
	public final void readActions_invalidFileStream_throwCriticalException() throws Exception {
		//configurando
		when( reader.ready() ).thenReturn( false );
		
		//testando
		LogReader.readActions(reader);
	}
	
	@Test
	public final void readActions_oneBeginMatch_returnListWithOneBeginMatch() throws Exception{
		//configurando
		when( reader.readLine() ).thenReturn( "23/04/2013 15:34:22 - New match 11348965 has started", (String)null );
		
		//testando
		List<Action> actions = LogReader.readActions(reader);
		
		//verificando
		Assert.assertTrue( "Não tem nenhum action" , actions.size() == 1 );
		Assert.assertTrue( "Não tem uma action BeginMatch" , actions.get( 0 ).getClass() == BeginMatch.class );
	}
	
	@Test
	public final void readActions_oneKill_returnListWithOneKill() throws Exception{
		//configurando
		when( reader.readLine() ).thenReturn( "23/04/2013 15:36:04 - Roman killed Nick using M16", (String)null );
		
		//testando
		List<Action> actions = LogReader.readActions(reader);
		
		//verificando
		Assert.assertTrue( "Não tem nenhum action" , actions.size() == 1 );
		Assert.assertTrue( "Não tem uma action Kill" , actions.get( 0 ).getClass() == Kill.class );
	}
	
	@Test
	public final void readActions_oneEndMatch_returnListWithOneEndMatch() throws Exception{
		//configurando
		when( reader.readLine() ).thenReturn( "23/04/2013 15:39:22 - Match 11348965 has ended", (String)null );
		
		//testando
		List<Action> actions = LogReader.readActions(reader);
		
		//verificando
		Assert.assertTrue( "Não tem nenhum action" , actions.size() == 1 );
		Assert.assertTrue( "Não tem uma action EndMatch" , actions.get( 0 ).getClass() == EndMatch.class );
	}
	
	@Test
	public final void readActions_oneWorldKill_returnOneNullAction() throws Exception{
		//configurando
		when( reader.readLine() ).thenReturn( "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN", (String)null );
		
		//testando
		List<Action> actions = LogReader.readActions(reader);
		
		//verificando
		Assert.assertTrue( "Não tem nenhum action" , actions.size() == 1 );
		Assert.assertTrue( "Não ignorou o action" , actions.get( 0 ) == null );
	}
	
}