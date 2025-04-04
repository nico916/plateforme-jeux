package et3projet;

public class Evaluation {
	private int note;
	public String titre;
	private String commentaire;
	private int evalPos; //nombre de likes de l'évaluation
	private int evalNeut;//nombre d'avis neutres de l'évaluation
	private int evalNeg;//nombre de dislikes de l'évaluation
	public Jeu jeu;
	public Membre auteur;
	public int ordre;
	public int signalements; //nombre de signalements
	public Evaluation(int note, String titre, String commentaire,Jeu jeu,Membre membre,int ordre,int signalements) {
		this.note=note;
		this.titre=titre;
		this.commentaire=commentaire;
		this.evalPos=0;
		this.evalNeut=0;
		this.evalNeg=0;
		this.jeu=jeu;
		this.auteur=membre;
		this.ordre=ordre;
		this.signalements=signalements;
	}
	
	public int getSignalements() {
		return signalements;
	}
	
	public void setSignalements(int sign) {
		this.signalements=sign; 
	}
	
	public void newSignalements() {
		signalements= signalements+1;
	}
	
	public Membre getAuteur() {
		return auteur;
	}
	
	public Jeu getJeu() {
		return jeu;
	}
	
	
	public void setJeu (Jeu jeu) {
		this.jeu=jeu;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public int getNote() {
		return note;
	}
	
	public String getCommentaire() {
		return commentaire;
	}
	
	public Jeu getNom() {
		return jeu;
	}
	
	public void newEvalPos() {
		evalPos=evalPos+1;
	}
	
	public int getEvalPos() {
		return evalPos;
	}
	
	public void newEvalNeutre() {
		evalNeut=evalNeut+1;
	}
	
	public int getEvalNeutre() {
		return evalNeut;
	}
	
	public void newEvalNeg() {
		evalNeg=evalNeg+1;
	}
	
	public int getEvalNeg() {
		return evalNeg;
	}
	
	public void setEvalPos (int eval) {
		this.evalPos=eval;
	}
	
	public void setEvalNeutres (int eval) {
		this.evalNeut=eval;
	}
	
	public void setEvalNeg (int eval) {
		this.evalNeg=eval;
	}
	
	public void setOrdre(int ordre) {
		this.ordre=ordre;
	}
	
	public int getOrdre() {
		return ordre;
	}
}
