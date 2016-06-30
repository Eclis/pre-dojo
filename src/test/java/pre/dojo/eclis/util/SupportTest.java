package pre.dojo.eclis.util;

import static org.junit.Assert.assertEquals;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class SupportTest {
	
	String dataStringExemplo = "";
	Date dataExemplo = null;
	
	@Before
	public void setUp() {
		dataStringExemplo = "23/04/2013 15:34:22";
		dataExemplo = new SimpleDateFormat(Support.DATE_PATTERN).parse(dataStringExemplo, new ParsePosition(0));
	}

	@Test
	public void deveParsearDataQuandoFormatoDeDataForValido() {
		assertEquals(dataExemplo, Support.parsedDate(dataStringExemplo));
	}
	
	@Test
	public void deveParsearDateParaStringNoFormatoEspecificadoNoDatePattern() {
		assertEquals(dataStringExemplo, Support.toString(dataExemplo));
	}
	
	@Test
	public void deveRetornarVazioParaDataNula() {
		assertEquals("", Support.toString(null));
	}
	
	@Test
	public void deveRetornarDiferençaEmSegundosNaoImportandoAOrdemDasDatas() {
		Date data1 = new Date(System.currentTimeMillis() + 7000);
		Date data2 = new Date();
		assertEquals(7, Support.diffSegundos(data1, data2));
		
		data1 = new Date();
		data2 = new Date(System.currentTimeMillis() + 7000);
		assertEquals(7, Support.diffSegundos(data1, data2));
	}
}
