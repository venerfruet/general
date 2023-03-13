package br.com.vener.general.game;

/**
 * Sorteador de dados
 * 
 * @author Vener Fruet da Sileira
 * @version 2023-02-24
 */
public class RollDados {

	/**
	 * trocarte - Mantém os valores atuais, trocando apenas os que estão com valor
	 * 0(zero).
	 * 
	 * @param currentNumbers Informa os números atuais.
	 * @return vetor de 5 inteiros
	 */
	public int[] trocarte(int[] currentNumbers) {
		for (int i = 0; i < currentNumbers.length; i++) {
			if (currentNumbers[i] == 0) {
				currentNumbers[i] = (int) (Math.random() * 6 + 1);
			}
		}
		return currentNumbers;
	}

	/**
	 * rollAll - retorna 5 números aleatórios entre 1 e 6.
	 * 
	 * @return vetor de 5 inteiros
	 */
	public int[] rollAll() {
		int[] currentNumbers = { 0, 0, 0, 0, 0 };

		for (int i = 0; i < currentNumbers.length; i++) {
			currentNumbers[i] = (int) (Math.random() * 6 + 1);
		}
		return currentNumbers;
	}
}
