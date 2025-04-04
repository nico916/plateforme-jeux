package et3projet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * La classe Jeu représente un jeu vidéo.
 */

public class Jeu {
    private String nom;
    private String categoriePrincipale;
    private String editeur;
    private String classement;
    private String supports;
    private int anneeSortie;
    private String developpeur;
    private double ventesMondiales;
    private int critiquesTesteurs;
    private double scoreCritiques;
    private int evaluationsJoueurs;
    private double scoreEvaluations;
    private double ventesNorthAmerica;
    private double ventesEurope;
    private double ventesJapon;
    private double autreVentes;
    private HashMap<Integer,Membre> jetonsPlaces;
   
    /**
     * Constructeur de la classe Jeu.
     *
     * @param nom     Le nom du jeu.
     * @param support Le support du jeu.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    
    //on parcourt le fichier jusqu'à qu'on valide la condition qui est que c'est le meme nom de jeu et le meme support
    public Jeu(String nom, String support) throws IOException {
        FileReader fr = new FileReader("src/ListeJeux.csv");
        BufferedReader br = new BufferedReader(fr);
        String ligne = "";
        while ((ligne = br.readLine()) != null) {
            String[] colonnes = ligne.split(",");
          
            if (colonnes[1].equalsIgnoreCase(nom) && colonnes[2].equalsIgnoreCase(support)) { 
            	
            	this.jetonsPlaces=new HashMap<Integer,Membre>();
            	if(colonnes.length<=11) {
            		
            		this.scoreCritiques=-1;
            		this.critiquesTesteurs=-1;
            		this.scoreEvaluations=-1;
            		this.evaluationsJoueurs=-1;
            		this.developpeur="null";
            		this.classement="null";
            		this.nom=colonnes[1];
            		this.supports=colonnes[2];
            		this.anneeSortie=Integer.parseInt(colonnes[3]);
            		this.categoriePrincipale=colonnes[4];
            		this.editeur=colonnes[5];
            		this.ventesNorthAmerica=Double.parseDouble(colonnes[6]);
            		this.ventesEurope=Double.parseDouble(colonnes[7]);
            		this.ventesJapon=Double.parseDouble(colonnes[8]);
            		this.autreVentes=Double.parseDouble(colonnes[9]);
            		this.ventesMondiales=Double.parseDouble(colonnes[10]);
            		
            	}
            	else {
	            	//if(colonnes[1].isEmpty()) {
	            	//	this.nom="null";
	            	//}
	            	//else {
	            		this.nom = colonnes[1];
	            	//}
	            	if(colonnes[2].isEmpty()) {
	            		this.supports="null";
	            	}
	            	else {
	            		this.supports = colonnes[2];
	            	}
	            	if(colonnes[3].isEmpty()) {
	            		this.anneeSortie=-1;
	            	}
	            	else {
	            		this.anneeSortie = Integer.parseInt(colonnes[3]);
	            	}
	            	if(colonnes[4].isEmpty()) {
	            		this.categoriePrincipale="null";
	            	}
	            	else {
	            		this.categoriePrincipale = colonnes[4];
	            	}
	            	if(colonnes[5].isEmpty()) {
	            		this.editeur="null";
	            	}
	            	else {
	            		this.editeur = colonnes[5];
	            	}
	            	if(colonnes[6].isEmpty()) {
	            		this.ventesNorthAmerica=-1;
	            	}
	            	else {
	            		this.ventesNorthAmerica = Double.parseDouble(colonnes[6]);
	            	}
	            	if(colonnes[7].isEmpty()) {
	            		this.ventesEurope=-1;
	            	}
	            	else {
	            		this.ventesEurope = Double.parseDouble(colonnes[7]);
	            	}
	            	if(colonnes[8].isEmpty()) {
	            		this.ventesJapon=-1;
	            	}
	            	else {
	            		this.ventesJapon = Double.parseDouble(colonnes[8]);
	            	}
	            	if(colonnes[9].isEmpty()) {
	            		this.autreVentes=-1;
	            	}
	            	else {
	            		this.autreVentes = Double.parseDouble(colonnes[9]);
	            	}
	            	if(colonnes[10].isEmpty()) {
	            		this.ventesMondiales=-1;
	            	}
	            	else {
	            		this.ventesMondiales = Double.parseDouble(colonnes[10]);
	            	}
	            	if(colonnes[11].isEmpty()) {
	            		this.scoreCritiques=-1;
	            	}
	            	else {
	            		this.scoreCritiques = Double.parseDouble(colonnes[11]);
	            	}
	            	if(colonnes[12].isEmpty()) {
	            		this.critiquesTesteurs=-1;
	            	}
	            	else {
	            		this.critiquesTesteurs = Integer.parseInt(colonnes[12]);
	            	}
	            	if(colonnes[13].isEmpty()) {
	            		this.scoreEvaluations=-1;
	            	}
	            	else {
	            		if(colonnes[13].equalsIgnoreCase("tbd")) {
	            			this.scoreEvaluations=-1;
	            		}
	            		else {
	            			this.scoreEvaluations = Double.parseDouble(colonnes[13]);
	            		}
	            	}
	            	if(colonnes[14].isEmpty()) {
	            		this.evaluationsJoueurs=-1;
	            	}
	            	else {
	            		this.evaluationsJoueurs = Integer.parseInt(colonnes[14]);
	            	}            	            	                                                                                             
	            	if(colonnes[15].isEmpty()) {
	            		this.developpeur="null";
	            	}
	            	else {
	            		this.developpeur = colonnes[15];
	            	}
	            	
	            	if(colonnes[16].isEmpty()) {
	            		this.classement="null";
	            	}
	            	else {
	            		this.classement = colonnes[16];
	            	}           	
	                break;
	            	//}
            }
        } 
            else {
            
        	this.nom="null";  // le jeu n'est pas dans la liste
        }
        
        }
        br.close();
    }
 
    /**
     * Ajoute des jetons pour un membre donné.
     *
     * @param nmbJetons Le nombre de jetons à ajouter.
     * @param membre    Le membre pour lequel les jetons sont ajoutés.
     */
    
    public void ajoutJetons(int nmbJetons,Membre membre) {
    	this.jetonsPlaces.put(nmbJetons, membre);
    }
    
    /**
     * Retourne les jetons de places attribués pour ce jeu.
     *
     * @return Les jetons de places attribués pour ce jeu.
     */
    
    public HashMap<Integer,Membre> getJetonsPlaces() {
    	return jetonsPlaces;
    }
    
    public void setEvalJeux(HashMap<Integer,Membre> jetons) {
    	jetonsPlaces=jetons;
    }
    
    public String getNomJeu() {
    	return nom;
    }
    
    public String getSupport() {
    	return supports;
    }
    
    public int getEvalJoueurs() {
    	return evaluationsJoueurs;
    }
    
    public String getDeveloppeur() {
    	return developpeur;
    }
    
    public String afficherInformations() {
        String informations = "";
        System.out.println(" Information pour le jeu : "+this.nom+" sur "+this.supports);
        informations += "-Catégorie principale: " + this.categoriePrincipale + "\n";
        informations += "-Editeur: " + this.editeur + "\n";
        informations += "-Classement: " + this.classement + "\n";
        informations += "-Année de sortie: " + this.anneeSortie + "\n";
        informations += "-Développeur: " + this.developpeur + "\n";
        
        informations += "-Critiques testeurs: " + this.critiquesTesteurs + "\n";
        informations += "-Score critiques: " + this.scoreCritiques + "\n";
        informations += "-Evaluations joueurs: " + this.evaluationsJoueurs + "\n";
        informations += "-Score évaluations: " + this.scoreEvaluations + "\n";
        informations += "-Ventes mondiales: " + this.ventesMondiales + "\n";
        informations += "-Ventes North America: " + this.ventesNorthAmerica + "\n";
        informations += "-Ventes Europe: " + this.ventesEurope + "\n";
        informations += "-Ventes Japon: " + this.ventesJapon + "\n";
        informations += "-Autres ventes: " + this.autreVentes + "\n";
        return informations;
        
    }

    
    
}
