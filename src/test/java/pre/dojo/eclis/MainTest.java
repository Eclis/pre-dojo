package pre.dojo.eclis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class MainTest {
	Main main;
	
	@Before
	public void setUp() throws Exception {
		main = new Main(new File(".")); 
	}

	@Test
	public void deveValidarRegexQueSelecionaLinhaDoLogParaComecarETerminarUmaPartida() {
		assertTrue(Main.REGEX_PARTIDA.matcher("23/04/2013 15:34:22 - New match 11348965 has started").matches());
		assertTrue(Main.REGEX_PARTIDA.matcher("23/04/2013 15:39:22 - Match 11348965 has ended").matches());
		assertFalse(Main.REGEX_PARTIDA.matcher("").matches());
		assertFalse(Main.REGEX_PARTIDA.matcher("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN").matches());
	}
	
	@Test
	public void deveValidarCadaLinhaDoLog() {
		main.eachLine("23/04/2013 15:34:22 - New match 11348965 has started");
		assertEquals(main.partidas.size(), 1);
		
		main.eachLine("23/04/2013 15:36:04 - Roman killed Nick using M16");
		assertEquals(main.partidas.size(), 1);
		
		main.eachLine("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		assertEquals(main.partidas.size(), 1);
		
		main.eachLine("23/04/2013 15:39:22 - Match 11348965 has ended");
		assertEquals(main.partidas.size(), 1);
	}
	
	

}
