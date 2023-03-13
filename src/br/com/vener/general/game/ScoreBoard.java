package br.com.vener.general.game;

/**
 * Placar do jogo
 */
public class ScoreBoard {
	/**
	 * Campos da classe
	 */
	Environment environment = new Environment();
	String resetColors = environment.ANSI_RESET;
	String fgRed = environment.ANSI_RED;
	String fgPurple = environment.ANSI_PURPLE;

	String scoreBoard;
	private int maxRouds;
	
	/**
	 * Construtor ScoreBoard
	 * @param maxRouds Informa o máximo de rodadas possíveis
	 */
	public ScoreBoard(int maxRouds) {
		this.maxRouds=maxRouds;
	}

	/**
	 * getScoreBoard - Retorna o placar
	 * @param round Infrma a rodada atual
	 * @param score Informa a  pontuação atual
	 * @return String
	 */
	public String getScoreBoard(int round, int score) {
		scoreBoard = resetColors + fgRed + "RODADA: " + round + " de " + maxRouds + fgPurple + " - PONTUAÇÃO ATUAL: "
				+ score;
		return scoreBoard;
	}

}
