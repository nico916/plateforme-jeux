package et3projet;

public class Administrateur extends Testeur {
	public Administrateur(String pseudo) {
		super(pseudo);
	}
	
	public String getRole() {
		return "administrateur";
	}
}
