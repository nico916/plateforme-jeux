package et3projet;

import java.util.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Cette classe abstraite représente un membre de la plateforme.
 * Elle contient les informations et les actions liées à un membre.
 */
public abstract class Membre {
	private String pseudo;
	private HashMap<Jeu,Integer> jeux; //jeux possedes par un membre et leurs temps de jeu respectifs
	private int tempsDeJeuTotale;
	private int nombEvaluations;
	private int nombTests;
	private int nombEvalPositivesQueJaiLike;
	private int nombEvalPositivesQuonMaLike;
	private int nombEvalNeutresQueJaiFaite;
	private int nombEvalNeutresQuonMaFaite;
	private int nombEvalNegativesQueJaiDislike;
	private int nombEvalNegativesQuonMaDislike;
	private int jetons;
	private ArrayList<Test> listeTests; 
	private HashMap<Evaluation,Jeu> evals; //evaluations postés par un membre pour leurs jeux respectifs
	private HashMap<Jeu,Integer> jetonsPlaces; //nombre de jetons placés pour les jeux respectifs
	private ArrayList<Evaluation> listeEvals;
	private ArrayList<EvalPositives> listeEvalsLikes;
	private ArrayList<EvalNeutres> listeEvalsNeutres;
	private ArrayList<EvalNegatives> listeEvalsDislikes;
	private ArrayList<Membre> listeDesMembresQuiOntPubliesUneEval;
	private ArrayList<EvalPositives> listeEvalQuiOntEteLikes;
	private ArrayList<Membre> listeMembreEvaluationsNeutres;
	private ArrayList<Membre> listeMembreEvaluationsDislikes;
	
	public Membre() {
		this.pseudo = null;
		this.jeux=new HashMap<>();
		this.evals=new HashMap<>();
		this.jetonsPlaces=new HashMap<>();
		this.listeTests=new ArrayList<>();
		this.listeEvals= new ArrayList<Evaluation>();
		this.listeEvalsLikes= new ArrayList<EvalPositives>();
		this.listeEvalsNeutres= new ArrayList<EvalNeutres>();
		this.listeEvalsDislikes= new ArrayList<EvalNegatives>();
		this.listeDesMembresQuiOntPubliesUneEval= new ArrayList<Membre>();
		this.listeEvalQuiOntEteLikes= new ArrayList<EvalPositives>();
		this.listeMembreEvaluationsNeutres= new ArrayList<Membre>();
		this.listeMembreEvaluationsDislikes= new ArrayList<Membre>();
		this.nombEvalPositivesQueJaiLike=0;
		this.nombEvalPositivesQuonMaLike=0;
		this.nombEvalNeutresQueJaiFaite=0;
		this.nombEvalNeutresQuonMaFaite=0;
		this.nombEvalNegativesQueJaiDislike=0;
		this.nombEvalNegativesQuonMaDislike=0;
		this.tempsDeJeuTotale=0;
		this.nombEvaluations=0;
	}
	
	/**
     * Constructeur de la classe Membre avec le pseudo et les jetons spécifiés.
     * @param pseudo Le pseudo du membre.
     * @param jetons Le nombre de jetons du membre.
     */
	public Membre(String pseudo,int Jeton) {
		this.pseudo=pseudo;
		this.jetons=Jeton;
		this.jeux=new HashMap<>();
		this.nombTests=0;
		this.evals=new HashMap<>();
		this.jetonsPlaces=new HashMap<>();
		this.listeTests=new ArrayList<>();
		this.listeEvals= new ArrayList<Evaluation>();
		this.listeEvalsLikes= new ArrayList<EvalPositives>();
		this.listeEvalsNeutres= new ArrayList<EvalNeutres>();
		this.listeEvalsDislikes= new ArrayList<EvalNegatives>();
		this.listeDesMembresQuiOntPubliesUneEval= new ArrayList<Membre>();
		this.listeEvalQuiOntEteLikes= new ArrayList<EvalPositives>();
		this.listeMembreEvaluationsNeutres= new ArrayList<Membre>();
		this.listeMembreEvaluationsDislikes= new ArrayList<Membre>();
		this.tempsDeJeuTotale=0;
		this.nombEvalPositivesQueJaiLike=0;
		this.nombEvalPositivesQuonMaLike=0;
		this.nombEvalNeutresQueJaiFaite=0;
		this.nombEvalNeutresQuonMaFaite=0;
		this.nombEvalNegativesQueJaiDislike=0;
		this.nombEvalNegativesQuonMaDislike=0;
	}
	
	public Membre(String role) {
		this.jeux=new HashMap<>();
		this.nombTests=0;
		this.evals=new HashMap<>();
		this.listeTests=new ArrayList<>();
		this.listeEvals= new ArrayList<Evaluation>();
		this.listeEvalsLikes= new ArrayList<EvalPositives>();
		this.listeEvalsNeutres= new ArrayList<EvalNeutres>();
		this.listeEvalsDislikes= new ArrayList<EvalNegatives>();
		this.listeDesMembresQuiOntPubliesUneEval= new ArrayList<Membre>();
		this.listeEvalQuiOntEteLikes= new ArrayList<EvalPositives>();
		this.listeMembreEvaluationsNeutres= new ArrayList<Membre>();
		this.listeMembreEvaluationsDislikes= new ArrayList<Membre>();
		this.tempsDeJeuTotale=0;
		this.nombEvalPositivesQueJaiLike=0;
		this.nombEvalPositivesQuonMaLike=0;
		this.nombEvalNeutresQueJaiFaite=0;
		this.nombEvalNeutresQuonMaFaite=0;
		this.nombEvalNegativesQueJaiDislike=0;
		this.nombEvalNegativesQuonMaDislike=0;
	}
	
	
	/**
     * Ajoute un certain nombre de jetons pour un jeu associé.
     * @param jeu Le jeu associé.
     * @param nmbJetons Le nombre de jetons à ajouter.
     */
	public void ajoutJetons (Jeu jeu,int nmbJetons) {
    	this.jetonsPlaces.put(jeu, nmbJetons);
    }
	
	public void setJetonsPlaces (HashMap<Jeu,Integer> jetons) {
		this.jetonsPlaces=jetons;
	}
	
	//supprime le nombre de jetons placés pour le jeu en paramètre associé (nom du jeu et support)
	public void supprimerJeton(String nomJeu, String support) {
	    Iterator<Map.Entry<Jeu, Integer>> iterator = jetonsPlaces.entrySet().iterator();
	    
	    while (iterator.hasNext()) {
	        Map.Entry<Jeu, Integer> entry = iterator.next();
	        Jeu jeu = entry.getKey();
	        
	        if (jeu.getNomJeu().equals(nomJeu) && jeu.getSupport().equals(support)) {
	            iterator.remove();
	        }
	    }
	}

	
	
	
	public HashMap<Jeu,Integer> getJetonsPlaces() {
    	return jetonsPlaces;
    }
	
	public void supprimerEval (Evaluation eval) {
		this.listeEvals.remove(eval);
		this.evals.remove(eval);
	}
	
	public void supprimerEvalPositive (Evaluation eval) {
		for(EvalPositives  evals : getEvalPositives()) {
			if(evals.getEval().getTitre().equalsIgnoreCase(eval.getTitre()) &&  evals.getEval().getCommentaire().equalsIgnoreCase(eval.getCommentaire())) {
				this.listeEvalsLikes.remove(evals);
				setNombEvalQueJaiLike(getNombEvalQueJaiDislike()-1);
				
			}
		}
	}
	
	public void supprimerEvalNeutre (Evaluation eval) {
		for(EvalNeutres  evals : getListeEvalNeutres()) {
			if(evals.getEval().getTitre().equalsIgnoreCase(eval.getTitre()) &&  evals.getEval().getCommentaire().equalsIgnoreCase(eval.getCommentaire())) {
				this.listeEvalsNeutres.remove(evals);
				setNombEvalNeutresQueJaiFaite(getNombEvalNeutresQueJaiFaite()-1);
				
			}
		}
	}
	
	public void supprimerEvalNegative (Evaluation eval) {
		for(EvalNegatives  evals : getListeEvalNegatives()) {
			if(evals.getEval().getTitre().equalsIgnoreCase(eval.getTitre()) &&  evals.getEval().getCommentaire().equalsIgnoreCase(eval.getCommentaire())) {
				this.listeEvalsDislikes.remove(evals);
				setNombEvalQueJaiDislike(getNombEvalQueJaiDislike()-1);
			}
		}
	}
	
	public void ajouterListeTest(Test test) {
		this.listeTests.add(test);
	}
	
	public void setListeEvalJeux (HashMap<Evaluation,Jeu> evals) {
		this.evals=evals;
	}
	
	public void setListeTest (ArrayList<Test> test) {
		this.listeTests=test;
	}
	
	public void afficherTest () {
		for (Test test : listeTests) {
			System.out.println(test.getTitre());
		}
	}
	
	public ArrayList<Test> getListeTest() {
		return listeTests;
	}
	
	public void newNombEvalQueJaiLike() {
		this.nombEvalPositivesQueJaiLike=nombEvalPositivesQueJaiLike+1;
	}
	
	public void newNombEvalQueJaiDislike() {
		this.nombEvalNegativesQueJaiDislike=nombEvalNegativesQueJaiDislike+1;
	}
	
	public void newNombEvalQueJaifaiteNeutres() {
		this.nombEvalNeutresQueJaiFaite=nombEvalNeutresQueJaiFaite+1;
	}
	
	public int getNombEvalQueJaiLike() {
		return nombEvalPositivesQueJaiLike;
	}
	
	public void setNombEvalQueJaiLike(int nomb) {
		this.nombEvalPositivesQueJaiLike=nomb;
	}
	
	public int getNombEvalQueJaiDislike() {
		return nombEvalNegativesQueJaiDislike;
	}
	
	public void setNombEvalQueJaiDislike(int nomb) {
		this.nombEvalNegativesQueJaiDislike=nomb;
	}
	
	public int getNombEvalNeutresQueJaiFaite() {
		return nombEvalNeutresQueJaiFaite;
	}
	
	public void setNombEvalNeutresQueJaiFaite(int nomb) {
		this.nombEvalNeutresQueJaiFaite=nomb;
	}
	
	public int getNombEvalPositivesQuonMaFaite() {
		return nombEvalPositivesQuonMaLike;
	}
	
	public int getNombEvalNegativesQuonMaFaite() {
		return nombEvalNegativesQuonMaDislike;
	}
	
	public int getNombEvalNeutresQuonMaFaite() {
		return nombEvalNeutresQuonMaFaite;
	}
	
	public void setNombEvalPositivesQuonMaFaite(int nomb) {
		this.nombEvalPositivesQuonMaLike=nomb;
	}
	
	public void setNombEvalNeutresQuonMaFaite(int nomb) {
		this.nombEvalNeutresQuonMaFaite=nomb;
	}
	
	public void setNombEvalNegativesQuonMaFaite(int nomb) {
		this.nombEvalNegativesQuonMaDislike=nomb;
	}
	
	
	public void ajouterMembre(Membre membre) {
		listeDesMembresQuiOntPubliesUneEval.add(membre);
	}
	
	public void ajouterEvaluationQuiEstLike(EvalPositives evals) {
		listeEvalQuiOntEteLikes.add(evals);
	}
	
	public String getEvaluationsQuiOntEteLikees() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getPseudo()+", voici la liste Evaluations de vos evaluations qui ont likees : \n");
	    if (listeEvalQuiOntEteLikes.isEmpty()) {
	    	sb.append("C'est vide");
	    	return sb.toString();
	    }
	    else {
	    	System.out.println("Je suis la");
	    	for (EvalPositives l : listeEvalQuiOntEteLikes) {
		        sb.append(l.getEval().getTitre());
		        sb.append(" : ");
		        sb.append(l.getEval().getCommentaire());
		        sb.append(" avec une note de "+l.getEval().getNote()+" /10");
		    }
		    return sb.toString();
	    }
		
	}
	
	public void ajouterMembreQuiEstNeutre(Membre membre) {
		listeMembreEvaluationsNeutres.add(membre);
	}
	
	public void ajouterMembreQuiEstDislikes(Membre membre) {
		listeMembreEvaluationsDislikes.add(membre);
	}
	
	public String getPseudo() {  
		return pseudo;
	}
	
	public HashMap<Jeu, Integer>getJeux() {
		return jeux;
	}
	
	public void setJeux(HashMap<Jeu,Integer> jeux) {
		this.jeux=jeux;
	}
	
	public HashMap<Evaluation, Jeu>getEvaluations(){
		return evals;
	}
	
	public void PlusTempsDeJeuTotale(int temps) {	
		tempsDeJeuTotale = tempsDeJeuTotale + temps;
	}
	
	public void setTempsDeJeuTotale(int temps) {
		this.tempsDeJeuTotale=temps;
	}
	
	public int getTempsDeJeuTotale() {
		return tempsDeJeuTotale;
	}
	
	public int getNombEvaluations() {
		return nombEvaluations;
	}
	
	public int getNombTests() {
		return nombTests;
	}
	
	public void setNombTests(int nombTests) {
		this.nombTests=nombTests;
	}
	
	public void afficherJeuxParDureeDecroissante() {
	    List<Map.Entry<Jeu, Integer>> list = new ArrayList<>(jeux.entrySet());
	    Collections.sort(list, new Comparator<Map.Entry<Jeu, Integer>>() {
	        public int compare(Map.Entry<Jeu, Integer> o1, Map.Entry<Jeu, Integer> o2) {
	            return o2.getValue().compareTo(o1.getValue());
	        }
	    });
	    for (Map.Entry<Jeu, Integer> entry : list) {
	        System.out.println(entry.getKey().getNomJeu() + " - Durée : " + entry.getValue() + " minutes");
	    }
	}


	
	public String AjouterJeux(Jeu jeu, Integer tempsDeJeu) {
		if (jeu == null || tempsDeJeu == null) {
		    throw new IllegalArgumentException("Jeu et temps de jeu doivent être non null");
		}
		jeux.put(jeu, tempsDeJeu);
		StringBuilder sb = new StringBuilder();
	    sb.append("Liste des jeux joués : \n");
	    for (Map.Entry<Jeu, Integer> entry : jeux.entrySet()) {
	        sb.append(entry.getKey().getNomJeu());
	        sb.append(" : ");
	        sb.append(entry.getValue());
	        sb.append(" heures jouées\n");
	    }
	    return sb.toString();
	}
	
	//affiche (renvoie un string) la liste des jeux possédés par un joueur
	public String getJeux2() {
		StringBuilder sb = new StringBuilder();
	    sb.append("Liste des jeux joués : \n");
	    for (Map.Entry<Jeu, Integer> entry : jeux.entrySet()) {
	        sb.append("- "+entry.getKey().getNomJeu()+"\n");
	    }
	    return sb.toString();
	}
	
	public String AjouterEval(Evaluation evaluation, Jeu Jeu) {
		if (evaluation == null || Jeu == null) {
		    throw new IllegalArgumentException("Evaluation et Jeu doivent être non null");
		}
		evals.put(evaluation, Jeu);
		StringBuilder sb = new StringBuilder();
	    sb.append("Liste des evaluations : \n");
	    for (Map.Entry<Evaluation, Jeu> entry : evals.entrySet()) {
	    	sb.append("titre commentaire : ");
	    	sb.append(entry.getKey().getTitre());
	        sb.append(" ,pour le jeu suivant : ");
	        sb.append(entry.getValue().getNomJeu());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
	
	public void ajouterListeEval(Evaluation evaluation) {
		listeEvals.add(evaluation);
	}
	
	public ArrayList<Evaluation> getListeEval() {
		return listeEvals;
	}
	
	public void nouvelleEvaluation() {
		
		nombEvaluations=nombEvaluations +1;

	}
	
	public void nouvelleEvaluationPositiveQuonMaFaite() {
		nombEvalPositivesQuonMaLike = nombEvalPositivesQuonMaLike + 1;
	}
	
	public void afficherJeuxParNombreDeJetons() {
	    HashMap<String, Integer> jeuxParNombreJetons = new HashMap<>();

	    for (Map.Entry<Jeu, Integer> entry : jetonsPlaces.entrySet()) {
	        Jeu jeu = entry.getKey();
	        int jetons = entry.getValue();
	        String key = jeu.getNomJeu() + "|" + jeu.getSupport();
	        jeuxParNombreJetons.put(key, jetons);
	    }

	    
	    for (Map.Entry<Jeu, Integer> entry : jeux.entrySet()) {
	        Jeu jeu = entry.getKey();
	        int jetons = entry.getValue();
	        String key = jeu.getNomJeu() + "|" + jeu.getSupport();
	        if (!jeuxParNombreJetons.containsKey(key)) {
	            jeuxParNombreJetons.put(key,0);
	        }
	    }

	    
	    List<Map.Entry<String, Integer>> sortedJeux = new ArrayList<>(jeuxParNombreJetons.entrySet());
	    sortedJeux.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

	    
	    for (Map.Entry<String, Integer> entry : sortedJeux) {
	        String[] parts = entry.getKey().split("\\|");
	        String nomJeu = parts[0];
	        String support = parts[1];
	        int jetons = entry.getValue();

	        System.out.println("Jeu : " + nomJeu);
	        System.out.println("Support : " + support);
	        System.out.println("Nombre de jetons : " + jetons);
	        System.out.println("---------------");
	    }
	}

	
	public void nouvelleEvaluationNegativesQuonMaFaite() {
		nombEvalNegativesQuonMaDislike = nombEvalNegativesQuonMaDislike + 1;
	}
	
	public void nouvelleEvaluationNeutresQuonMaFaite() {
		nombEvalNeutresQuonMaFaite = nombEvalNeutresQuonMaFaite + 1;
	}
	
	public void nouveauTest() {
		nombTests=nombTests + 1;
		jetons=jetons+5;
	}
	
	public void InitialisationJetons() {
		this.jetons=3;
	}
	
	public void SetJetons(int jetons) {
		this.jetons=jetons;
	}
	
	public int getJetons() {
		return jetons;
	}
	
	public void setEval(int nombreEvaluations) {
		this.nombEvaluations=nombreEvaluations;
	}
	
	public abstract String getRole();
	
	public void setPseudo(String pseudo) {
		this.pseudo=pseudo;
	}
	
	public String getEval() {
        StringBuilder sb = new StringBuilder();
        sb.append("Liste des evaluations fait par : ");
        sb.append(pseudo).append("\n");
        
        if (evals.isEmpty()) {
            sb.append("Aucune évaluation pour ce jeu.");
        } else {
            int i = 1;
            for (Map.Entry<Evaluation, Jeu> entry : evals.entrySet()) {
                sb.append("Evaluation ").append(i).append(" :\n");
                sb.append("Titre de l'évaluation : ").append(entry.getKey().getTitre()).append("\n");
                sb.append("Commentaire : ").append(entry.getKey().getCommentaire()).append("\n");
                sb.append("Pour le jeu : ").append(entry.getValue().getNomJeu()).append("\n");
                
                i++;
            }
        }
        return sb.toString();
}
	
	public void ajoutEvalLike(EvalPositives eval) {
		listeEvalsLikes.add(eval);
	}
	
	public void setListeEvalLike (ArrayList<EvalPositives> eval) {
		this.listeEvalsLikes=eval;
	}
	
	public void setListeEvalNeutres (ArrayList<EvalNeutres>eval) {
		this.listeEvalsNeutres=eval;
	}
	
	public void setListeEvalDislike (ArrayList<EvalNegatives> eval) {
		this.listeEvalsDislikes=eval;
	}
	
	
	public ArrayList<EvalPositives> getEvalPositives() {
		return listeEvalsLikes;
	}
	
	public void ajoutEvalNeutre(EvalNeutres eval) {
		listeEvalsNeutres.add(eval);
	}
	
	public ArrayList<EvalNeutres> getListeEvalNeutres() {
		return listeEvalsNeutres;
	}
	
	public ArrayList<EvalNegatives> getListeEvalNegatives() {
		return listeEvalsDislikes;
	}
	
	public void ajoutEvalDislikes(EvalNegatives eval) {
		listeEvalsDislikes.add(eval);
	}
	
	public void afficherListeEvals () {
		if(listeEvals.isEmpty()) {
			System.out.println("La liste evals pour "+getPseudo()+ " est vide");
		}
		for(Evaluation eval: listeEvals) {
			System.out.println(eval.getTitre());
		}
	}
	
	public void setListeEvals(ArrayList<Evaluation> eval) {
		this.listeEvals=eval;
	}
	
	public void ajoutEval(Evaluation eval) {
		listeEvals.add(eval);
	}
	
	
	
	
}
