package et3projet;

public class Invite extends Membre {
	public Invite() {
		super();
	}
	
	public Invite(String pseudo) { //Pour le cas où un joueur est bloque
		super(pseudo);
		setPseudo(pseudo);
	}
	
	public String getRole() {
		return "invite";
	}
	
}
