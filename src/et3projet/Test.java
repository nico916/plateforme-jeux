package et3projet;

import java.time.LocalDate;

public class Test {
	private int noteInterface;
	private int noteGameplay;
	private int noteOptimisation;
	LocalDate date;
	private String titre;
	private String commentaire;
	private Jeu jeu;
	private Membre auteur;
	private int noteGlobale;

	//Constructeur pour initialiser les propriétés de l'objet Test
	
	public Test(String titre,String commentaire, int noteInterface, int noteGameplay, int noteOptimisation, LocalDate date, int noteGlobale,Membre auteur, Jeu jeu) {
		this.titre=titre;
		this.commentaire=commentaire;
		this.noteInterface=noteInterface;
		this.noteGameplay=noteGameplay;
		this.noteOptimisation=noteOptimisation;
		this.date=date;
		this.noteGlobale=noteGlobale;
		this.auteur=auteur;
		this.jeu=jeu;
	}
	
	public String getTitre() {
		return titre;
	}
	
	public String getCommentaire() {
		return commentaire;
}
	
	public int getNoteInterface() {
		return noteInterface;
	}
	
	public int getNoteGameplay() {
		return noteGameplay;
	}
	
	public int getNoteOptimisation() {
		return noteOptimisation;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public int getNoteGlobale() {
		return noteGlobale;
	}
	
	public Membre getAuteur() {
		return auteur;
	}
	
	public Jeu getJeu() {
		return jeu;
	}
}