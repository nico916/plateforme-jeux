package et3projet;
import java.util.Map;
import java.util.HashMap;

public class Joueur extends Invite {

	public Joueur(String pseudo) {
		super();
		setPseudo(pseudo);
		SetJetons(getJetons());
	}
	
	public String getRole() {
		return "joueur";
	}
}

