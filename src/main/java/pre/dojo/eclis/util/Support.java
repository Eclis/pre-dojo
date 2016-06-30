package pre.dojo.eclis.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Support {

	static final String DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
	public static Date parsedDate(String data) {
		return new SimpleDateFormat(DATE_PATTERN).parse(data, new ParsePosition(0));
	}
	
	public static String toString(Date data) {
		if (data != null) {
			return new SimpleDateFormat(DATE_PATTERN).format(data);
		} else {
			return "";
		}
	}

	public static int diffSegundos(Date data1, Date data2) {
		return Math.abs((int) ((data1.getTime() - data2.getTime()) / 1000L));
	}

	public static int diffMinutos(Date data1, Date data2) {
		return Math.abs((int) ((data1.getTime() - data2.getTime()) / (1000L * 60)));
	}
}
