package pre.dojo.eclis;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import pre.dojo.eclis.util.Support;

public class JogadorTest {
	
	Jogador jogador = null;
	Jogador jogador2 = null;
	
	@Before
	public void setUp() throws Exception {
		Partida partida = new Partida("11348965", Support.parsedDate("23/04/2013 15:34:22"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using M16"));
		partida.addLog(Log.fromLine("23/04/2013 15:36:04 - Roman killed Nick using AK47"));
		partida.terminar(new Date());
		jogador = partida.getJogadores().get(0);
		jogador2 = partida.getJogadores().get(1);
	}

	@Test
	public void deveApontarArmaPreferida() {
		assertEquals("M16", jogador.getArmaPreferida());
	}
	
	@Test
	public void deveCalcularScoreSubtraindoAsMortesDosAssassinatos() {
		assertEquals(new Integer(7), jogador.getScore());
		assertEquals(new Integer(-7), jogador2.getScore());
	}

	@Test
	public void deveMostrarAwardsRecebidosPeloJogador() {
		assertEquals(new Integer(2), jogador.getAwards());
		assertEquals(new Integer(0), jogador2.getAwards());
	}
	
	@Test
	public void deveVerificarSeOJogadorEhOVencedorDaPartida() {
		assertTrue(jogador.souVencedor());
		assertFalse(jogador2.souVencedor());
	}
}
