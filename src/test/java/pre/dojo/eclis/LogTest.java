package pre.dojo.eclis;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pre.dojo.eclis.util.Support;

public class LogTest {
	
	Log logValido = null;
	Log logInvalido = null;

	@Before
	public void setUp() throws Exception {
		logValido = Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16");
		logInvalido = Log.fromLine("23/04/2013 15:34:22 - New match 11348965 has started");
	}

	@Test
	public void deveValidarCondicoesDoRegexScoreParaPegarAsLinhasDeAssassinatoDoArquivoLogDoJogo() {
		boolean isLinhaDeAssassinato = Log.REGEX_SCORE.matcher("23/04/2013 15:36:04 - Roman killed Nick using M16").matches();
		assertTrue(isLinhaDeAssassinato);
		
		isLinhaDeAssassinato = Log.REGEX_SCORE.matcher("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN").matches();
		assertTrue(isLinhaDeAssassinato);
		
		isLinhaDeAssassinato = Log.REGEX_SCORE.matcher("23/04/2013 15:34:22 - New match 11348965 has started").matches();
		assertFalse(isLinhaDeAssassinato);
		
		isLinhaDeAssassinato = Log.REGEX_SCORE.matcher("").matches();
		assertFalse(isLinhaDeAssassinato);
	}
	
	@Test
	public void deveCriarLogAPartirDeUmaLinhaDoArquivoDeLog() {
		assertEquals("M16", logValido.arma);
		assertEquals("Roman", logValido.assassino);
		assertEquals(Support.parsedDate("23/04/2013 15:36:04"), logValido.data);
		assertEquals("Nick", logValido.vitima);
		assertNull(logInvalido);
	}
}
