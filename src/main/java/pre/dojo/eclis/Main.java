package pre.dojo.eclis;

import static org.asciidoctor.Asciidoctor.Factory.create;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.Options;

import pre.dojo.eclis.util.Support;

public class Main {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			args = new String[] { "log.txt" }; //arquivo default
		}
		File logFile = new File(args[0]);
		if (!logFile.exists() || !logFile.isFile()) {
			throw new RuntimeException("arquivo de log inválido: " + logFile);
		}
		new Main(logFile).process();
	}
	
	static Pattern REGEX_PARTIDA = Pattern.compile("^(../../.... ..:..:..) - (New match|Match) (.*?) has (.*?)$");
    LinkedList<Partida> partidas = new LinkedList<Partida>();
    
	private File logFile;

	public Main(File logFile) {
		this.logFile = logFile;
	}

	public void process() throws IOException {
		for (String line : FileUtils.readLines(logFile,"UTF-8")) {
			eachLine(line);
		}
	    printResult();
	    generateHtml();
	}

    public void eachLine(String line) {
		Matcher matcher = REGEX_PARTIDA.matcher(line);
		if (matcher.matches()) {
            Date data = Support.parsedDate(matcher.group(1));
            String id = matcher.group(3);
            String status = matcher.group(4);
			if ("started".equals(status)) {
            	partidas.add(new Partida(id,data));
            } else if ("ended".equals(status)) {
            	partidas.getLast().terminar(data);
            }
        } else {
        	Log log = Log.fromLine(line);
        	if (log != null) {
        		partidas.getLast().addLog(log);
        	}
        }
	}
    
    public void generateHtml() throws FileNotFoundException, IOException {
    	System.out.println("Gerando HTML");
		StringBuilder sb = new StringBuilder("= Partidas\n\n");
		for (Partida partida : partidas) {
			sb.append(partida.toAsciiDoc());
		}
		Asciidoctor asciidoctor = create();
		Attributes attributes = new Attributes();
		attributes.setLinkCss(false);
		Options options = new Options();
		options.setAttributes(attributes); 
		options.setInPlace(true);
		File adocFile = new File(logFile.getParent(),logFile.getName() + ".adoc");
		try {
			FileUtils.write(adocFile,sb.toString(), "UTF-8");
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		asciidoctor.renderFile(adocFile, options);
		System.out.println("HTML gerado em " + adocFile.getAbsolutePath().replaceAll("\\.adoc$", ".html"));
		// resolucao temporaria pra resolver o problema do linkcss que nao esta funcionando direito
		File targetCss = new File(logFile.getParent(),"asciidoctor.css");
		IOUtils.copy(Main.class.getResourceAsStream("/gems/asciidoctor-1.5.4/data/stylesheets/asciidoctor-default.css"), 
				new FileOutputStream(targetCss));
	}

	public void printResult() {
		for (Partida partida : partidas) {
			System.out.println(partida);
		}
	}

}