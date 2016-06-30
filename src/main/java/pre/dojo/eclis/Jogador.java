package pre.dojo.eclis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Jogador implements Comparable<Jogador> {

	private Partida partida;
	private String nome;
	private List<Log> assassinatos;
	private List<String> mortes;
	private int streak;
	private int maxStreak;

	public Jogador(Partida partida, String nome) {
		this.partida = partida;
		this.nome = nome;
		this.assassinatos = new LinkedList<Log>();
		this.mortes = new LinkedList<String>();
		this.streak = 0;
		this.maxStreak = 0;
	}
	
	public String getArmaPreferida() {
		String melhorArma = null;
		Integer maior = 0;
		Map<String,Integer> contadores = new HashMap<String,Integer>();
		for (Log log : assassinatos) {
			Integer contador = contadores.get(log.arma);
			if (contador == null) {
				contadores.put(log.arma, contador = 1);
			} else {
				contadores.put(log.arma, contador + 1);
			}
			if (contador > maior) {
				maior = contador;
				melhorArma = log.arma;
			}
		}
		return melhorArma;
	}

	public String getNome() {
		return nome;
	}

	public void addAssassinato(Log log) {
		this.assassinatos.add(log);
		this.streak++;
		if (this.streak > this.maxStreak) {
			this.maxStreak = streak;
		}
	}
	
	public void addMorte(String arma) {
		this.mortes.add(arma);
		this.streak = 0;
	}
	
	public Integer getScore() {
		return assassinatos.size() - mortes.size();
	}
	
	public Integer getAwards() {
		int awards = (souVencedor() && mortes.isEmpty()) ? 1 : 0;
		for (int i = 0; i < assassinatos.size(); i++) {
			Log logN = assassinatos.get(i);
			if (assassinatos.size() > i + 4) {
				Log logNmais4 = assassinatos.get(i + 4);
				if (logNmais4.diffSegundos(logN) <= 60) {
					awards++;
					i = i + 4;
				}
			}
		}
		return awards;
	}
 	
	public boolean souVencedor() {
		return partida.getVencedor() == this;
	}

	@Override
	public String toString() {
		return this.toString(12);
	}
	
	public String toString(int size) {
		return new StringBuilder().append(padRight(nome,size))
				.append(padRight(assassinatos.size(),size))
				.append(padRight(mortes.size(),size))
				.append(padRight(maxStreak,size))
				.append(padRight(getAwards(),size))
				.toString();
	}
	
	public Partida getPartida() {
		return partida;
	}

	public List<Log> getAssassinatos() {
		return assassinatos;
	}

	public List<String> getMortes() {
		return mortes;
	}

	public int getStreak() {
		return streak;
	}

	public int getMaxStreak() {
		return maxStreak;
	}
	
	private static String padRight(Object o, int size) {
		StringBuilder sb = new StringBuilder(o.toString());
		for (int i = sb.length(); i < size; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Jogador o) {
		return -getScore().compareTo(o.getScore());
	}

	public static String cabecalho(int size) {
		return new StringBuilder().append(padRight("Nome",size))
				.append(padRight("Assassinatos",size))
				.append(padRight("Mortes",size))
				.append(padRight("MaxStreaks",size))
				.append(padRight("Awards",size))
				.toString();
	}
	
	public String toAsciiDoc() {
		return new StringBuilder()
				.append('|').append(nome).append(' ')
				.append('|').append(assassinatos.size()).append(' ')
				.append('|').append(mortes.size()).append(' ')
				.append('|').append(maxStreak).append(' ')
				.append('|').append(getAwards()).append(' ')
				.append('\n')
				.toString();
	}
}