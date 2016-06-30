package pre.dojo.eclis;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pre.dojo.eclis.util.Support;

public class Partida {

	private String id;
	private Date dataInicio;
	private Date dataTermino;
	private int minutos;
	private List<Log> logs;
	private List<Jogador> jogadores;

	public Partida(String id, Date dataInicio) {
		this.id = id;
		this.dataInicio = dataInicio;
		this.logs = new LinkedList<Log>();
		this.jogadores = Collections.emptyList();
	}
	
	public void terminar(Date dataTermino) {
		this.dataTermino = dataTermino;
		this.minutos = Support.diffMinutos(dataTermino, this.dataInicio);
		this.calcularResultado();
	}

	private void calcularResultado() {
		Map<String,Jogador> map = new HashMap<String,Jogador>();
		for (Log log : logs) {
			Jogador assassino = map.get(log.assassino);
			if (assassino == null) {
				assassino = new Jogador(this,log.assassino);
				map.put(log.assassino, assassino);
			}
			assassino.addAssassinato(log);
			Jogador vitima = map.get(log.vitima);
			if (vitima == null) {
				vitima = new Jogador(this,log.vitima);
				map.put(log.vitima, vitima);
			}
			vitima.addMorte(log.arma);
		}
		map.remove("<WORLD>");
		this.jogadores = new LinkedList<Jogador>(map.values());
		Collections.sort(this.jogadores);
	}

	public void addLog(Log log) {
		this.logs.add(log);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Partida ").append(id)
				.append(" com duracao de ").append(minutos).append(" minutos")
				.append("\niniciada em  ").append(Support.toString(dataInicio))
				.append("\nterminada em ").append(Support.toString(dataTermino))
				.append("\nvencedor ").append(getVencedor().getNome())
				.append(", arma preferida ").append(getVencedor().getArmaPreferida())
				.append('\n');
		sb.append("Ranking:\n");
		sb.append("  ").append(Jogador.cabecalho(13)).append('\n');
		for (Jogador jogador : jogadores) {
			sb.append("  ").append(jogador.toString(13)).append('\n');
		}
		return sb.toString();
	}

	Jogador getVencedor() {
		return jogadores.get(0);
	}

	public String getId() {
		return id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public int getMinutos() {
		return minutos;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}
	
	public String toAsciiDoc() {
		StringBuilder sb = new StringBuilder()
				.append("[cols=\"<,<\"]\n")
				.append("|===\n")
				.append("| Partida: *").append(id).append("*\n")
				.append("| Duraçao (min): *").append(minutos).append("*\n")
				.append("| Início: *").append(Support.toString(dataInicio)).append("*\n")
				.append("| Fim: *").append(Support.toString(dataTermino)).append("*\n")
				.append("| Vencedor: *").append(getVencedor().getNome()).append("*\n")
				.append("| Arma Preferida: *").append(getVencedor().getArmaPreferida()).append("*\n")
				.append("|===\n\n")
				.append("[options=\"header\"]\n")
				.append("|===\n\n")
				.append("|Jogador |Assassinatos |Mortes |MaxStreak |Awards\n")
				;
		for (Jogador jogador : jogadores) {
			sb.append(jogador.toAsciiDoc());
		}
		return sb.append("|===\n\n---\n\n").toString();
	}

}
