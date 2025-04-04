package et3projet;

public class Testeur extends Joueur {
	public Testeur(String pseudo) {
		super(pseudo);
		setPseudo(pseudo);
	}
	
	public String getRole() {
		return "testeur";
	}
}
