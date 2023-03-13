package br.com.vener.general.game;
/**
 * Manipulador das combinações dos dados
 * @author VENER
 *
 */
public class Combinations {
	
	/**
	 * showTabaleCombinations - Retorno da tabela de combinação possíveis.
	 * 
	 * @param computed Informa quais combinações já foram usadas.
	 * @param sum informa a soma dos valores possíveis para cada combinação de dados.
	 * @return Retorna a tabela das combinações possíveis
	 */
	public String showTableCombinations(int[] computed, int[] sum) {
		Environment environment=new Environment();
		String resetColors=environment.ANSI_RESET;
		String bgGreen=environment.ANSI_GREEN_BG;
		String fgWhite=environment.ANSI_WHITE;
		String table;

		//retorno da tabela de combinações possíveis e também os valores já computados.
		table=resetColors
				+"\n" + bgGreen + fgWhite + "Combinações possíveis"
				+"\n┏━━━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┳━━━━━━━┓"
				+"\n┃descrição┃SOMA 1 ┃SOMA 2 ┃SOMA 3 ┃SOMA 4 ┃SOMA 5 ┃SOMA 6 ┃TRINCA ┃QUADRA ┃  FULL ┃ SEQU. ┃GENERAL┃"
				+"\n┣━━━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━┫"
				+"\n┃  opção  ┃   "
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
				+(computed[10]==-1?"11":"X ") +"   ┃"
				+"\n┣━━━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━╋━━━━━━━┫"
				+"\n┃  valor  ┃  "
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
				+ String.format("%02d", sum[10]) + "   ┃"
				+"\n┗━━━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┻━━━━━━━┛"+ resetColors;

		return table;
	}

	/**
	 * sumCombinations - Soma os valores possíveis das combinações dos dados.
	 * 
	 * @param currentNumbers Informa os números atuais do sorteio.
	 * @param computed Informa quais combinações já foram usadas.
	 * @return Retorna a soma das combinações possíveis
	 */
	public int[] sumCombinations(int[] currentNumbers, int[] computed) {
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

		return sum;
	}

	/**
	 * sumTrio - Soma todos os valores dos dados se existir uma trinca. <br>
	 * Uma trinca sempre existe no general, na quadra e no full house.
	 * 
	 * @param values Informa os valores entre 1 e 6 que foram sorteados.
	 * @return Retorna verdadeiro caso exista uma trinca.
	 */
	private int sumTrio(int[] values) {
		int sum = 0;
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
	private boolean hasSequence(int[] values) {
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
