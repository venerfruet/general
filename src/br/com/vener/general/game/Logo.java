package br.com.vener.general.game;

/**
 * Representação gráfica do logotipo do jogo 
 * @author VENER
 *
 */
public class Logo {
	/**
	 * Cria o logotipo General.
	 */
	private String logo;
	
	public Logo() {
		Environment environment=new Environment();
		String resetColors=environment.ANSI_RESET;
		String bgBlack=environment.ANSI_BLACK_BG;
		String fgGreen=environment.ANSI_GREEN;
		
		logo=resetColors + bgBlack + fgGreen;
		logo += "╔══════════════════════════════════════════════════════════════════╗\n";
		logo += "║ ░░░░░░░░ ░░░░░░░░ ░░░░    ░░ ░░░░░░░░ ░░░░░░░░ ░░░░░░░░ ░░       ║\n";
		logo += "║ ░░       ░░       ░░ ░░   ░░ ░░       ░░    ░░ ░░    ░░ ░░       ║\n";
		logo += "║ ▒▒  ▒▒▒▒ ▒▒▒▒▒▒   ▒▒  ▒▒  ▒▒ ▒▒▒▒▒▒   ▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒ ▒▒       ║\n";
		logo += "║ ▒▒    ▒▒ ▒▒       ▒▒   ▒▒ ▒▒ ▒▒       ▒▒  ▒▒   ▒▒    ▒▒ ▒▒       ║\n";
		logo += "║ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓    ▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓   ▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓▓▓ ║\n";
		logo += "╚══════════════════════════════════════════════════════════════════╝\n";
		logo += resetColors;
	}

	/**
	 * getLogo - Retorna a logo general
	 * @return String de retorno
	 */
	public String getLogo() {
		return logo;
	}
	
}
