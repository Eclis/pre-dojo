package pre.dojo.eclis;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pre.dojo.eclis.util.Support;

class Log {
    static final Pattern REGEX_SCORE = Pattern.compile("^(../../.... ..:..:..) - (.*?) killed (.*?) (using|by) (.*?)$");

    public final Date data;
    public final String assassino;
    public final String vitima;
    public final String arma;
    
	public Log(Date data, String assassino, String vitima, String arma) {
		this.data = data;
		this.assassino = assassino;
		this.vitima = vitima;
		this.arma = arma;
	}

	public int diffSegundos(Log other) {
		return Support.diffSegundos(this.data, other.data);
	}

	static Log fromLine(String line) {
		Log log = null;
		Matcher matcher = REGEX_SCORE.matcher(line);
		if (matcher.matches()) {
			log = new Log(
					Support.parsedDate(matcher.group(1)),
					matcher.group(2),
					matcher.group(3),
					matcher.group(5)
				);
		}
		return log;
	}
}