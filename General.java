package br.com.senai.bauru.vener.general;

import java.util.Scanner;
/**
 * Esta versão do famoso jogo General, é um jogo de dados virtuais onde o objetivo é somar o maior número de pontos com as combinações dos dados sorteados em 11 rodadas.
 * @author Vener Fruet da Silveira
 * @version 2023-02-23
 */
public class General {

	/**Restaura o console para as cores padrão.*/
	public static final String ANSI_RESET = "\u001B[0m";

	/**Constante de cor de frente*/
	public static final String ANSI_BLACK = "\u001B[30m";
	/**Constante de cor de frente*/
	public static final String ANSI_RED = "\u001B[31m";
	/**Constante de cor de frente*/
	public static final String ANSI_GREEN = "\u001B[32m";
	/**Constante de cor de frente*/
	public static final String ANSI_YELLOW = "\u001B[33m";
	/**Constante de cor de frente*/
	public static final String ANSI_BLUE = "\u001B[34m";
	/**Constante de cor de frente*/
	public static final String ANSI_PURPLE = "\u001B[35m";
	/**Constante de cor de frente*/
	public static final String ANSI_CYAN = "\u001B[36m";
	/**Constante de cor de frente*/
	public static final String ANSI_WHITE = "\u001B[37m";

	/**Constante de cor de fundo*/
	public static final String ANSI_BLACK_BG = "\u001B[40m";
	/**Constante de cor de fundo*/
	public static final String ANSI_RED_BG = "\u001B[41m";
	/**Constante de cor de fundo*/
	public static final String ANSI_GREEN_BG = "\u001B[42m";
	/**Constante de cor de fundo*/
	public static final String ANSI_YELLOW_BG = "\u001B[43m";
	/**Constante de cor de fundo*/
	public static final String ANSI_BLUE_BG = "\u001B[44m";
	/**Constante de cor de fundo*/
	public static final String ANSI_PURPLE_BG = "\u001B[45m";
	/**Constante de cor de fundo*/
	public static final String ANSI_CYAN_BG = "\u001B[46m";
	/**Constante de cor de fundo*/
	public static final String ANSI_WHITE_BG = "\u001B[47m";

	/**Constante que define o máximo de rodadas*/
	public static final int MAX_ROUNDS = 11; //máximo de rodadas no jogo
	/** Constante que define o máximo de jogadas para cada rodada*/
	public static final int MAX_MOVES = 3; //máximo de jogadas em uma rodada

	/**
	 * Método principal onde se dá toda a dinâmica do jogo.
	 * 
	 * @param args - Os argumento de entrada não são usados no jogo. 
	 */
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

		int score = 0; // pontuação do jogador
		int round = 1; // rodada atual
		int selectedCombination = 0; // combinação que o jogador quer usar na rodada
		
		int[] combinations; // absorve retorno das combinações possíveis
		int[] currentNumbers = { 0, 0, 0, 0, 0 };// números sorteados
		int[] combinationsComputed = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }; // combinações computadas não pode
																						// mais ser usadas, -1 não foi
																						// usada ainda
		// looping principal - rodadas
		while (round <= MAX_ROUNDS) {

			int move = 1; //jogada
			int replacement = -1; // qtde de dados que quer trocar, começa em -1 porque 0 é uma commando de
									// descarte

			printLogo();
			printScoreBoard(round, score);

			// sorteia os dados iniciais
			currentNumbers = drawnNumbers(currentNumbers, false);

			// looping secudário - jogadas
			while (move <= MAX_MOVES) {

				replacement = -1; // restaura o camando de descarte

				// mostra dados na tela
				System.out.println(
						ANSI_BLACK_BG + ANSI_WHITE + "Números sorteados para a jogada " + move + " de " + MAX_MOVES);
				System.out.print(ANSI_RESET);
				printDrawns(currentNumbers);

				// trocar dados?
				while (replacement < 0 || replacement > 5) {
					System.out.println("Digite de 1 a 5 para realizar a troca ou 0 para finalizar a jogada.");
					System.out.print("Quantos dados você deseja trocar? -> ");
					replacement = scn.nextInt(); // como tratar a execeção caso não seja int?
				}

				// caso queira trocar dados, solicita quais e os zera na matriz sorteada
				if (replacement > 0) {

					for (int i = 0; i < replacement; i++) {
						int id = 0;
						while (id <= 0 || id > 5) {
							System.out.print("Digite o número " + (i + 1) + "º dado a ser trocado -> ");
							id = scn.nextInt();
						}
						currentNumbers[id - 1] = 0;
					}

					move++; // incremento da jogada

				} else {

					move = MAX_MOVES; // caso o comando de descarte seja 0 maximiza a jogada para finalizar a rodada
					break;

				}

				// sorteia os dados zerados
				currentNumbers = drawnNumbers(currentNumbers, false);

			}

			// mostra jogada final
			System.out.println(ANSI_BLUE_BG + ANSI_WHITE + "Jogada final");
			printDrawns(currentNumbers);
			
			// mostra opções
			combinations = showCombinations(currentNumbers, combinationsComputed);
			
			do { // looping que permite apenas a combinação que ainda não foi computada
				
				do { // looping que permite apenas opção entre 1 e o máximo de rodadas
					
					System.out.print("Escolha uma opção para marcar seus pontos -> ");
					selectedCombination = scn.nextInt();
					System.out.println("");

				} while (selectedCombination < 1 || selectedCombination > MAX_ROUNDS);
				
			} while(combinationsComputed[selectedCombination - 1] >= 0);
			
			combinationsComputed[selectedCombination - 1] = combinations[selectedCombination - 1]; // marca combinação como usada com o
																									// respectivo valor
			score += combinations[selectedCombination - 1]; // e acrescenta o valor a pontução
			
			round++; // incremento da rodada

			// sorteia todos para começar uma nova rodada
			currentNumbers = drawnNumbers(currentNumbers, true);
		}

		// exibe o resulta final com a logo
		printLogo();
		System.out.println("Sua pontuação final foi de "+ score);
		System.out.println("Obrigado por jogar!");
		
		scn.close();
	}

	/**
	 * drawnNumbers - retorna 5 números aleatórios entre 1 e 6.
	 * 
	 * @param currentNumbers Informa os números atuais do sorteio.
	 * @param all informa se todos os os números serão trocados, caso o parâmetro seja falso apenas os valores iguais a 0(zero) serão trocados.
	 * @return Retorna um novo conjunto de números aleatórios. 
	 */
	private static int[] drawnNumbers(int[] currentNumbers, boolean all) {

		for (int i = 0; i < currentNumbers.length; i++) {
			if (currentNumbers[i] == 0 || all) {
				currentNumbers[i] = (int) (Math.random() * 6 + 1);
			}
		}

		return currentNumbers;
	}

	/**
	 * printLogo - Mostra o logotipo na tela.
	 */
	private static void printLogo() {
		System.out.print(ANSI_RESET);
		System.out.print(ANSI_BLACK_BG + ANSI_GREEN);
		System.out.println("╔══════════════════════════════════════════════════════════════════╗");
		System.out.println("║ ░░░░░░░░ ░░░░░░░░ ░░░░    ░░ ░░░░░░░░ ░░░░░░░░ ░░░░░░░░ ░░       ║");
		System.out.println("║ ░░       ░░       ░░ ░░   ░░ ░░       ░░    ░░ ░░    ░░ ░░       ║");
		System.out.println("║ ▒▒  ▒▒▒▒ ▒▒▒▒▒▒   ▒▒  ▒▒  ▒▒ ▒▒▒▒▒▒   ▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒ ▒▒       ║");
		System.out.println("║ ▒▒    ▒▒ ▒▒       ▒▒   ▒▒ ▒▒ ▒▒       ▒▒  ▒▒   ▒▒    ▒▒ ▒▒       ║");
		System.out.println("║ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓    ▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓   ▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓▓▓ ║");
		System.out.println("╚══════════════════════════════════════════════════════════════════╝");
		System.out.print(ANSI_RESET);
		System.out.println(" ");
	}

	/**
	 * printScoreBoard - Mostra o placar na tela.
	 * 
	 * @param round Informa a rodada atual.
	 * @param score informa a pontuação atual.
	 */
	private static void printScoreBoard(int round, int score) {
		System.out.println(
				ANSI_RED + "RODADA: " + round + " de " + MAX_ROUNDS + ANSI_PURPLE + " - PONTUAÇÃO ATUAL: " + score);
		System.out.println(" ");
	}

	/**
	 * printDrawns - Representa os números sorteados como dados de jogo na saída de tela.
	 * 
	 * @param currentNumbers Informa os números atuais do sorteio.
	 */
	private static void printDrawns(int[] currentNumbers) {
		String[][] imgDados = new String[5][6];

		System.out.println(" ");

		imgDados[0][0] = "┌─────────┐";
		imgDados[1][0] = "│         │";
		imgDados[2][0] = "│    ●    │";
		imgDados[3][0] = "│         │";
		imgDados[4][0] = "└─────────┘";

		imgDados[0][1] = "┌─────────┐";
		imgDados[1][1] = "│ ●       │";
		imgDados[2][1] = "│         │";
		imgDados[3][1] = "│       ● │";
		imgDados[4][1] = "└─────────┘";

		imgDados[0][2] = "┌─────────┐";
		imgDados[1][2] = "│ ●       │";
		imgDados[2][2] = "│    ●    │";
		imgDados[3][2] = "│       ● │";
		imgDados[4][2] = "└─────────┘";

		imgDados[0][3] = "┌─────────┐";
		imgDados[1][3] = "│ ●     ● │";
		imgDados[2][3] = "│         │";
		imgDados[3][3] = "│ ●     ● │";
		imgDados[4][3] = "└─────────┘";

		imgDados[0][4] = "┌─────────┐";
		imgDados[1][4] = "│ ●     ● │";
		imgDados[2][4] = "│    ●    │";
		imgDados[3][4] = "│ ●     ● │";
		imgDados[4][4] = "└─────────┘";

		imgDados[0][5] = "┌─────────┐";
		imgDados[1][5] = "│ ●     ● │";
		imgDados[2][5] = "│ ●     ● │";
		imgDados[3][5] = "│ ●     ● │";
		imgDados[4][5] = "└─────────┘";

		System.out.println( ANSI_YELLOW_BG + "┌────1º───┐┌────2º───┐┌────3º───┐┌────4º───┐┌────5º───┐"+ ANSI_RESET);

		for (int i = 0; i < imgDados.length; i++) {
			for (int j = 0; j < currentNumbers.length; j++) {
				System.out.print(imgDados[i][currentNumbers[j] - 1]);
			}
			System.out.print("\n");
		}
	}

	/**
	 * showCombinations - Exibe as combinação possíveis para cada jogada
	 * e retorna os valores para que possam ser somados na pontuação. 
	 * 
	 * @param currentNumbers Informa os números atuais do sorteio.
	 * @param computed Informa quais combinações já foram usadas.
	 * @return Retorna a soma das combinações possíveis
	 */
	private static int[] showCombinations(int[] currentNumbers, int[] computed) {

		boolean[] findRepetitions = { false, false, false, false, false, false }; // para saber se encontrou alguma
																					// combinação
		int[] defaultValues = { 0, 0, 0, 0, 0, 0, 0, 25, 30, 40, 50 }; // padrão das combinações
		int[] sum = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; // usado para armazenar a soma dos dados iguais nas 6 primeiras
															// posições e das combinações nas 5 últimas posições

		// loop-ing para realizar a soma dos dados
		for (int i = 0; i < currentNumbers.length; i++) {
			sum[currentNumbers[i] - 1] += currentNumbers[i];
		}

		// looping para descobrir as repetição e combinações
		for (int i = 0; i < sum.length - 5; i++) {

			int repetition = sum[i] / (i + 1); // divide a soma pelo valor do dado para encontrar repetição. ex:15/3=5
												// repetições de 3.
			findRepetitions[repetition] = true; // marca verdadeiro para a combinação encontrada. ex:
												// combination=5 é marcado verdadeiro o índice 4

		}

		// Determina as combinações possíveis
		if (findRepetitions[5]) {// Encontrou general e trinca
			sum[10] = defaultValues[10];
			sum[6] = sumTrio(sum);
		}
		if (findRepetitions[4]) {// encontrou quadra e trinca
			sum[7] = defaultValues[7];
			sum[6] = sumTrio(sum);
		}
		if (findRepetitions[3] && findRepetitions[2]) {// encontrou full house e trinca
			sum[8] = defaultValues[8];
			sum[6] = sumTrio(sum);
		}
		if (findRepetitions[3]) {// encontrou trinca
			sum[6] = sumTrio(sum);
		}
		if (hasSequence(sum)) {// encontrou sequência
			sum[9] = defaultValues[9];
		}

		// exibe os valores computados
		for (int i = 0; i < computed.length; i++) {
			sum[i] = computed[i] < 0 ? sum[i] : computed[i];
		}

		//Exibe a tabela de combinações possíveis e também os valores já computados.
		System.out.print(ANSI_RESET);
		System.out.println(ANSI_GREEN_BG + ANSI_WHITE + "Combinações possíveis");
		System.out.println(
				"┏━━━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┓");
		System.out.println(
				"┃descrição┃SOMA 1 ┃SOMA 2 ┃SOMA 3 ┃SOMA 4 ┃SOMA 5 ┃SOMA 6 ┃TRINCA ┃QUADRA ┃  FULL ┃ SEQU. ┃GENERAL┃");
		System.out.println(
				"┣━━━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━┫");
		System.out.println("┃  opção  ┃   "
				+(computed[0]==-1?"1":"X") +"   ┃   "
				+(computed[1]==-1?"2":"X") +"   ┃   "
				+(computed[2]==-1?"3":"X") +"   ┃   "
				+(computed[3]==-1?"4":"X") +"   ┃   "
				+(computed[4]==-1?"5":"X") +"   ┃   "
				+(computed[5]==-1?"6":"X") +"   ┃   "
				+(computed[6]==-1?"7":"X") +"   ┃   "
				+(computed[7]==-1?"8":"X") +"   ┃   "
				+(computed[8]==-1?"9":"X") +"   ┃  "
				+(computed[9]==-1?"10":"X ") +"   ┃  "
				+(computed[10]==-1?"11":"X ") +"   ┃");
		System.out.println(
				"┣━━━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━┫");
		System.out.println("┃  valor  ┃  "
				+ String.format("%02d", sum[0]) + "   ┃  "
				+ String.format("%02d", sum[1]) + "   ┃  "
				+ String.format("%02d", sum[2]) + "   ┃  "
				+ String.format("%02d", sum[3]) + "   ┃  "
				+ String.format("%02d", sum[4]) + "   ┃  "
				+ String.format("%02d", sum[5]) + "   ┃  "
				+ String.format("%02d", sum[6]) + "   ┃  "
				+ String.format("%02d", sum[7]) + "   ┃  "
				+ String.format("%02d", sum[8]) + "   ┃  "
				+ String.format("%02d", sum[9]) + "   ┃  "
				+ String.format("%02d", sum[10]) + "   ┃");
		System.out.println(
				"┗━━━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┛");
		System.out.print(ANSI_RESET);

		return sum;
	}

	/**
	 * sumTrio - Soma todos os valores dos dados se existir uma trinca.
	 * <br>Uma trinca sempre existe no general, na quadra e no full house.
	 * 
	 * @param values Informa os valores entre 1 e 6 que foram sorteados.
	 * @return Retorna verdadeiro caso exista uma trinca. 
	 */
	private static int sumTrio(int[] values) {
		int sum=0;
		for (int i = 0; i < values.length - 5; i++) {
			sum += values[i];
		}
		return sum;
	}
	
	/**
	 * hasSequence - Verifica se existe uma sequência na jogada.
	 * <br>As sequências possíveis são 1-2-3-4-5 e 2-3-4-5-6.
	 * @param values Informa os valores entre 1 e 6 que foram sorteados.
	 * @return Retorna verdadeiro caso exista uma sequência. 
	 */
	private static boolean hasSequence(int[] values) {
		boolean sequence = true;

		for (int i = 1; i < values.length - 6; i++) {
			if (values[i] == 0) {
				sequence = false;
				break;
			}

		}

		sequence = sequence == true && (values[0] > 0 || values[5] > 0) ? true : false;

		return sequence;

	}
}
