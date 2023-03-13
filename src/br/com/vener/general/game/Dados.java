
  package br.com.vener.general.game;

  /**
   * Representação gráfica dos dados
   * @author VENER
   *
   */
  public class Dados {
  
	 /**
	 * printDrawns - Representa os números sorteados como dados de jogo na saída de
	 * tela.
	 * 
	 * @param currentNumbers Informa os números atuais do sorteio.
	 * @return String representando os dados
	 */
	  public static String printDados(int[] currentNumbers) {
		  Environment environment=new Environment();
		  String resetColors=environment.ANSI_RESET;
		  String bgYellow=environment.ANSI_YELLOW_BG;
		  String[][] imgDados = new String[3][6];
		  String sequence;
		  
		  imgDados[0][0] = "│         │";
		  imgDados[1][0] = "│    ●    │";
		  imgDados[2][0] = "│         │";
		  
		  imgDados[0][1] = "│ ●       │";
		  imgDados[1][1] = "│         │";
		  imgDados[2][1] = "│       ● │";
		  
		  imgDados[0][2] = "│ ●       │";
		  imgDados[1][2] = "│    ●    │";
		  imgDados[2][2] = "│       ● │";
		  
		  imgDados[0][3] = "│ ●     ● │";
		  imgDados[1][3] = "│         │";
		  imgDados[2][3] = "│ ●     ● │";
		  
		  imgDados[0][4] = "│ ●     ● │";
		  imgDados[1][4] = "│    ●    │";
		  imgDados[2][4] = "│ ●     ● │";
		  
		  imgDados[0][5] = "│ ●     ● │";
		  imgDados[1][5] = "│ ●     ● │";
		  imgDados[2][5] = "│ ●     ● │";
		  
		  sequence="\n"+ bgYellow + "┌────1º───┐┌────2º───┐┌────3º───┐┌────4º───┐┌────5º───┐\n"+ resetColors;
		  
		  for (int i = 0; i < imgDados.length; i++) {
			  for (int j = 0; j < currentNumbers.length; j++) {
				  sequence += imgDados[i][currentNumbers[j] - 1];
			  } 
			  sequence += "\n";
		  }
		  
		  sequence += "└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘\n"+ resetColors;
		  
		return sequence;
		
	  }
	  
  }
		 