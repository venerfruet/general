package br.com.vener.general.game;

import java.util.Scanner;

/**
 * Esta versão do famoso jogo General, é um jogo de dados virtuais onde o
 * objetivo é somar o maior número de pontos com as combinações dos dados
 * sorteados em 11 rodadas.
 * 
 * @author Vener Fruet da Silveira
 * @version 2023-02-24
 */
public class Game {

	/** Constante que define o máximo de rodadas */
	public static final int MAX_ROUNDS = 11; // máximo de rodadas no jogo
	/** Constante que define o máximo de jogadas para cada rodada */
	public static final int MAX_MOVES = 3; // máximo de jogadas em uma rodada

	/**
	 * Método principal onde se dá toda a dinâmica do jogo.
	 * 
	 * @param args - Os argumento de entrada não são usados no jogo.
	 */
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		Environment environment = new Environment();
		Logo logo = new Logo();
		ScoreBoard scoreBoard = new ScoreBoard(MAX_ROUNDS);
		RollDados rollDados = new RollDados();
		Combinations combinations = new Combinations();

		String resetColors = environment.ANSI_RESET;
		String fgWhite = environment.ANSI_WHITE;
		String bgBlack = environment.ANSI_BLACK_BG;
		String bgBlue = environment.ANSI_BLUE_BG;

		int score = 0; // pontuação do jogador
		int round = 1; // rodada atual
		int selectedCombination = 0; // combinação que o jogador quer usar na rodada

		int[] vectSumCombinations = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // absorve o retorno das combinações possíveis
		int[] vectCurrentNumbers;// absorve os números sorteados

		/*
		 * vectCombinationsComputed - combinações computadas que não podem mais serem
		 * usadas, -1 = não usada e >= 0 representa o valor da combinação usada
		 */
		int[] vectCombinationsComputed = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

		// looping principal - rodadas
		while (round <= MAX_ROUNDS) {

			int move = 1; // jogada
			int replacement = -1; // qtde de dados que quer trocar, começa em -1 porque 0 é uma commando de
									// descarte

			for (int i = 0; i < vectSumCombinations.length; i++)
				vectSumCombinations[i] = vectCombinationsComputed[i] > -1 ? vectCombinationsComputed[i] : 0;

			// mostra logo na tela
			System.out.println(logo.getLogo());
			// mostra tabela de combinação atual
			System.out.println(combinations.showTableCombinations(vectCombinationsComputed, vectSumCombinations));
			// mostra o placar
			System.out.println("\n" + scoreBoard.getScoreBoard(round, score));

			// sorteia os dados iniciais
			vectCurrentNumbers = rollDados.rollAll();

			// looping secudário - jogadas
			while (move <= MAX_MOVES) {

				replacement = -1; // restaura o camando de descarte

				// mostra dados na tela
				System.out.println(bgBlack + fgWhite + "Números sorteados para a jogada " + move + " de " + MAX_MOVES);
				System.out.print(resetColors);
				System.out.println(Dados.printDados(vectCurrentNumbers));

				// trocar dados?
				while (replacement < 0 || replacement > 5) {
					try {
						System.out.println("Digite de 1 a 5 para realizar a troca ou 0 para finalizar a jogada.");
						System.out.print("Quantos dados você deseja trocar? -> ");
						replacement = scn.nextInt(); // como tratar a execeção caso não seja int?
					} catch (Exception e) {
						scn.nextLine();
					}
				}

				// caso queira trocar dados, solicita quais e os zera na matriz sorteada
				if (replacement > 0) {
					for (int i = 0; i < replacement; i++) {
						int id = 0;
						while (id <= 0 || id > 5) {
							try {
								System.out.print("Digite o número " + (i + 1) + "º dado a ser trocado -> ");
								id = scn.nextInt();
							} catch (Exception e) {
								scn.nextLine();
							}
						}
						vectCurrentNumbers[id - 1] = 0;
					}
					// incremento da jogada
					move++;
				} else {
					// caso o comando de descarte seja 0 maximiza a jogada para finalizar a rodada
					move = MAX_MOVES;
					break;
				}

				// sorteia os dados zerados
				vectCurrentNumbers = rollDados.trocarte(vectCurrentNumbers);

			}

			// mostra jogada final
			System.out.println("");
			System.out.println(bgBlue + fgWhite + "Jogada final");
			// mostra dados
			System.out.println(Dados.printDados(vectCurrentNumbers));

			// mostra opções
			vectSumCombinations = combinations.sumCombinations(vectCurrentNumbers, vectCombinationsComputed);
			System.out.println(combinations.showTableCombinations(vectCombinationsComputed, vectSumCombinations));

			do { // looping que permite apenas a combinação que ainda não foi computada

				do { // looping que permite apenas opção entre 1 e o máximo de rodadas

					try {
						System.out.print("Escolha uma opção para marcar seus pontos -> ");
						selectedCombination = scn.nextInt();
						System.out.println("");
					} catch (Exception e) {
						scn.nextLine();
					}

				} while (selectedCombination < 1 || selectedCombination > MAX_ROUNDS);

			} while (vectCombinationsComputed[selectedCombination - 1] >= 0);

			// marca combinação como usada com o respectivo valor
			vectCombinationsComputed[selectedCombination - 1] = vectSumCombinations[selectedCombination - 1];
			// soma o valor a pontuação atual
			score += vectSumCombinations[selectedCombination - 1];
			// incremento da rodada
			round++;
			// sorteia todos para começar uma nova rodada
			vectCurrentNumbers = rollDados.rollAll();
		}

		// exibe o resulta final com a logo
		System.out.println(logo.getLogo());// mostra logo na tela
		System.out.println("Sua pontuação final foi de " + score);
		System.out.println("Obrigado por jogar!");

		scn.close();
	}

}
