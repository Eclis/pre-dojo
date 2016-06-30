package pre.dojo.eclis;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pre.dojo.eclis.util.Support;

public class PartidaTest {
	
	Partida partida = null;

	@Before
	public void setUp() throws Exception {
		partida = new Partida("11348965", Support.parsedDate("23/04/2013 15:34:22"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN"));
	}

	@Test
	public void deveTerminarUmaPartidaECalcularResultado() {
		partida.terminar(Support.parsedDate("23/04/2013 15:39:22"));
		assertEquals(Support.parsedDate("23/04/2013 15:34:22"), partida.getDataInicio());
		assertEquals(Support.parsedDate("23/04/2013 15:39:22"), partida.getDataTermino());
		assertEquals("11348965", partida.getId());
		assertEquals(2, partida.getJogadores().size());
		assertEquals(2, partida.getLogs().size());
		assertEquals(5, partida.getMinutos());
		assertEquals("Roman", partida.getVencedor().getNome());
	}
}