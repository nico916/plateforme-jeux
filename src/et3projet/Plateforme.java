package et3projet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

//Petit commentaire sur les jeux : j'ai un problème concernant les jeux qui n'ont pas toutes leurs informations. En effet, ils sont dit comme nuls dans mon programme sans comprendre pourquoi, mais j'avoue ne pas avoir réussi à trouver une solution. 
//Ils sont donc difficilement utilisables pour ce code : contrairement aux jeux qui ont toutes leurs informations; qui eux, marchent parfaitement



/**
 * Cette classe représente la plateforme de jeux
 * Elle gère les profils des utilisateurs, la connexion, l'inscription, etc.
 */

public class Plateforme {
	//Liste des utilisateurs inscrits sur la plateforme
	private ArrayList<Membre> profils; 
	
	//Celui qui vient de se connecter ou de s'inscrire
	private Membre utilisateur; 
	
	//Creation de la classe et du fichier qui permettent d'avoir une persistance des données
	private FichierMembre fichierMembre; 
	private static final String NOM_FICHIER_MEMBRE = "membres.text";
	
	//Nombre qui s'incrémente à chaque nouvelle évaluation - permet d'avoir l'ordre d'écriture des évaluations
	public int ordre;
	
	/**
     * Constructeur de la classe Plateforme.
     * Initialise la plateforme en chargeant les membres à partir du fichier.
     *
     * @throws IOException en cas d'erreur de lecture du fichier membre
     */
	
	public Plateforme() throws IOException {
		fichierMembre= new FichierMembre(NOM_FICHIER_MEMBRE);
		
		//permet la persistance des données lorsque que l'on réexecute le programme (prends les données écrites dans le fichier)
		profils= fichierMembre.chargerMembres();
		
		utilisateur = null;
		ordre=0;
	}
	
	//Methode qui permet pour un jeu donné en paramètre, d'afficher tous les test qui ont réalisés pour ce jeu
	public String afficherTestPourUnJeu (Jeu jeu) {
		
		ArrayList<Test> listeTests = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (Membre membre : profils) {
			
			for(Test test : membre.getListeTest()) {  //getListeTest récupère la liste des évalutions qu'un membre a fait
				
				if  ((test.getJeu().getNomJeu().equalsIgnoreCase(jeu.getNomJeu())) && (test.getJeu().getSupport().equalsIgnoreCase(jeu.getSupport()))) {
					listeTests.add(test);
				}
			}
		}
		if(listeTests.isEmpty()) {
			
			sb.append("Ce jeu n'a pas de test");
		}
		else {
			
			sb.append("Voici les tests pour "+jeu.getNomJeu()+"\n");
			int i=1;
			for(Test test: listeTests) {
				
				sb.append("    Test "+i+"\n");
				sb.append("-Testeur du test : "+test.getAuteur().getPseudo()+"\n");
				sb.append("-Titre du test"+test.getTitre()+"\n");
				sb.append("-Commentaire du test :"+test.getCommentaire()+"\n");
				sb.append("-Date du test : "+test.getDate()+"\n");
				sb.append("-Note donne par le testeur sur l'interface du jeu : "+test.getNoteInterface()+"\n");
				sb.append("-Note donne par le testeur sur le gameplay du jeu : "+test.getNoteGameplay()+"\n");
				sb.append("-Note donne par le testeur sur l'optimisation du jeu : "+test.getNoteOptimisation()+"\n");
				sb.append("-Note globale : "+test.getNoteGlobale()+"\n");
		}
		
		}
		return sb.toString();
	}
	
	//Cette methode prend un certain préfixe en paramètre (String), et permet de retourner une liste de tous les jeux qui ont également ce préfixe
	public ArrayList<Jeu> rechercherJeuxParPrefixe(String prefixe) throws IOException {
	    ArrayList<Jeu> jeuxTrouves = new ArrayList<>();
	    FileReader fr = new FileReader("ListeJeux.csv");  //Ouvre le fichier csv où se trouve les jeux
	    BufferedReader br = new BufferedReader(fr);
	    String ligne;
	    while ((ligne = br.readLine()) != null) { //Lit toutes les lignes du fichier
	    	
	        String[] colonnes = ligne.split(",");
	        String nomJeu = colonnes[1];
	        if (nomJeu.startsWith(prefixe)) { 
	        	
	            String support = colonnes[2];
	            Jeu jeu = new Jeu(nomJeu, support);
	            jeuxTrouves.add(jeu);
	        }
	    }
	    
	    br.close(); //Fermeture du fichier
	    
	    return jeuxTrouves;
	}
	
	
	
	//Cette methode affiche la liste des jeux contenant le prefixe 
	public boolean afficherListeJeux(ArrayList<Jeu> jeux) {
		
	    if (jeux.isEmpty()) {
	    	
	        System.out.println("Aucun jeu trouve.");
	        return false;
	    } 
	    else {
	    	
	        System.out.println("Jeux pour ce prefixe :");
	        for (Jeu jeu : jeux) {
	        	if(jeu.getNomJeu().equals("null")) { 
	        	}
	        	else {
	        		
	        		//System.out.println(jeu.getNomJeu().getClass().getSimpleName());
	        		System.out.println( jeu.getNomJeu());
	 	            System.out.println("Support : " + jeu.getSupport());
	 	            System.out.println("------------------------------");
	        	}
	        }
	    }
	    
	    return true;
	}

	
	//Cette méthode permet d'afficher toutes les évaluations qui ont été réalisées sur un certain jeu, passé en paramètre
	public String afficherEvaluationsPourUnJeu (Jeu jeu) {
		
		ArrayList<Evaluation> listeEvals = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (Membre membre : profils) {
			
			for(Evaluation eval : membre.getListeEval()) {
				
				if  ((eval.getJeu().getNomJeu().equalsIgnoreCase(jeu.getNomJeu())) && (eval.getJeu().getSupport().equalsIgnoreCase(jeu.getSupport()))) {
					
					listeEvals.add(eval);
				}
			}
		}
		if(listeEvals.isEmpty()) {
			
			sb.append("Ce jeu n'a pas d'evaluations"+"\n");
		}
		else {
			
			sb.append("Voici les tests pour "+jeu.getNomJeu());
			int i=1;
			for(Evaluation eval: listeEvals) {
				
				sb.append("    Evaluation "+i);
				sb.append("-Auteur de l'evaluation : "+eval.getAuteur().getPseudo());
				sb.append("-Titre de l'evaluation"+eval.getTitre());
				sb.append("-Commentaire de l'evaluation :"+eval.getCommentaire());
				sb.append("-Note : "+eval.getNote());
				sb.append("-Nombre de likes : "+eval.getEvalPos());
				sb.append("-Nombre d'appreciations neutres : "+eval.getEvalNeutre());
				sb.append("-Nombre de dislikes : "+eval.getEvalNeg());
				sb.append("---------------");
		}
		
		}
		return sb.toString();
	}
	
	//Cette méthode affiche également les évaluations pour un certain jeu donné paramètre
	//Cependant, ici on affiche les evaluations en nombre decroissant de like sur ces evaluations
	public ArrayList<Evaluation> afficherEvaluations(Jeu jeu) {
		ArrayList<Evaluation> evaluations = new ArrayList<>();
	    for (Membre membre : profils) {
	        
	    	for (Evaluation eval : membre.getListeEval()) {
	    		
	            if ((eval.getJeu().getNomJeu().equalsIgnoreCase(jeu.getNomJeu())) && (eval.getJeu().getSupport().equalsIgnoreCase(jeu.getSupport())) ) {
	            	
	                evaluations.add(eval);
	                
	            }
	        }
	    }
	   
	    //Tri dans un ordre decroissant de like
	    evaluations.sort((eval1, eval2) -> {
	        int likesCompare = Integer.compare(eval2.getEvalPos(), eval1.getEvalPos());
	        if (likesCompare != 0) {
	        	
	            return likesCompare;
	        } 
	        else {
	        	
	            return Integer.compare(eval1.getOrdre(), eval2.getOrdre());
	        }
	    });
	    
	    
	    if(evaluations.isEmpty()) {
	    	
	    	System.out.println("C'est vide");
	    	
	    }
	    int i=1;
	    for (Evaluation eval : evaluations) {
	    	
	    	System.out.println("Eval "+i);
	    	System.out.println("Auteur de l'evaluation: " + eval.getAuteur().getPseudo());
	        System.out.println("Note : " + eval.getNote()+"/10");
	        System.out.println("Titre : " + eval.getTitre());
	        System.out.println("Commentaire : " + eval.getCommentaire());
	        System.out.println("Nombre de likes : " + eval.getEvalPos());
	        System.out.println("---------------");
	        i=i+1;
	    } 
	    
	    return evaluations;
	}



	//Methode qui est appelée lorsque qu'un joueur fait une action après la connexion ou l'inscription (permet aux joueurs de faire plusieurs actions de suite)
	public void choixJoueur() throws IOException {
		
		System.out.println("Que souhaitez vous faire ? Petit rappel : en tant que joueur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		System.out.println(" - Afficher les informations sur un membre : 1 ");
	 		System.out.println(" - Ajouter un jeu : 2 ");		   	    	 		
	 		System.out.println(" - Evaluer un jeu : 3 ");
	 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");
	 		System.out.println(" - Vous deconnecter : 5");
	 		System.out.println(" - Se desinscrire : 6 ");
	 		System.out.println(" - Afficher les informations sur jeu : 7");
	 		System.out.println(" - Placer un/des jetons pour un jeu afin de demander un test : 8");
	 		String choix;
	 		Scanner sc2 = new Scanner(System.in);
	 		System.out.println("Votre choix : ");
	 		choix = sc2.nextLine();
	 		//Possibilite pour l'utilisateur de se tromper
	 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7") && !choix.equalsIgnoreCase("8")) {
	 			do {
	 				System.out.println("Veuillez choisi un chiffre entre 1 et 6 par rapport à ce que vous voulez faire");
	 				System.out.println("En tant que joueur voila ce que pouvez faire, choisissez ce que vous voulez faire");
	 				System.out.println(" - Afficher les informations sur un membre : 1 ");
	 				System.out.println(" - Ajouter un jeu : 2 ");		   	    	 		
	 				System.out.println(" - Evaluer un jeu : 3 ");
	 				System.out.println(" - Voire les evaluations d'un jeu : 4 ");
	 				System.out.println(" - Vous deconnecter : 5");
	 				System.out.println(" - Se desinscrire : 6 ");
	 				System.out.println(" - Afficher les informations sur jeu : 7");
	 				System.out.println(" - Placer un/des jetons pour un jeu afin de demander un test : 8");
	 				choix = sc2.nextLine();
	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8"));
	 		}
	    	 switch (choix) {
	    	case "1":
    		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
    	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
    	     String pseudo2 = sc2.nextLine();
    	     afficherInfos(pseudo2); 
    	     System.out.println(" Souhaitez vous faire autre chose?");
    		 System.out.println(" - Si oui, repondez oui ");
    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
    		 Scanner sc4=new Scanner(System.in);
    		 System.out.println("Votre choix : ");
    		 String rep = sc4.nextLine();
    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
    			 
    			 System.out.println("Veuillez repondre par oui ou par non");
    			 String reponseEncore = sc4.nextLine();
    			 rep = reponseEncore;
    			 
    	    }
    		 if(rep.equalsIgnoreCase("non")) {
    			 deconnecter();
    		 }
    		 else if (rep.equalsIgnoreCase("oui")) {
    			 //Possibilite de faire un autre choix en appelant la methode choixJoueur()
    			 choixJoueur();
    		 }
    	     break;
    	 case "2":
    	     System.out.println("Vous avez choisi d'ajouter un jeu ");
    	     ajouterJeu();
    	     System.out.println(" Souhaitez vous faire autre chose?");
    		 System.out.println(" - Si oui, repondez oui ");
    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
    		 Scanner sc3=new Scanner(System.in);
    		 System.out.println("Votre choix : ");
    		 String rep2 = sc3.nextLine();
    		 while (!rep2.equalsIgnoreCase("non")&&!rep2.equalsIgnoreCase("oui")) {
    			 System.out.println("Veuillez repondre par oui ou par non");
    			 String reponseEncore = sc3.nextLine();
    			 rep2 = reponseEncore;
    	    }
    		 if(rep2.equalsIgnoreCase("non")) {
    			 deconnecter();
    		 }
    		 else if (rep2.equalsIgnoreCase("oui")) {
    			 choixJoueur();
    		 }
    	     break;
    	 case "6":
    	      System.out.println("Vous avez choisi de vous desinscrire ");
    	      desinscrire();
    	      choix(); //Retour au menu
			  return;
    	 case "3":
    	      System.out.println("Vous avez choisi d'evaluer un jeu ");
    	      evaluer();
    	      System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc33=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep3 = sc33.nextLine();
	    		 while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc33.nextLine();
	    			 rep3 = reponseEncore;
	    	    }
	    		 if(rep3.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep3.equalsIgnoreCase("oui")) {
	    			 choixJoueur();
	    		 }
	    	     break;
    	 case "4":
    	   System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
    	   
    	   //Boolean qui permet de savoir si l'utilisateur possède le jeu ou pas, et appelle une methode qui permet d'afficher les évaluations d'un jeu
    	   boolean a=afficherEvaluationPourUnJeu();
    	   if(a==false) {
    		   System.out.println("Vous ne posseder pas de jeu");
    		   
    	   }
    	   else if(a==true) {
    		   //Possibilite d'evaluer l'evaluation
    		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
    		   Scanner sc41= new Scanner(System.in);
        	   String repA = sc41.nextLine();
        	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
        	   		System.out.println("Veuillez repondre par oui ou par non");
        	   		String newRep = sc41.nextLine();
        	   		repA=newRep;
        	   }
        	   if(repA.equalsIgnoreCase("oui")) { 
        		   if(utilisateur.getJeux().isEmpty()) {
        			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
        		   }
        		   else {
        			   evaluerEvaluation();
        		   }
        	   }
    	   }
    	   
    	   System.out.println(" Souhaitez vous faire autre chose?");
    		 System.out.println(" - Si oui, repondez oui ");
    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
    		 Scanner sc44=new Scanner(System.in);
    		 System.out.println("Votre choix : ");
    		 String rep4 = sc44.nextLine();
    		 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
    			 System.out.println("Veuillez repondre par oui ou par non");
    			 String reponseEncore = sc44.nextLine();
    			 rep4 = reponseEncore;
    	    }
    		 if(rep4.equalsIgnoreCase("non")) {
    			 deconnecter();
    		 }
    		 else if (rep4.equalsIgnoreCase("oui")) {
    			 choixJoueur();
    		 }
    	     break;
    	 case "5" :
    		 System.out.println("Vous avez choisi de vous deconnecter");
    		 deconnecter();
    		 return;
    	 case "7" :
    		 System.out.println("Vous avez choisi d'afficher les informations sur un jeu");
    		 System.out.println("De quel jeu voulez vous avoir les informations ?");
    		 Scanner sc45=new Scanner(System.in);
    		 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
    		 String rep6 = sc45.nextLine();
    		 System.out.println("Sur quelle plateforme ?");
    		 String support = sc45.nextLine();
    		 afficherInfosJeuxPourJoueur(rep6,support);
    		 System.out.println(" Souhaitez vous faire autre chose?");
    		 System.out.println(" - Si oui, repondez oui ");
    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
    		 Scanner sc42=new Scanner(System.in);
    		 System.out.println("Votre choix : ");
    		 String rep42 = sc42.nextLine();
    		 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
    			 System.out.println("Veuillez repondre par oui ou par non");
    			 String reponseEncore = sc42.nextLine();
    			 rep4 = reponseEncore;
    	    }
    		 if(rep42.equalsIgnoreCase("non")) {
    			 deconnecter();
    		 }
    		 else if (rep42.equalsIgnoreCase("oui")) {
    			 choixJoueur();
    		 }
    	     break;
    	 case "8":
    		 System.out.println("Vous avez choisi de placer un ou des jetons sur un jeu ");
    		 placerJetons();
    		 System.out.println(" Souhaitez vous faire autre chose?");
    		 System.out.println(" - Si oui, repondez oui ");
    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
    		 Scanner sc40=new Scanner(System.in);
    		 System.out.println("Votre choix : ");
    		 String rep45 = sc40.nextLine();
    		 while (!rep45.equalsIgnoreCase("non")&&!rep45.equalsIgnoreCase("oui")) {
    			 System.out.println("Veuillez repondre par oui ou par non");
    			 String reponseEncore = sc40.nextLine();
    			 rep45 = reponseEncore;
    	    }
    		 if(rep45.equalsIgnoreCase("non")) {
    			 deconnecter();
    		 }
    		 else if (rep45.equalsIgnoreCase("oui")) {
    			 choixJoueur();
    		 }
    	     break;
    	 	}
	}
	
	//methode qui est appelée lorsque qu'un testeur fait une action après la connexion ou l'inscription (permet aux testeurs de faire plusieurs actions de suite)
	public void choixTesteur() throws IOException {
		
		System.out.println("Que souhaitez vous faire ? Petit rappel : en tant que testeur voila ce que pouvez faire, choisissez ce que vous voulez faire");
	 	System.out.println(" - Afficher les informations sur un membre : 1 ");
	 	System.out.println(" - Ajouter un jeu : 2 ");
	 	System.out.println(" - Evaluer un jeu : 3 ");
	 	System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
	 	System.out.println(" - Signaler une evaluation problematique : 5");
	 	System.out.println(" - Ecrire un test pour un jeu : 6");
	 	System.out.println(" - Vous deconnecter : 7");
	 	System.out.println(" - Se desinscrire : 8 ");
	 	System.out.println(" - Afficher les informations sur jeu : 9");
	 	String choix;
	 	Scanner sc2 = new Scanner(System.in);
	 	System.out.println("Votre choix : ");
	 	choix = sc2.nextLine();
	 	if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")) {
	 		do {
   	 			System.out.println("Veuillez choisi un chiffre entre 1 et 8 par rapport à ce que vous voulez faire");
   	    	 	System.out.println("En tant que testeur voila ce que pouvez faire, choisissez ce que vous voulez faire");
   	    	 	System.out.println(" - Afficher les informations sur un membre : 1 ");
	    	 	System.out.println(" - Ajouter un jeu : 2 ");
	    	 	System.out.println(" - Evaluer un jeu : 3 ");
	    	 	System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
	    	 	System.out.println(" - Signaler une evaluation problematique : 5");
	    		System.out.println(" - Ecrire un test pour un jeu : 6");
	   	 		System.out.println(" - Vous deconnecter : 7");
	   	 		System.out.println(" - Se desinscrire : 8 ");
	   	 		System.out.println(" - Afficher les informations sur jeu : 9");
	   	 		choix = sc2.nextLine();
	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9"));
	 	}
	    	 switch (choix) {
	    	 case "1":
	    		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
	    	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
	    	     String pseudo2 = sc2.nextLine();
	    	     afficherInfos(pseudo2);
	    	     System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc4=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep = sc4.nextLine();
	    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc4.nextLine();
	    			 rep = reponseEncore;
	    	    }
	    		 if(rep.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep.equalsIgnoreCase("oui")) {
	    			 choixTesteur();
	    		 }
	    	     break;
	    	 case "2":
	    	     System.out.println("Vous avez choisi d'ajouter un jeu ");
	    	     ajouterJeu();
	    	     System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc33=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep3 = sc33.nextLine();
	    		 while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc33.nextLine();
	    			 rep3 = reponseEncore;
	    	    }
	    		 if(rep3.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep3.equalsIgnoreCase("oui")) {
	    			 choixTesteur();
	    		 }
	    	     break;
	    	 case "8":
	    	      System.out.println("Vous avez choisi de vous desinscrire ");
	    	      desinscrire();
	    	      choix(); //Retour au menu
	    	      return;
	    	 case "3":
	    	      System.out.println("Vous avez choisi d'evaluer un jeu ");
	    	      evaluer();
	    	      System.out.println(" Souhaitez vous faire autre chose?");
		    		 System.out.println(" - Si oui, repondez oui ");
		    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		    		 Scanner sc34=new Scanner(System.in);
		    		 System.out.println("Votre choix : ");
		    		 String rep4 = sc34.nextLine();
		    		 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
		    			 System.out.println("Veuillez repondre par oui ou par non");
		    			 String reponseEncore = sc34.nextLine();
		    			 rep4 = reponseEncore;
		    	    }
		    		 if(rep4.equalsIgnoreCase("non")) {
		    			 deconnecter();
		    		 }
		    		 else if (rep4.equalsIgnoreCase("oui")) {
		    			 choixTesteur();
		    		 }
	    	      break;
	    	 case "4":
	    		 System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
	      	   
	      	   boolean a=afficherEvaluationPourUnJeu
	      			   ();
	      	   if(a==false) {
	      		   System.out.println("Vous ne posseder pas de jeu");
	      		   
	      	   }
	      	   else if(a==true) {
	      		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
	      		   Scanner sc41= new Scanner(System.in);
	          	   String repA = sc41.nextLine();
	          	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
	          	   		System.out.println("Veuillez repondre par oui ou par non");
	          	   		String newRep = sc41.nextLine();
	          	   		repA=newRep;
	          	   }
	          	   if(repA.equalsIgnoreCase("oui")) { 
	          		   if(utilisateur.getJeux().isEmpty()) {
	          			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
	          		   }
	          		   else {
	          			   evaluerEvaluation();
	          		   }
	          	   }
	      	   }
	      	   
	    		 System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc44=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep44 = sc44.nextLine();
	    		 while (!rep44.equalsIgnoreCase("non")&&!rep44.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc44.nextLine();
	    			 rep44 = reponseEncore;
	    	    }
	    		 if(rep44.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep44.equalsIgnoreCase("oui")) {
	    			 choixTesteur();
	    		 }
	    		 break;
	    	 case "5":
	    		 System.out.println("Vous avez choisi de signaler une evaluation problematique");
	    		 System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc5=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep5 = sc5.nextLine();
	    		 while (!rep5.equalsIgnoreCase("non")&&!rep5.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc5.nextLine();
	    			 rep5 = reponseEncore;
	    	    }
	    		 if(rep5.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep5.equalsIgnoreCase("oui")) {
	    			 choixTesteur();
	    		 }
	    		 break;
	    	 case "6" :
	    		 System.out.println("Vous avez choisi d'ecrire un test pour un jeu");
	    		 Tester();
	    		 System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc6=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep6 = sc6.nextLine();
	    		 while (!rep6.equalsIgnoreCase("non")&&!rep6.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc6.nextLine();
	    			 rep6 = reponseEncore;
	    	    }
	    		 if(rep6.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep6.equalsIgnoreCase("oui")) {
	    			 choixTesteur();
	    		 }
	    		 break;
	    	 case "7":
	    		System.out.println("Vous avez choisi de vous deconnecter");
	    		deconnecter();
	    		choix(); //Retour au menu
	    		return;
	    	 case "9":
	    		 System.out.println("Vous avez choisi de voire les informations d'un jeu");
	    		 System.out.println("De quel jeu voulez vous avoir les informations ?");
	    		 Scanner sc45=new Scanner(System.in);
	    		 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
	    		 String rep9 = sc45.nextLine();
	    		 System.out.println("Sur quelle plateforme ?");
	    		 String support = sc45.nextLine();
	    		 afficherInfosJeuxPourTesteur(rep9,support); //Les testeurs peuvent voir plus de choses que les joueurs mais moins que les administrateurs
	    		 System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
	    		 Scanner sc42=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep42 = sc42.nextLine();
	    		 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
	    			 System.out.println("Veuillez repondre par oui ou par non");
	    			 String reponseEncore = sc42.nextLine();
	    			 rep4 = reponseEncore;
	    	    }
	    		 if(rep42.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep42.equalsIgnoreCase("oui")) {
	    			 choixTesteur();
	    		 }
	    	     break;

	    	 	}
	}
	
	//Methode qui permet, lorsque l'on est administrateur, de supprimer les évaluations qui onr eté signalées
	public void supprimerEval() {
		
		if(estVide()==false) {
			
			System.out.println("Il n'y a pas d'evaluations qui ont ete signalees");
			
		}
		
		//affichage des évaluations par nombre de signalements décroissants pour voir quelle évaluation est la plus problematique
		//stockage de la liste de ces evaluations qui ont ete signalees
		ArrayList<Evaluation> evals = afficherEvaluationsSignalees(); 
		System.out.println("Voulez vous supprimer l'une d'entre elles ? Repondre par oui ou par non");
		Scanner sc = new Scanner(System.in);
		String sup= sc.nextLine();
		
		//Decision de la supprimer ou pas 
		while(!sup.equalsIgnoreCase("oui")&& !sup.equalsIgnoreCase("non")) {
			System.out.println("Veuillez repondre par oui ou par non");
			sup= sc.nextLine();
		}
		
		if(sup.equalsIgnoreCase("non")) {
			System.out.println("Tres bien, vous ne voulez pas supprimer d'evaluations");
			return;
		}
		
		else if (sup.equalsIgnoreCase("oui")) {
			
			System.out.println("Veuillez inscrire le titre de l'evaluation que vous voulez supprimer : ");
			 String evalSupp= sc.nextLine();
			 Evaluation eval =chercherEvaluationParTitre(evalSupp,evals); //cherche l'evaluation dans la liste des evaluations signalees recuperees plus haut et la renvoie quand on la trouve
			 while(eval==null) {
				 
				 System.out.println("Cette evaluation n'existe pas");
				 System.out.println("Voulez vous toujours supprimer une evaluation (oui pour oui et non pour non)? Pour rappel, voici les evaluations pour ce jeu :");
				 afficherEvaluationsSignalees();
				 String rep69=sc.nextLine();
				 
				 while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
					 
			   	   		System.out.println("Veuillez repondre par oui ou par non");
			   	   		String newRep = sc.nextLine();
			   	   		rep69=newRep;
			   	   		
			   	 	}
				 if(rep69.equalsIgnoreCase("oui")) {
					System.out.println("Veuillez inscrire le titre de l'evaluation que vous voulez supprimer : ");
					evalSupp=sc.nextLine();
				 }
					if(rep69.equalsIgnoreCase("non")) {
						System.out.println("D'accord, vous ne voulez plus supprimer cette evaluation");
						return; //Retour à la methode d'où cette methode a été appelée
					}	 
				 }
			 	System.out.println("Etes vous sûr de vouloir supprimer cette evaluation ?");
			 	System.out.println("titre de l'evaluation : " + eval.getTitre());
			 	System.out.println("contenu de l'evaluation : " + eval.getCommentaire());
			 	String rep69=sc.nextLine();
			 	while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
			 		
		   	   		System.out.println("Veuillez repondre par oui ou par non");
		   	   		String newRep = sc.nextLine();
		   	   		rep69=newRep;
		   	   		
		   	 	}
			 	if(rep69.equalsIgnoreCase("oui")) {
			 		
					 System.out.println("Cette evaluation a bien ete supprimee"); 
					 verificationEval(eval.getTitre(),eval.getCommentaire(),eval); //Supprime toutes les evaluations positives, neutres et négatives de cette évaluation
					 eval.getAuteur().supprimerEval(eval); //Supprime l'évaluation de la liste des évaluations de l'auteur
					 fichierMembre.sauvegarderMembres(profils); //Ecris dans le fichier membre toutes les informations importantes à stocker, donc ici apres la suppression d'une evaluation il faut sauvegarder le fait qu'elle ne soit plus la
					 
			 	}
			 	if(rep69.equalsIgnoreCase("non")) {
			 		
				 System.out.println("Annulation : cette evaluation n'a pas ete supprimee");
				 
			 	} 
		 	}
		}

	
	//Methode qui est appelée lorsque qu'un administateur fait une action après la connexion ou l'inscription (permet aux administrateurs de faire plusieurs actions de suite)
	public void choixAdministrateur() throws IOException {
		
		System.out.println("Que souhaitez vous donc faire ? Petit rappel en tant qu'administrateur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		System.out.println(" - Afficher les informations sur un membre : 1 ");
 		System.out.println(" - Ajouter un jeu : 2 ");
 		System.out.println(" - Evaluer un jeu : 3 ");
 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
 		System.out.println(" - Signaler une evaluation problematique : 5");
 		System.out.println(" - Ecrire un test pour un jeu : 6");
 		System.out.println(" - Desinscrire un joueur : 7");
 		System.out.println(" - Promouvoir un joueur : 8"); 
 		System.out.println(" - Bloquer un joueur : 9");
 		System.out.println(" - Supprimer l'evaluation d'un joueur : 10");
 		System.out.println(" - Vous deconnecter : 11");
 		System.out.println(" - Se desinscrire : 12 ");
 		System.out.println(" - Afficher les informations sur jeu : 13");
 		System.out.println(" - Debloquer un joueur : 14");
 		String choix;
 		Scanner sc2 = new Scanner(System.in);
 		System.out.println("Votre choix : ");
 		choix = sc2.nextLine();
 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")&& !choix.equalsIgnoreCase("10")&& !choix.equalsIgnoreCase("11")&& !choix.equalsIgnoreCase("12")&& !choix.equalsIgnoreCase("13")&& !choix.equalsIgnoreCase("14")) {
 			do {
 			System.out.println("Veuillez choisi un chiffre entre 1 et 12 par rapport à ce que vous voulez faire");
	    	 	System.out.println("En tant qu'administrateur voila ce que pouvez faire, choisissez ce que vous voulez faire");
	    	 	System.out.println(" - Afficher les informations sur un membre : 1 ");
	 		System.out.println(" - Ajouter un jeu : 2 ");
	 		System.out.println(" - Evaluer un jeu : 3 ");
	 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
	 		System.out.println(" - Signaler une evaluation problematique : 5");
	 		System.out.println(" - Ecrire un test pour un jeu : 6");
	 		System.out.println(" - Desinscrire un joueur : 7");
	 		System.out.println(" - Promouvoir un joueur : 8"); 
	 		System.out.println(" - Bloquer un joueur : 9");
	 		System.out.println(" - Supprimer l'evaluation d'un joueur : 10");
	 		System.out.println(" - Vous deconnecter : 11");
	 		System.out.println(" - Se desinscrire : 12 ");
	 		System.out.println(" - Afficher les informations sur jeu : 13");
	 		System.out.println(" - Debloquer un joueur : 14");
   	 		choix = sc2.nextLine();
 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")&& !choix.equalsIgnoreCase("10")&& !choix.equalsIgnoreCase("11")&& !choix.equalsIgnoreCase("12") && !choix.equalsIgnoreCase("13")&& !choix.equalsIgnoreCase("14"));
 		}
   	 switch (choix) {
   	 case "1":
   		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
   	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
   	     String pseudo2 = sc2.nextLine();
   	     afficherInfos(pseudo2);
   	     System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc4=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep = sc4.nextLine();
		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc4.nextLine();
			 rep = reponseEncore;
	    }
		 if(rep.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   	     break;
   	 case "2":
   	     System.out.println("Vous avez choisi d'ajouter un jeu ");
   	     ajouterJeu();
   	  System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc5=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep2 = sc5.nextLine();
		 while (!rep2.equalsIgnoreCase("non")&&!rep2.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc5.nextLine();
			 rep2 = reponseEncore;
	    }
		 if(rep2.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep2.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   	      break;
   	 case "12":
   	      System.out.println("Vous avez choisi de vous desinscrire ");
   	      desinscrireSoisMemeAdmin();
   	      choix();
   	      return;
   	 case "3":
   	      System.out.println("Vous avez choisi d'evaluer un jeu ");
   	      evaluer();
   	      System.out.println(" Souhaitez vous faire autre chose?");
   	      System.out.println(" - Si oui, repondez oui ");
   	      System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
   	      Scanner sc123=new Scanner(System.in);
   	      System.out.println("Votre choix : ");
   	      String rep3 = sc123.nextLine();
   	      while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
   	    	  System.out.println("Veuillez repondre par oui ou par non");
   	    	  String reponseEncore = sc123.nextLine();
   	    	  rep3 = reponseEncore;
	    }
		 if(rep3.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep3.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   	      break;
   	 case "4":
   		System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
 	   
 	   boolean a=afficherEvaluationPourUnJeu();
 	   if(a==false) {
 		   System.out.println("Vous ne posseder pas de jeu");
 		   
 	   }
 	   else if(a==true) {
 		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
 		   Scanner sc41= new Scanner(System.in);
     	   String repA = sc41.nextLine();
     	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
     	   		System.out.println("Veuillez repondre par oui ou par non");
     	   		String newRep = sc41.nextLine();
     	   		repA=newRep;
     	   }
     	   if(repA.equalsIgnoreCase("oui")) { 
     		   if(utilisateur.getJeux().isEmpty()) {
     			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
     		   }
     		   else {
     			   evaluerEvaluation();
     		   }
     	   }
 	   }
 	   
   		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc1=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep4 = sc1.nextLine();
		 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc1.nextLine();
			 rep4 = reponseEncore;
	    }
		 if(rep4.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep4.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
   	 case "5":
   		 System.out.println("Vous avez choisi de signaler une evaluation problematique");
   		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc51=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep5 = sc51.nextLine();
		 while (!rep5.equalsIgnoreCase("non")&&!rep5.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc51.nextLine();
			 rep5 = reponseEncore;
	    }
		 if(rep5.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep5.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
   	 case "6" :
   		 System.out.println("Vous avez choisi d'ecrire un test pour un jeu");
   		 Tester();
   		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc6=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep6 = sc6.nextLine();
		 while (!rep6.equalsIgnoreCase("non")&&!rep6.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc6.nextLine();
			 rep6 = reponseEncore;
	    }
		 if(rep6.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep6.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
   	 case "11":
   		System.out.println("Vous avez choisi de vous deconnecter");
   		deconnecter();
   		return;
   	case "7":
   		System.out.println("Vous avez choisi de desinscrire un joueur");
   		desinscrire();
   		System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc7=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep7 = sc7.nextLine();
		 while (!rep7.equalsIgnoreCase("non")&&!rep7.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc7.nextLine();
			 rep7 = reponseEncore;
	    }
		 if(rep7.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep7.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
   	case "8":
   		System.out.println("Vous avez choisi de promouvoir un joueur");
   		promotion();
   		System.out.println(" Souhaitez vous faire autre chose?");
		System.out.println(" - Si oui, repondez oui ");
		System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		Scanner sc8=new Scanner(System.in);
		System.out.println("Votre choix : ");
		String rep8 = sc8.nextLine();
		 while (!rep8.equalsIgnoreCase("non")&&!rep8.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc8.nextLine();
			 rep8 = reponseEncore;
	    }
		 if(rep8.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep8.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
   	case "9":
   		System.out.println("Vous avez choisi de bloquer un joueur");
   		bloquerJoueur();
   		System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc9=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep9 = sc9.nextLine();
		 while (!rep9.equalsIgnoreCase("non")&&!rep9.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc9.nextLine();
			 rep = reponseEncore;
	    }
		 if(rep9.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep9.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
   	case "10":
   		System.out.println("Vous avez choisi de suprimer l'evaluation d'un joueur");
   		supprimerEval();
   		System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc10=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep10 = sc10.nextLine();
		 while (!rep10.equalsIgnoreCase("non")&&!rep10.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
		 	String reponseEncore = sc10.nextLine();
		 	rep = reponseEncore;
	    }
		 if(rep10.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep10.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
   		 break;
    case "13":
		 System.out.println("Vous avez choisi de voire les informations d'un jeu");
		 System.out.println("De quel jeu voulez vous avoir les informations ?");
		 Scanner sc45=new Scanner(System.in);
		 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
		 String rep13 = sc45.nextLine();
		 System.out.println("Sur quelle plateforme ?");
		 String support = sc45.nextLine();
		 afficherInfosJeuxPourAdministrateur(rep13,support);
		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc42=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep42 = sc42.nextLine();
		 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc42.nextLine();
			 rep42 = reponseEncore;
	    }
		 if(rep42.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep42.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
	     break;
    case "14":
    	 System.out.println(" Vous avez choisi de debloquer un joueur");
    	 debloquerJoueur();
		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc22=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep45 = sc22.nextLine();
		 while (!rep45.equalsIgnoreCase("non")&&!rep45.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc22.nextLine();
			 rep45 = reponseEncore;
	    }
		 if(rep45.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep45.equalsIgnoreCase("oui")) {
			 choixAdministrateur();
		 }
	     break;
   	 	}
	return;
	}
	
	//Methode qui vérifie si un joueur bloqué ou non
	public boolean bloqueOuPas() {
		boolean a=false;
		for (Membre m: profils) {
			if (m.getRole().equalsIgnoreCase("invite")) { //Un joueur bloqué reste dans la liste des utilisateurs de la plateforme, mais en tant qu'invite
				 return true;
	        }
		}
		return a;
	}
	
	//Methode qui affiche la liste des joueurs qui sont bloqués
	public void afficherBloque() {
		for (Membre m: profils) {
			if (m.getRole().equalsIgnoreCase("invite")) {
				 System.out.println("- " + m.getPseudo()+" : "+m.getRole());
	        }
		}
	}
	
	//Methode qui cherche un joueur bloque à travers son pseudo (chercher un invite qui a pour pseudo celui en parametre et qui est dans la liste des membres)
	public Membre chercherBloque(String pseudo) {
			for (Membre m: profils) {
				if (m.getPseudo().equalsIgnoreCase(pseudo)&& m.getRole().equalsIgnoreCase("invite")) {
		            return m;
		        }
			}
			return null;
	}
	
	//Methode qui permet de debloquer un joueur
	public void debloquerJoueur() {
		
		//Permet de verifier si il y a des joueurs bloques
		boolean a=bloqueOuPas();
		if(a==false) {
			//Il n'y a donc pas de joueurs bloques
			System.out.println("Il n'y a pas de joueurs bloques");
			
		}
		else {
			
			//Il y a donc des joueurs bloques
			System.out.println("Voici la liste des joueurs bloques : ");
			//Affiche les joueurs bloques
			afficherBloque(); 
			System.out.println("Parmi ces truands, voulez vous en debloquer un ? (oui ou non)");
			Scanner sc = new Scanner(System.in);
			String rep= sc.nextLine();
			while(!rep.equalsIgnoreCase("oui") && !rep.equalsIgnoreCase("non")) {
				
				System.out.println("Veuillez repondre par oui ou par non");
				rep=sc.nextLine();
				
			}
			if(rep.equalsIgnoreCase("oui")) {
				System.out.println("Inscrivez le pseudo du joueur que vous voulez debloquer : :");
				String pseudo = sc.nextLine();
				Membre membre=chercherBloque(pseudo); //Cherche le pseudo du joueur a debloquer dans la liste des membres (en recherchant un invite qui a ce pseudo)
				while(membre==null) {
					
					if(membre==null) {
						
						System.out.println("Desole mais ce pseudo n'appartient pas à un des joueurs bloques");
						System.out.println("Voulez-vous toujours debloquer un joueur? Repondez par oui ou par non");
						String rep2=sc.nextLine();
						while(!rep2.equalsIgnoreCase("oui")&& !rep2.equalsIgnoreCase("non")) {
							
							System.out.println("Veuillez repondre par oui ou par non");
							rep2=sc.nextLine();
							
						}
						if(rep2.equalsIgnoreCase("oui")) {
							
							System.out.println("Inscrivez le pseudo du joueur que vous voulez debloquer");
							 pseudo= sc.nextLine();
							 membre=chercherBloque(pseudo);
							 
						}
						if(rep2.equalsIgnoreCase("non")) {
							System.out.println("Très bien, vous ne voulez plus debloquer de joueur");
							return;
						}
					}
					
				}
				System.out.println("Etes-vous sur de vouloir debloquer le joueur "+pseudo+" ?");
				String rep2= sc.nextLine();
				while(!rep2.equalsIgnoreCase("oui")&& !rep2.equalsIgnoreCase("non")) {
					System.out.println("Veuillez repondre par oui ou par non");
					rep2=sc.nextLine();
				}
				if(rep.equalsIgnoreCase("oui")) {
					System.out.println("Vous avez debloque le joueur "+pseudo);
					profils.remove(membre); //Supprimer le joueur en tant que invite (supprime le fait qu'il soit bloquer)
					Joueur joueur= new Joueur(pseudo);
					profils.add(joueur); //Remet le joueur debloque dans la liste des membre en tant que joueur
					joueur.SetJetons(3); //Redistribution de ses 3 jetons (car en tant qu'invite il n'avait plus de jetons
					
					fichierMembre.sauvegarderMembres(profils);//permet d'ecrire dans le fichier texte et donc de sauvegarder le fait que le joueur a ete debloque
				}
				
			}
			if(rep.equalsIgnoreCase("non")) {
				System.out.println("Tres bien, vous avez decider de ne pas debloquer de joueur");
				return;
			}
		}
	}
	
	//Methode qui permet de bloquer un Joueur
	public void bloquerJoueur() {
		
		//Methode qui permet d'afficher la liste de tous les joueurs de la plateforme
		afficherJoueurs();
		System.out.println("Inscrivez le pseudo du joueur que vous voulez bloquer");
		Scanner sc=new Scanner(System.in);
		String pseudo= sc.nextLine();
		Membre membre=chercherPseudo(pseudo); //pour verifier si il y a un membre de ce pseudo
		Membre membre2= chercherJoueur(pseudo); //pour verifiter si il y a un joueur de ce pseudo
		while(membre==null || membre2==null) {
			if(membre==null) { //le pseudo appartient à aucun utilisateur
				System.out.println("Desole, mais ce pseudo n'appartient à aucun utilisateur sur cette plateforme");
				System.out.println("Voulez-vous toujours bloquer un joueur?  Repondez par oui ou par non");
				String rep=sc.nextLine();
				while(!rep.equalsIgnoreCase("oui")&& !rep.equalsIgnoreCase("non")) {
					System.out.println("Veuillez repondre par oui ou par non");
					rep=sc.nextLine();
				}
				if(rep.equalsIgnoreCase("oui")) {
					System.out.println("Inscrivez le pseudo du joueur que vous voulez bloquer");
					 pseudo= sc.nextLine();
					 membre=chercherPseudo(pseudo);
					 membre2= chercherJoueur(pseudo);
				}
				if(rep.equalsIgnoreCase("non")) {
					System.out.println("Très bien, vous ne voulez plus bloquer de joueur");
					return;
				}
			}
			if(membre2==null) { //le pseudo n'appartient pas à un joueur, mais le pseudo appartient bel et bien à un utilisateur de la plateforme
				
				System.out.println("Desole mais cet utilisateur n'est pas un joueur, vous pouvez bloquer uniquement les joueurs");
				System.out.println("Voulez-vous toujours bloquer un joueur? Repondez par oui ou par non");
				String rep=sc.nextLine();
				
				while(!rep.equalsIgnoreCase("oui")&& !rep.equalsIgnoreCase("non")) {
					System.out.println("Veuillez repondre par oui ou par non");
					rep=sc.nextLine();
				}
				if(rep.equalsIgnoreCase("oui")) {
					System.out.println("Inscrivez le pseudo du joueur que vous voulez bloquer");
					 pseudo= sc.nextLine();
					 membre=chercherPseudo(pseudo);
					 membre2= chercherJoueur(pseudo);
				}
				if(rep.equalsIgnoreCase("non")) {
					System.out.println("Très bien, vous ne voulez plus bloquer de joueur");
					return;
				}
			}
			
		}
		System.out.println("Etes-vous sur de vouloir bloquer "+pseudo+". Cet utilisateur perdra toutes ces donnees, et sera bride en tant qu'invite.  Repondez par oui ou par non");
		String ans=sc.nextLine();
		while(!ans.equalsIgnoreCase("oui")&& !ans.equalsIgnoreCase("non")) {
			System.out.println("Veuillez repondre par oui ou par non");
			ans=sc.nextLine();
		}
		if(ans.equalsIgnoreCase("oui")) {
			System.out.println("Le joueur "+pseudo+ " a bien ete bloque");
			profils.remove(membre); //Supprime le joueur de la liste des membres et de toutes ces données 
			Invite invite = new Invite(pseudo);
			profils.add(invite); //Le joueur devient un invite et est ajoute dans la liste des membre pour ne pas oublier son pseudo. Ce qui permettra de le debloquer pourquoi pas
			fichierMembre.sauvegarderMembres(profils);//permet d'ecrire dans le fichier texte et donc de sauvegarder le fait que le joueur a ete bloque (nouveau invite)
			return;
		}
		else if(ans.equalsIgnoreCase("non")) {
			System.out.println("Très bien, vous avez annuler le bloquage");
			return;
		}
	}
	
	//methode qui est appelée lorsque qu'un invite fait une action après la connexion ou l'inscription (permet aux invites de faire plusieurs actions de suite)
	public void choixInvite() throws IOException {
		System.out.println(" Que souhaitez vous donc faire ? Petit rappel : en tant qu'invite, choisissez ce que vous voulez faire parmis cette liste");
 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
 		System.out.println(" - Vous deconnecter : 2");
 		String choix;
 		Scanner sc3 = new Scanner(System.in);
 		System.out.println("Votre choix : ");
 		choix = sc3.nextLine();
 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2")) {
 			do {
 			System.out.println("Veuillez choisi un chiffre entre 1 et 2 par rapport à ce que vous voulez faire");
	    	 	System.out.println("En tant qu'invite voila ce que pouvez faire, choisissez ce que vous voulez faire");
   	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
   	 		System.out.println(" - Vous deconnecter : 2");
   	 		choix = sc3.nextLine();
 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2"));
 		}
	    	 switch (choix) {
	    	 case "1":
	    		 System.out.println(" Vous avez choisi de voire les evaluations d'un jeu");
	    		 afficherEvaluationPourUnJeu(); 
	    		 System.out.println(" Souhaitez vous faire autre chose?");
	    		 System.out.println(" - Si oui, repondez oui ");
	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnte ");
	    		 Scanner sc4=new Scanner(System.in);
	    		 System.out.println("Votre choix : ");
	    		 String rep = sc3.nextLine();
	    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
	    		System.out.println("Veuillez repondre par oui ou par non");
	    		String reponseEncore = sc4.nextLine();
	  	    	rep = reponseEncore;
	  	    }
	    		 if(rep.equalsIgnoreCase("non")) {
	    			 deconnecter();
	    		 }
	    		 else if (rep.equalsIgnoreCase("oui")) {
	    			 choixInvite();
	    		 }
	    		 break;
	    	 case "2":
	    		System.out.println("Vous avez choisi de vous deconnecter");
	    		deconnecter();
	    		break;
	    	 	}
        return;
}

	//Methode qui permet aux joueurs de se deconnecter et qui permet de retourner au menu ou de quitter la plateforme
	public void deconnecter() throws IOException {
		System.out.println("Deconnexion : souhaitez vous quittez l'application ou retourner sur le menu principal ?");
		Scanner sc=new Scanner(System.in);
		System.out.println(" Si vous souhaitez retourner au menu principale : mettez 1");
		System.out.println(" Si vous souhaitez quittez l'application : mettez 2");
		String reponse=sc.nextLine();
		while (!reponse.equalsIgnoreCase("1")&&!reponse.equalsIgnoreCase("2")) {
	    	System.out.println("Veuillez repondre par 1 ou 2");
	    	String reponseEncore = sc.nextLine();
	    	reponse = reponseEncore;
	    }
		if(reponse.equalsIgnoreCase("1")) {
			choix();
		}
		if(reponse.equalsIgnoreCase("2")) {
			System.out.println("Au revoir, n'hesitez pas à revenir"+utilisateur.getPseudo());
			System.out.println("Vous avez quitté la plateforme (momentanement, vous n'etes pas désinscrit)");
		}
	}
	
	//methode qui permet d'afficher toutes les evaluations pour un jeu, qui utilise notamment une autre methode pour avoir la liste des evaluations pour un jeu donne
	public boolean afficherEvaluationPourUnJeu () {
		
		boolean a=true;
		System.out.println("Indiquez de quel jeu vous voulez voire les evaluations : ");
		Scanner sc= new Scanner(System.in);
		String rep=sc.nextLine();
		System.out.println("Sous quelle plateforme ?");
		String rep2=sc.nextLine();
		Jeu jeu;
		try {
			jeu = new Jeu(rep,rep2);
			if (jeu.getNomJeu().equalsIgnoreCase("null")) {
				System.out.println("Desole, mais ce jeu n'existe pas");
				return false;
			}
			else {
				ArrayList<Evaluation> evals=ajouterEvaluationsPourJeu(rep);
				if(evals.isEmpty()) {
					return false;
				}
			    System.out.println(afficherEvaluationsPourJeu(evals,rep));
			    return true;
			}	
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	//Methode qui sert de menu, qui permet soit de se connecter , soit de s'inscrire
	public void choix () throws IOException {
		System.out.println("    MENU PRINCIPAL : ");
		Scanner sc = new Scanner(System.in);
		System.out.println(" Avez-vous deja un compte?");
		System.out.println(" - Si oui, repondez oui ");
		System.out.println(" - Si non, repondez non ");
		System.out.println(" - Si vous voulez vous connecter en tant qu'invite, repondez invite"); //On peut soit se connecter, soit s'inscrire, soit se connecter en tant qu'invite
	    String reponse = sc.nextLine();
	    while (!reponse.equalsIgnoreCase("non")&&!reponse.equalsIgnoreCase("oui")&&!reponse.equalsIgnoreCase("invite")) {
	    	
	    	System.out.println("Veuillez repondre par oui, non ou invite");
	    	String reponseEncore = sc.nextLine();
	    	reponse = reponseEncore;
	    	
	    }
	    if (reponse.equalsIgnoreCase("oui")) {	    	
	    	connexion(); //L'utilisateur a choisi de se connecter
	    }
	    if(reponse.equalsIgnoreCase("non")) {
	    	Scanner sc2 = new Scanner(System.in);
	    	System.out.println("Souhaitez vous vous inscrire? Si oui, repondez oui. Sinon dites non, et vous serez connecter en tant qu'invite. ");
		    String reponse2 = sc2.nextLine();
		    while (!reponse2.equalsIgnoreCase("non")&&!reponse2.equalsIgnoreCase("oui")) {
		    	System.out.println("Veuillez repondre par oui ou pas non");
		    	String reponseEncore2 = sc2.nextLine();
		    	reponse2 = reponseEncore2;
		    }
		    if(reponse2.equalsIgnoreCase("oui")) {
		    	inscrire(); //L'utilisateur n'a pas de compte, il a alors décider de s'inscire
		    }
		    if(reponse2.equalsIgnoreCase("non")) { //Si l'utilisateur ne se connecte pas et ne s'inscrit pas, il est alors connecter en tant qu'invite
		    	 
		    	System.out.println("Bonjour, vous etes connecte en tant qu'invite");
		    	 utilisateur = new Invite();
		    	 System.out.println("En tant qu'invite, choisissez ce que vous voulez faire");
	    	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
	    	 		System.out.println(" - Vous deconnecter : 2");
	    	 		String choix;
	    	 		Scanner sc3 = new Scanner(System.in);
	    	 		System.out.println("Votre choix : ");
	    	 		choix = sc3.nextLine();
	    	 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2")) {
	    	 			do {
	    	 			System.out.println("Veuillez choisi un chiffre entre 1 et 2 par rapport à ce que vous voulez faire");
		   	    	 	System.out.println("En tant qu'invite voila ce que pouvez faire, choisissez ce que vous voulez faire");
	   	    	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
	   	    	 		System.out.println(" - Vous deconnecter : 2");
	   	    	 		choix = sc3.nextLine();
	    	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2"));
	    	 		}
		   	    	 switch (choix) {
		   	    	 case "1":
		   	    		 System.out.println(" Vous avez choisi de voire les evaluations d'un jeu");
		   	    		 afficherEvaluationPourUnJeu(); 
		   	    		 System.out.println(" Souhaitez vous faire autre chose?");
		   	    		 System.out.println(" - Si oui, repondez oui ");
		   	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   	    		 Scanner sc4=new Scanner(System.in);
		   	    		 System.out.println("Votre choix : ");
		   	    		 String rep = sc3.nextLine();
		   	    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
		   	    		System.out.println("Veuillez repondre par oui ou par non");
		   	    		String reponseEncore = sc.nextLine();
		   	  	    	rep = reponseEncore;
		   	  	    }
		   	    		 if(rep.equalsIgnoreCase("non")) {
		   	    			 deconnecter();
		   	    		 }
		   	    		 else if (rep.equalsIgnoreCase("oui")) {
		   	    			 choixInvite();
		   	    		 }
		   	    		 break;
		   	    	 case "2":
		   	    		System.out.println("Vous avez choisi de vous deconnecter");
		   	    		deconnecter();
		   	    		break;
		   	    	 	}
		            return;
		    }
	    }
	    
		    if(reponse.equalsIgnoreCase("invite")) {  //L'utilisateur a directement décidé de se connecter en tant qu'invite
		    	
		    	System.out.println("Bonjour, vous etes connecte en tant qu'invite");
		    	 utilisateur = new Invite();
		    	 System.out.println("En tant qu'invite, choisissez ce que vous voulez faire");
	    	 	System.out.println(" - Voire les evaluations d'un jeu : 1 ");
	    	 	System.out.println(" - Vous deconnecter : 2");
	    	 	String choix;
	    	 	Scanner sc3 = new Scanner(System.in);
	    	 	System.out.println("Votre choix : ");
	    	 	choix = sc3.nextLine();
	    	 		
	    	 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2")) {
	    	 			do {
	    	 			System.out.println("Veuillez choisi un chiffre entre 1 et 2 par rapport à ce que vous voulez faire");
		   	    	 	System.out.println("En tant qu'invite voila ce que pouvez faire, choisissez ce que vous voulez faire");
	   	    	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
	   	    	 		System.out.println(" - Vous deconnecter : 2");
	   	    	 		choix = sc3.nextLine();
	    	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2"));
	    	 		}
		   	    	 switch (choix) {
		   	    	 case "1":
		   	    		 System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
		   	    		 afficherEvaluationPourUnJeu(); 
		   	    		 System.out.println(" Souhaitez vous faire autre chose?");
		   	    		 System.out.println(" - Si oui, repondez oui ");
		   	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnte ");
		   	    		 Scanner sc4=new Scanner(System.in);
		   	    		 System.out.println("Votre choix : ");
		   	    		 String rep = sc3.nextLine();
		   	    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
		   	    		System.out.println("Veuillez repondre par oui ou par non");
		   	    		String reponseEncore = sc.nextLine();
		   	  	    	rep = reponseEncore;
		   	  	    }
		   	    		 if(rep.equalsIgnoreCase("non")) {
		   	    			 deconnecter();
		   	    		 }
		   	    		 else if (rep.equalsIgnoreCase("oui")) {
		   	    			 choixInvite();
		   	    		 }
		   	    		 break; 
		   	    	 case "2":
		   	    		System.out.println("Vous avez choisi de vous deconnecter");
		   	    		deconnecter();
		   	    		 break;
		   	    	 	}
		            return;
		    }
	    
}
	//methode qui permet en fonction, de son pseudo de se connecter en fonction de role également
	private void connexion() throws IOException {
		
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Entrez votre pseudo : ");
	    String pseudo = sc.nextLine();
	    Membre profil = chercherPseudo(pseudo); //Renvoie un type membre correspondant au pseudo dans la liste des utilisateurs (profils), sinon renvoi null
	    while(profil==null && !pseudo.equalsIgnoreCase("admin")) {
	    	
	    	utilisateur=null;
		    System.out.println("Ce pseudo n'existe pas, avez vous un compte ?"); 
		   	String reponse=sc.nextLine();
		   	if(reponse.equalsIgnoreCase("non")) { 
		   		
		   		utilisateur = new Invite(); //Si tu as voulu te connecte mais que tu n'as pas de compte, tu es connecté en tant qu'invite
		   		System.out.println("Bonjour, vous etes connecte en tant qu'invite");
	            System.out.println("En tant qu'invite, choisissez ce que vous voulez faire");
       	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
   	    	 	System.out.println(" - Vous deconnecter : 2");
   	    	 	String choix;
   	   			Scanner sc2 = new Scanner(System.in);
   	     		System.out.println("Votre choix : ");
   	   	 		choix = sc2.nextLine();
   	   	 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2")) {
   	    	 			do {
   	    	 			System.out.println("Veuillez choisi un chiffre entre 1 et 2 par rapport à ce que vous voulez faire");
		   	    	 	System.out.println("En tant qu'invite voila ce que pouvez faire, choisissez ce que vous voulez faire");
	   	    	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
	   	    	 		System.out.println(" - Vous deconnecter : 2");
	   	    	 		choix = sc2.nextLine();
   	    	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2"));
   	    	 	}
		   	   	 switch (choix) {
		   	    	 case "1":
		   	    		 System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
		   	    		afficherEvaluationPourUnJeu(); 
		   	    		 System.out.println(" Souhaitez vous faire autre chose?");
		   	    		 System.out.println(" - Si oui, repondez oui ");
		   	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnte ");
		   	    		 Scanner sc4=new Scanner(System.in);
		   	    		 System.out.println("Votre choix : ");
		   	    		 String rep = sc4.nextLine();
		   	    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
		   	    		System.out.println("Veuillez repondre par oui ou par non");
		   	    		String reponseEncore = sc.nextLine();
		   	  	    	rep = reponseEncore;
		   	  	    }
		   	    		 if(rep.equalsIgnoreCase("non")) {
		   	    			 deconnecter();
		   	    		 }
		   	    		 else if (rep.equalsIgnoreCase("oui")) {
		   	    			 choixInvite();
		   	    		 }
		   	    		 break; 
		   	    	 case "2":
		   	    		System.out.println("Vous avez choisi de vous deconnecter");
		   	    		deconnecter();
		   	    		 break;
		   	    	 	}
		            return;
		    	}
		    	else if(reponse.equalsIgnoreCase("oui")) {
		    		System.out.println("Entrez votre pseudo : ");
		    		pseudo=sc.nextLine();
		    		profil=chercherPseudo(pseudo);
		    		if(pseudo==null) {
		    			System.out.println("Ce pseudo n'existe pas une nouvelle fois, vous allez être redigere vers le menu");
		    			choix();
		    		}
		    		//System.out.println(profil);
		    	}
		    
	    }
		    if(pseudo.equalsIgnoreCase("admin")) { //Si ton pseudo est admin tu es directement connecte en tant qu'admin
		        utilisateur = new Administrateur(pseudo);
		        System.out.println("Bonjour, vous etes connecte en tant qu'administrateur");
		        utilisateur.InitialisationJetons();
		        utilisateur.getJetons();
		    	System.out.println("Vous avez "+utilisateur.getJetons()+" jetons");
		    	System.out.println("En tant qu'administrateur, choisissez ce que vous voulez faire");
		    	System.out.println(" - Afficher les informations sur un membre : 1 ");
		 		System.out.println(" - Ajouter un jeu : 2 ");
		 		System.out.println(" - Evaluer un jeu : 3 ");
		 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
		 		System.out.println(" - Signaler une evaluation problematique : 5");
		 		System.out.println(" - Ecrire un test pour un jeu : 6");
		 		System.out.println(" - Desinscrire un joueur : 7");
		 		System.out.println(" - Promouvoir un joueur : 8"); 
		 		System.out.println(" - Bloquer un joueur : 9");
		 		System.out.println(" - Supprimer l'evaluation d'un joueur : 10");
		 		System.out.println(" - Vous deconnecter : 11");
		 		System.out.println(" - Se desinscrire : 12 ");
		 		System.out.println(" - Afficher les informations sur jeu : 13");
		 		System.out.println(" - Debloquer un joueur : 14");
		 		String choix;
		 		Scanner sc2 = new Scanner(System.in);
		 		System.out.println("Votre choix : ");
		 		choix = sc2.nextLine();
		 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")&& !choix.equalsIgnoreCase("10")&& !choix.equalsIgnoreCase("11")&& !choix.equalsIgnoreCase("12")&& !choix.equalsIgnoreCase("13")&& !choix.equalsIgnoreCase("14")) {
		 			do {
		 			System.out.println("Veuillez choisi un chiffre entre 1 et 12 par rapport à ce que vous voulez faire");
			    	 	System.out.println("En tant qu'administrateur voila ce que pouvez faire, choisissez ce que vous voulez faire");
			    	 	System.out.println(" - Afficher les informations sur un membre : 1 ");
			 		System.out.println(" - Ajouter un jeu : 2 ");
			 		System.out.println(" - Evaluer un jeu : 3 ");
			 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
			 		System.out.println(" - Signaler une evaluation problematique : 5");
			 		System.out.println(" - Ecrire un test pour un jeu : 6");
			 		System.out.println(" - Desinscrire un joueur : 7");
			 		System.out.println(" - Promouvoir un joueur : 8"); 
			 		System.out.println(" - Bloquer un joueur : 9");
			 		System.out.println(" - Supprimer l'evaluation d'un joueur : 10");
			 		System.out.println(" - Vous deconnecter : 11");
			 		System.out.println(" - Se desinscrire : 12 ");
			 		System.out.println(" - Afficher les informations sur jeu : 13");
			 		System.out.println(" - Debloquer un joueur : 14");
		   	 		choix = sc2.nextLine();
		 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")&& !choix.equalsIgnoreCase("10")&& !choix.equalsIgnoreCase("11")&& !choix.equalsIgnoreCase("12") && !choix.equalsIgnoreCase("13")&& !choix.equalsIgnoreCase("14"));
		 		}
		   	 switch (choix) {
		   	 case "1":
		   		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
		   	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
		   	     String pseudo2 = sc2.nextLine();
		   	     afficherInfos(pseudo2);
		   	     System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc4=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep = sc4.nextLine();
				 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc4.nextLine();
					 rep = reponseEncore;
			    }
				 if(rep.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   	     break;
		   	 case "2":
		   	     System.out.println("Vous avez choisi d'ajouter un jeu ");
		   	     ajouterJeu();
		   	  System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc5=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep2 = sc5.nextLine();
				 while (!rep2.equalsIgnoreCase("non")&&!rep2.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc5.nextLine();
					 rep2 = reponseEncore;
			    }
				 if(rep2.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep2.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   	      break;
		   	 case "12":
		   	      System.out.println("Vous avez choisi de vous desinscrire ");
		   	      desinscrireSoisMemeAdmin(); //Appel de cette methode qui empeche le profil "admin" de se désinscrire 
		   	      choix();
		   	      return;
		   	 case "3":
		   	      System.out.println("Vous avez choisi d'evaluer un jeu ");
		   	      evaluer();
		   	      System.out.println(" Souhaitez vous faire autre chose?");
		   	      System.out.println(" - Si oui, repondez oui ");
		   	      System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   	      Scanner sc123=new Scanner(System.in);
		   	      System.out.println("Votre choix : ");
		   	      String rep3 = sc.nextLine();
		   	      while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
		   	    	  System.out.println("Veuillez repondre par oui ou par non");
		   	    	  String reponseEncore = sc.nextLine();
		   	    	  rep3 = reponseEncore;
			    }
				 if(rep3.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep3.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   	      break;
		   	 case "4":
		   		System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
		    	   
		    	   boolean a=afficherEvaluationPourUnJeu
		    			   ();
		    	   if(a==false) {
		    		   System.out.println("Vous ne posseder pas de jeu");
		    		   
		    	   }
		    	   else if(a==true) {
		    		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
		    		   Scanner sc41= new Scanner(System.in);
		        	   String repA = sc41.nextLine();
		        	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
		        	   		System.out.println("Veuillez repondre par oui ou par non");
		        	   		String newRep = sc41.nextLine();
		        	   		repA=newRep;
		        	   }
		        	   if(repA.equalsIgnoreCase("oui")) { 
		        		   if(utilisateur.getJeux().isEmpty()) {
		        			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
		        		   }
		        		   else {
		        			   evaluerEvaluation();
		        		   }
		        	   }
		    	   }
		    	   
		   		 System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc1=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep4 = sc1.nextLine();
				 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc1.nextLine();
					 rep4 = reponseEncore;
			    }
				 if(rep4.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep4.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		   	 case "5":
		   		 System.out.println("Vous avez choisi de signaler une evaluation problematique");
		   		 System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc51=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep5 = sc51.nextLine();
				 while (!rep5.equalsIgnoreCase("non")&&!rep5.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc51.nextLine();
					 rep5 = reponseEncore;
			    }
				 if(rep5.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep5.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		   	 case "6" :
		   		 System.out.println("Vous avez choisi d'ecrire un test pour un jeu");
		   		 Tester();
		   		 System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc6=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep6 = sc6.nextLine();
				 while (!rep6.equalsIgnoreCase("non")&&!rep6.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc6.nextLine();
					 rep6 = reponseEncore;
			    }
				 if(rep6.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep6.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		   	 case "11":
		   		System.out.println("Vous avez choisi de vous deconnecter");
		   		deconnecter();
		   		return;
		   	case "7":
		   		System.out.println("Vous avez choisi de desinscrire un joueur");
		   		desinscrire();
		   		System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc7=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep7 = sc7.nextLine();
				 while (!rep7.equalsIgnoreCase("non")&&!rep7.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc7.nextLine();
					 rep7 = reponseEncore;
			    }
				 if(rep7.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep7.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		   	case "8":
		   		System.out.println("Vous avez choisi de promouvoir un joueur");
		   		promotion();
		   		System.out.println(" Souhaitez vous faire autre chose?");
				System.out.println(" - Si oui, repondez oui ");
				System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				Scanner sc8=new Scanner(System.in);
				System.out.println("Votre choix : ");
				String rep8 = sc8.nextLine();
				 while (!rep8.equalsIgnoreCase("non")&&!rep8.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc8.nextLine();
					 rep8 = reponseEncore;
			    }
				 if(rep8.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep8.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		   	case "9":
		   		System.out.println("Vous avez choisi de bloquer un joueur");
		   		bloquerJoueur();
		   		System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc9=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep9 = sc9.nextLine();
				 while (!rep9.equalsIgnoreCase("non")&&!rep9.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc9.nextLine();
					 rep = reponseEncore;
			    }
				 if(rep9.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep9.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		   	case "10":
		   		System.out.println("Vous avez choisi de suprimer l'evaluation d'un joueur");
		   		supprimerEval();
		   		System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc10=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep10 = sc10.nextLine();
				 while (!rep10.equalsIgnoreCase("non")&&!rep10.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
				 	String reponseEncore = sc10.nextLine();
				 	rep = reponseEncore;
			    }
				 if(rep10.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep10.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
		   		 break;
		    case "13":
				 System.out.println("Vous avez choisi de voire les informations d'un jeu");
				 System.out.println("De quel jeu voulez vous avoir les informations ?");
				 Scanner sc45=new Scanner(System.in);
				 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
				 String rep13 = sc45.nextLine();
				 System.out.println("Sur quelle plateforme ?");
				 String support = sc45.nextLine();
				 afficherInfosJeuxPourAdministrateur(rep13,support);
				 System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 Scanner sc42=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep42 = sc42.nextLine();
				 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc42.nextLine();
					 rep42 = reponseEncore;
			    }
				 if(rep42.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep42.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
			     break;
		    case "14":
		    	 System.out.println(" Vous avez choisi de debloquer un joueur");
		    	 debloquerJoueur();
				 System.out.println(" Souhaitez vous faire autre chose?");
				 System.out.println(" - Si oui, repondez oui ");
				 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				 //Scanner sc42=new Scanner(System.in);
				 System.out.println("Votre choix : ");
				 String rep45 = sc.nextLine();
				 while (!rep45.equalsIgnoreCase("non")&&!rep45.equalsIgnoreCase("oui")) {
					 System.out.println("Veuillez repondre par oui ou par non");
					 String reponseEncore = sc.nextLine();
					 rep45 = reponseEncore;
			    }
				 if(rep45.equalsIgnoreCase("non")) {
					 deconnecter();
				 }
				 else if (rep45.equalsIgnoreCase("oui")) {
					 choixAdministrateur();
				 }
			     break;
		   	 	}
			return;
		    }
		    if(profil.getRole().equalsIgnoreCase("administrateur")) { //Pseudo correspondant à un administrateur
		    	 utilisateur=profil;
		    	 System.out.println("Bonjour " + pseudo + ", vous etes connecte en tant qu'administrateur");
		    	 utilisateur.getJetons();
		    	 System.out.println("Vous avez "+utilisateur.getJetons()+" jetons");
		    	 System.out.println("En tant qu'administrateur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		    	 System.out.println(" - Afficher les informations sur un membre : 1 ");
			 		System.out.println(" - Ajouter un jeu : 2 ");
			 		System.out.println(" - Evaluer un jeu : 3 ");
			 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
			 		System.out.println(" - Signaler une evaluation problematique : 5");
			 		System.out.println(" - Ecrire un test pour un jeu : 6");
			 		System.out.println(" - Desinscrire un joueur : 7");
			 		System.out.println(" - Promouvoir un joueur : 8"); 
			 		System.out.println(" - Bloquer un joueur : 9");
			 		System.out.println(" - Supprimer l'evaluation d'un joueur : 10");
			 		System.out.println(" - Vous deconnecter : 11");
			 		System.out.println(" - Se desinscrire : 12 ");
			 		System.out.println(" - Afficher les informations sur jeu : 13");
			 		System.out.println(" - Debloquer un joueur : 14");
			 		String choix;
			 		Scanner sc2 = new Scanner(System.in);
			 		System.out.println("Votre choix : ");
			 		choix = sc2.nextLine();
			 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")&& !choix.equalsIgnoreCase("10")&& !choix.equalsIgnoreCase("11")&& !choix.equalsIgnoreCase("12")&& !choix.equalsIgnoreCase("13")&& !choix.equalsIgnoreCase("14")) {
			 			do {
			 			System.out.println("Veuillez choisi un chiffre entre 1 et 12 par rapport à ce que vous voulez faire");
				    	 	System.out.println("En tant qu'administrateur voila ce que pouvez faire, choisissez ce que vous voulez faire");
				    	 	System.out.println(" - Afficher les informations sur un membre : 1 ");
				 		System.out.println(" - Ajouter un jeu : 2 ");
				 		System.out.println(" - Evaluer un jeu : 3 ");
				 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
				 		System.out.println(" - Signaler une evaluation problematique : 5");
				 		System.out.println(" - Ecrire un test pour un jeu : 6");
				 		System.out.println(" - Desinscrire un joueur : 7");
				 		System.out.println(" - Promouvoir un joueur : 8"); 
				 		System.out.println(" - Bloquer un joueur : 9");
				 		System.out.println(" - Supprimer l'evaluation d'un joueur : 10");
				 		System.out.println(" - Vous deconnecter : 11");
				 		System.out.println(" - Se desinscrire : 12 ");
				 		System.out.println(" - Afficher les informations sur jeu : 13");
				 		System.out.println(" - Debloquer un joueur : 14");
			   	 		choix = sc2.nextLine();
			 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")&& !choix.equalsIgnoreCase("10")&& !choix.equalsIgnoreCase("11")&& !choix.equalsIgnoreCase("12") && !choix.equalsIgnoreCase("13")&& !choix.equalsIgnoreCase("14"));
			 		}
			   	 switch (choix) {
			   	 case "1":
			   		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
			   	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
			   	     String pseudo2 = sc2.nextLine();
			   	     afficherInfos(pseudo2);
			   	     System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc4=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep = sc4.nextLine();
					 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc4.nextLine();
						 rep = reponseEncore;
				    }
					 if(rep.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   	     break;
			   	 case "2":
			   	     System.out.println("Vous avez choisi d'ajouter un jeu ");
			   	     ajouterJeu();
			   	  System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc5=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep2 = sc5.nextLine();
					 while (!rep2.equalsIgnoreCase("non")&&!rep2.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc5.nextLine();
						 rep2 = reponseEncore;
				    }
					 if(rep2.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep2.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   	      break;
			   	 case "12":
			   	      System.out.println("Vous avez choisi de vous desinscrire ");
			   	      desinscrireSoisMemeAdmin();
			   	      choix();
			   	      return;
			   	 case "3":
			   	      System.out.println("Vous avez choisi d'evaluer un jeu ");
			   	      evaluer();
			   	      System.out.println(" Souhaitez vous faire autre chose?");
			   	      System.out.println(" - Si oui, repondez oui ");
			   	      System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
			   	      Scanner sc123=new Scanner(System.in);
			   	      System.out.println("Votre choix : ");
			   	      String rep3 = sc.nextLine();
			   	      while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
			   	    	  System.out.println("Veuillez repondre par oui ou par non");
			   	    	  String reponseEncore = sc.nextLine();
			   	    	  rep3 = reponseEncore;
				    }
					 if(rep3.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep3.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   	      break;
			   	 case "4":
			   		System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
			    	   
			    	   boolean a=afficherEvaluationPourUnJeu
			    			   ();
			    	   if(a==false) {
			    		   System.out.println("Vous ne posseder pas de jeu");
			    		   
			    	   }
			    	   else if(a==true) {
			    		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
			    		   Scanner sc41= new Scanner(System.in);
			        	   String repA = sc41.nextLine();
			        	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
			        	   		System.out.println("Veuillez repondre par oui ou par non");
			        	   		String newRep = sc41.nextLine();
			        	   		repA=newRep;
			        	   }
			        	   if(repA.equalsIgnoreCase("oui")) { 
			        		   if(utilisateur.getJeux().isEmpty()) {
			        			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
			        		   }
			        		   else {
			        			   evaluerEvaluation();
			        		   }
			        	   }
			    	   }
			    	   
			   		 System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc1=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep4 = sc1.nextLine();
					 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc1.nextLine();
						 rep4 = reponseEncore;
				    }
					 if(rep4.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep4.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			   	 case "5":
			   		 System.out.println("Vous avez choisi de signaler une evaluation problematique");
			   		 System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc51=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep5 = sc51.nextLine();
					 while (!rep5.equalsIgnoreCase("non")&&!rep5.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc51.nextLine();
						 rep5 = reponseEncore;
				    }
					 if(rep5.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep5.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			   	 case "6" :
			   		 System.out.println("Vous avez choisi d'ecrire un test pour un jeu");
			   		 Tester();
			   		 System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc6=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep6 = sc6.nextLine();
					 while (!rep6.equalsIgnoreCase("non")&&!rep6.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc6.nextLine();
						 rep6 = reponseEncore;
				    }
					 if(rep6.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep6.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			   	 case "11":
			   		System.out.println("Vous avez choisi de vous deconnecter");
			   		deconnecter();
			   		return;
			   	case "7":
			   		System.out.println("Vous avez choisi de desinscrire un joueur");
			   		desinscrire();
			   		System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc7=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep7 = sc7.nextLine();
					 while (!rep7.equalsIgnoreCase("non")&&!rep7.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc7.nextLine();
						 rep7 = reponseEncore;
				    }
					 if(rep7.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep7.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			   	case "8":
			   		System.out.println("Vous avez choisi de promouvoir un joueur");
			   		promotion();
			   		System.out.println(" Souhaitez vous faire autre chose?");
					System.out.println(" - Si oui, repondez oui ");
					System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					Scanner sc8=new Scanner(System.in);
					System.out.println("Votre choix : ");
					String rep8 = sc8.nextLine();
					 while (!rep8.equalsIgnoreCase("non")&&!rep8.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc8.nextLine();
						 rep8 = reponseEncore;
				    }
					 if(rep8.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep8.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			   	case "9":
			   		System.out.println("Vous avez choisi de bloquer un joueur");
			   		bloquerJoueur();
			   		System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc9=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep9 = sc9.nextLine();
					 while (!rep9.equalsIgnoreCase("non")&&!rep9.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc9.nextLine();
						 rep = reponseEncore;
				    }
					 if(rep9.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep9.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			   	case "10":
			   		System.out.println("Vous avez choisi de suprimer l'evaluation d'un joueur");
			   		supprimerEval();
			   		System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc10=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep10 = sc10.nextLine();
					 while (!rep10.equalsIgnoreCase("non")&&!rep10.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
					 	String reponseEncore = sc10.nextLine();
					 	rep = reponseEncore;
				    }
					 if(rep10.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep10.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
			   		 break;
			    case "13":
					 System.out.println("Vous avez choisi de voire les informations d'un jeu");
					 System.out.println("De quel jeu voulez vous avoir les informations ?");
					 Scanner sc45=new Scanner(System.in);
					 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
					 String rep13 = sc45.nextLine();
					 System.out.println("Sur quelle plateforme ?");
					 String support = sc45.nextLine();
					 afficherInfosJeuxPourAdministrateur(rep13,support);
					 System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 Scanner sc42=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep42 = sc42.nextLine();
					 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc42.nextLine();
						 rep42 = reponseEncore;
				    }
					 if(rep42.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep42.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
				     break;
			    case "14":
			    	 System.out.println(" Vous avez choisi de debloquer un joueur");
			    	 debloquerJoueur();
					 System.out.println(" Souhaitez vous faire autre chose?");
					 System.out.println(" - Si oui, repondez oui ");
					 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
					 //Scanner sc42=new Scanner(System.in);
					 System.out.println("Votre choix : ");
					 String rep45 = sc.nextLine();
					 while (!rep45.equalsIgnoreCase("non")&&!rep45.equalsIgnoreCase("oui")) {
						 System.out.println("Veuillez repondre par oui ou par non");
						 String reponseEncore = sc.nextLine();
						 rep45 = reponseEncore;
				    }
					 if(rep45.equalsIgnoreCase("non")) {
						 deconnecter();
					 }
					 else if (rep45.equalsIgnoreCase("oui")) {
						 choixAdministrateur();
					 }
				     break;
			   	 	}
		 	return;
		    }
		    else {
		        if (profil != null) { 
		            if (profil.getRole().equalsIgnoreCase("testeur")) { //Pseudo correspondant à un testeur
		                utilisateur = profil;
		                System.out.println("Bonjour " + pseudo + ", vous etes connecte en tant que testeur");
		                utilisateur.getJetons();
		   	    	 	System.out.println("Vous avez "+utilisateur.getJetons()+" jetons");
		   	    	 	System.out.println("En tant que testeur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		   	    	 System.out.println(" - Afficher les informations sur un membre : 1 ");
		   		 	System.out.println(" - Ajouter un jeu : 2 ");
		   		 	System.out.println(" - Evaluer un jeu : 3 ");
		   		 	System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
		   		 	System.out.println(" - Signaler une evaluation problematique : 5");
		   		 	System.out.println(" - Ecrire un test pour un jeu : 6");
		   		 	System.out.println(" - Vous deconnecter : 7");
		   		 	System.out.println(" - Se desinscrire : 8 ");
		   		 	System.out.println(" - Afficher les informations sur jeu : 9");
		   		 	String choix;
		   		 	Scanner sc2 = new Scanner(System.in);
		   		 	System.out.println("Votre choix : ");
		   		 	choix = sc2.nextLine();
		   		 	if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9")) {
		   		 		do {
		   	   	 			System.out.println("Veuillez choisi un chiffre entre 1 et 8 par rapport à ce que vous voulez faire");
		   	   	    	 	System.out.println("En tant que testeur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		   	   	    	 	System.out.println(" - Afficher les informations sur un membre : 1 ");
		   		    	 	System.out.println(" - Ajouter un jeu : 2 ");
		   		    	 	System.out.println(" - Evaluer un jeu : 3 ");
		   		    	 	System.out.println(" - Voire les evaluations d'un jeu : 4 ");	   	    	 		
		   		    	 	System.out.println(" - Signaler une evaluation problematique : 5");
		   		    		System.out.println(" - Ecrire un test pour un jeu : 6");
		   		   	 		System.out.println(" - Vous deconnecter : 7");
		   		   	 		System.out.println(" - Se desinscrire : 8 ");
		   		   	 		System.out.println(" - Afficher les informations sur jeu : 9");
		   		   	 		choix = sc2.nextLine();
		   		 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8")&& !choix.equalsIgnoreCase("9"));
		   		 	}
		   		    	 switch (choix) {
		   		    	 case "1":
		   		    		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
		   		    	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
		   		    	     String pseudo2 = sc2.nextLine();
		   		    	     afficherInfos(pseudo2);
		   		    	     System.out.println(" Souhaitez vous faire autre chose?");
		   		    		 System.out.println(" - Si oui, repondez oui ");
		   		    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   		    		 Scanner sc4=new Scanner(System.in);
		   		    		 System.out.println("Votre choix : ");
		   		    		 String rep = sc4.nextLine();
		   		    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
		   		    			 System.out.println("Veuillez repondre par oui ou par non");
		   		    			 String reponseEncore = sc4.nextLine();
		   		    			 rep = reponseEncore;
		   		    	    }
		   		    		 if(rep.equalsIgnoreCase("non")) {
		   		    			 deconnecter();
		   		    		 }
		   		    		 else if (rep.equalsIgnoreCase("oui")) {
		   		    			 choixTesteur();
		   		    		 }
		   		    	     break;
		   		    	 case "2":
		   		    	     System.out.println("Vous avez choisi d'ajouter un jeu ");
		   		    	     ajouterJeu();
		   		    	     System.out.println(" Souhaitez vous faire autre chose?");
		   		    		 System.out.println(" - Si oui, repondez oui ");
		   		    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   		    		 Scanner sc33=new Scanner(System.in);
		   		    		 System.out.println("Votre choix : ");
		   		    		 String rep3 = sc33.nextLine();
		   		    		 while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
		   		    			 System.out.println("Veuillez repondre par oui ou par non");
		   		    			 String reponseEncore = sc33.nextLine();
		   		    			 rep3 = reponseEncore;
		   		    	    }
		   		    		 if(rep3.equalsIgnoreCase("non")) {
		   		    			 deconnecter();
		   		    		 }
		   		    		 else if (rep3.equalsIgnoreCase("oui")) {
		   		    			 choixTesteur();
		   		    		 }
		   		    	     break;
		   		    	 case "8":
		   		    	      System.out.println("Vous avez choisi de vous desinscrire ");
		   		    	      desinscrire();
		   		    	      return;
		   		    	 case "3":
		   		    	      System.out.println("Vous avez choisi d'evaluer un jeu ");
		   		    	      evaluer();
		   		    	      System.out.println(" Souhaitez vous faire autre chose?");
		   			    		 System.out.println(" - Si oui, repondez oui ");
		   			    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   			    		 Scanner sc34=new Scanner(System.in);
		   			    		 System.out.println("Votre choix : ");
		   			    		 String rep4 = sc34.nextLine();
		   			    		 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
		   			    			 System.out.println("Veuillez repondre par oui ou par non");
		   			    			 String reponseEncore = sc34.nextLine();
		   			    			 rep4 = reponseEncore;
		   			    	    }
		   			    		 if(rep4.equalsIgnoreCase("non")) {
		   			    			 deconnecter();
		   			    		 }
		   			    		 else if (rep4.equalsIgnoreCase("oui")) {
		   			    			 choixTesteur();
		   			    		 }
		   		    	      break;
		   		    	 case "4":
		   		    		System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
		   		    	   
		   		    	   boolean a=afficherEvaluationPourUnJeu
		   		    			   ();
		   		    	   if(a==false) {
		   		    		   System.out.println("Vous ne posseder pas de jeu");
		   		    		   
		   		    	   }
		   		    	   else if(a==true) {
		   		    		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
		   		    		   Scanner sc41= new Scanner(System.in);
		   		        	   String repA = sc41.nextLine();
		   		        	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
		   		        	   		System.out.println("Veuillez repondre par oui ou par non");
		   		        	   		String newRep = sc41.nextLine();
		   		        	   		repA=newRep;
		   		        	   }
		   		        	   if(repA.equalsIgnoreCase("oui")) { 
		   		        		   if(utilisateur.getJeux().isEmpty()) {
		   		        			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
		   		        		   }
		   		        		   else {
		   		        			   evaluerEvaluation();
		   		        		   }
		   		        	   }
		   		    	   }
		   		    	   
		   		    		 System.out.println(" Souhaitez vous faire autre chose?");
		   		    		 System.out.println(" - Si oui, repondez oui ");
		   		    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   		    		 Scanner sc44=new Scanner(System.in);
		   		    		 System.out.println("Votre choix : ");
		   		    		 String rep44 = sc44.nextLine();
		   		    		 while (!rep44.equalsIgnoreCase("non")&&!rep44.equalsIgnoreCase("oui")) {
		   		    			 System.out.println("Veuillez repondre par oui ou par non");
		   		    			 String reponseEncore = sc44.nextLine();
		   		    			 rep44 = reponseEncore;
		   		    	    }
		   		    		 if(rep44.equalsIgnoreCase("non")) {
		   		    			 deconnecter();
		   		    		 }
		   		    		 else if (rep44.equalsIgnoreCase("oui")) {
		   		    			 choixTesteur();
		   		    		 }
		   		    		 break;
		   		    	 case "5":
		   		    		 System.out.println("Vous avez choisi de signaler une evaluation problematique");
		   		    		 System.out.println(" Souhaitez vous faire autre chose?");
		   		    		 System.out.println(" - Si oui, repondez oui ");
		   		    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   		    		 Scanner sc5=new Scanner(System.in);
		   		    		 System.out.println("Votre choix : ");
		   		    		 String rep5 = sc5.nextLine();
		   		    		 while (!rep5.equalsIgnoreCase("non")&&!rep5.equalsIgnoreCase("oui")) {
		   		    			 System.out.println("Veuillez repondre par oui ou par non");
		   		    			 String reponseEncore = sc5.nextLine();
		   		    			 rep5 = reponseEncore;
		   		    	    }
		   		    		 if(rep5.equalsIgnoreCase("non")) {
		   		    			 deconnecter();
		   		    		 }
		   		    		 else if (rep5.equalsIgnoreCase("oui")) {
		   		    			 choixTesteur();
		   		    		 }
		   		    		 break;
		   		    	 case "6" :
		   		    		 System.out.println("Vous avez choisi d'ecrire un test pour un jeu");
		   		    		 Tester();
		   		    		 System.out.println(" Souhaitez vous faire autre chose?");
		   		    		 System.out.println(" - Si oui, repondez oui ");
		   		    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   		    		 Scanner sc6=new Scanner(System.in);
		   		    		 System.out.println("Votre choix : ");
		   		    		 String rep6 = sc6.nextLine();
		   		    		 while (!rep6.equalsIgnoreCase("non")&&!rep6.equalsIgnoreCase("oui")) {
		   		    			 System.out.println("Veuillez repondre par oui ou par non");
		   		    			 String reponseEncore = sc6.nextLine();
		   		    			 rep6 = reponseEncore;
		   		    	    }
		   		    		 if(rep6.equalsIgnoreCase("non")) {
		   		    			 deconnecter();
		   		    		 }
		   		    		 else if (rep6.equalsIgnoreCase("oui")) {
		   		    			 choixTesteur();
		   		    		 }
		   		    		 break;
		   		    	 case "7":
		   		    		System.out.println("Vous avez choisi de vous deconnecter");
		   		    		deconnecter();
		   		    		choix();
		   		    		return;
		   		    	 case "9":
		   		    		 System.out.println("Vous avez choisi de voire les informations d'un jeu");
		   		    		 System.out.println("De quel jeu voulez vous avoir les informations ?");
		   		    		 Scanner sc45=new Scanner(System.in);
		   		    		 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
		   		    		 String rep9 = sc45.nextLine();
		   		    		 System.out.println("Sur quelle plateforme ?");
		   		    		 String support = sc45.nextLine();
		   		    		 afficherInfosJeuxPourTesteur(rep9,support);
		   		    		System.out.println(" Souhaitez vous faire autre chose?");
		   	    		 System.out.println(" - Si oui, repondez oui ");
		   	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		   	    		 Scanner sc42=new Scanner(System.in);
		   	    		 System.out.println("Votre choix : ");
		   	    		 String rep42 = sc42.nextLine();
		   	    		 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
		   	    			 System.out.println("Veuillez repondre par oui ou par non");
		   	    			 String reponseEncore = sc42.nextLine();
		   	    			 rep4 = reponseEncore;
		   	    	    }
		   	    		 if(rep42.equalsIgnoreCase("non")) {
		   	    			 deconnecter();
		   	    		 }
		   	    		 else if (rep42.equalsIgnoreCase("oui")) {
		   	    			 choixTesteur();
		   	    		 }
		   	    	     break;
		   	    	 	

		   		    	 	}
		            }
		            else if(profil.getRole().equalsIgnoreCase("joueur")) { //Pseudo correspondant à un joueur
		                utilisateur = profil;
		                System.out.println("Bonjour " + pseudo + ", vous etes connecte en tant que joueur");
		                utilisateur.getJetons();
		                fichierMembre.sauvegarderMembres(profils);	
		                fichierMembre.chargerMembres();		                
		   	    	 	System.out.println("Vous avez "+utilisateur.getJetons()+" jetons");				   	    	 	
		   	    	 	System.out.println("En tant que joueur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		   	    	 System.out.println(" - Afficher les informations sur un membre : 1 ");
		 	 		System.out.println(" - Ajouter un jeu : 2 ");		   	    	 		
		 	 		System.out.println(" - Evaluer un jeu : 3 ");
		 	 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");
		 	 		System.out.println(" - Vous deconnecter : 5");
		 	 		System.out.println(" - Se desinscrire : 6 ");
		 	 		System.out.println(" - Afficher les informations sur jeu : 7");
		 	 		System.out.println(" - Placer un/des jetons pour un jeu afin de demander un test : 8");
		 	 		String choix;
		 	 		Scanner sc2 = new Scanner(System.in);
		 	 		System.out.println("Votre choix : ");
		 	 		choix = sc2.nextLine();
		 	 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7") && !choix.equalsIgnoreCase("8")) {
		 	 			do {
		 	 				System.out.println("Veuillez choisi un chiffre entre 1 et 6 par rapport à ce que vous voulez faire");
		 	 				System.out.println("En tant que joueur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		 	 				System.out.println(" - Afficher les informations sur un membre : 1 ");
		 	 				System.out.println(" - Ajouter un jeu : 2 ");		   	    	 		
		 	 				System.out.println(" - Evaluer un jeu : 3 ");
		 	 				System.out.println(" - Voire les evaluations d'un jeu : 4 ");
		 	 				System.out.println(" - Vous deconnecter : 5");
		 	 				System.out.println(" - Se desinscrire : 6 ");
		 	 				System.out.println(" - Afficher les informations sur jeu : 7");
		 	 				System.out.println(" - Placer un/des jetons pour un jeu afin de demander un test : 8");
		 	 				choix = sc2.nextLine();
		 	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8"));
		 	 		}
		 	    	 switch (choix) {
		 	    	case "1":
		     		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
		     	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
		     	     String pseudo2 = sc2.nextLine();
		     	     afficherInfos(pseudo2);
		     	     System.out.println(" Souhaitez vous faire autre chose?");
		     		 System.out.println(" - Si oui, repondez oui ");
		     		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		     		 Scanner sc4=new Scanner(System.in);
		     		 System.out.println("Votre choix : ");
		     		 String rep = sc4.nextLine();
		     		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
		     			 System.out.println("Veuillez repondre par oui ou par non");
		     			 String reponseEncore = sc4.nextLine();
		     			 rep = reponseEncore;
		     	    }
		     		 if(rep.equalsIgnoreCase("non")) {
		     			 deconnecter();
		     		 }
		     		 else if (rep.equalsIgnoreCase("oui")) {
		     			 choixJoueur();
		     		 }
		     	     break;
		     	 case "2":
		     	     System.out.println("Vous avez choisi d'ajouter un jeu ");
		     	     ajouterJeu();
		     	     System.out.println(" Souhaitez vous faire autre chose?");
		     		 System.out.println(" - Si oui, repondez oui ");
		     		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		     		 Scanner sc3=new Scanner(System.in);
		     		 System.out.println("Votre choix : ");
		     		 String rep2 = sc3.nextLine();
		     		 while (!rep2.equalsIgnoreCase("non")&&!rep2.equalsIgnoreCase("oui")) {
		     			 System.out.println("Veuillez repondre par oui ou par non");
		     			 String reponseEncore = sc3.nextLine();
		     			 rep2 = reponseEncore;
		     	    }
		     		 if(rep2.equalsIgnoreCase("non")) {
		     			 deconnecter();
		     		 }
		     		 else if (rep2.equalsIgnoreCase("oui")) {
		     			 choixJoueur();
		     		 }
		     	     break;
		     	 case "6":
		     	      System.out.println("Vous avez choisi de vous desinscrire ");
		     	      desinscrire();
		     	      choix();
		 			  return;
		     	 case "3":
		     	      System.out.println("Vous avez choisi d'evaluer un jeu ");
		     	      evaluer();
		     	      System.out.println(" Souhaitez vous faire autre chose?");
		 	    		 System.out.println(" - Si oui, repondez oui ");
		 	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 	    		 Scanner sc33=new Scanner(System.in);
		 	    		 System.out.println("Votre choix : ");
		 	    		 String rep3 = sc33.nextLine();
		 	    		 while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
		 	    			 System.out.println("Veuillez repondre par oui ou par non");
		 	    			 String reponseEncore = sc33.nextLine();
		 	    			 rep3 = reponseEncore;
		 	    	    }
		 	    		 if(rep3.equalsIgnoreCase("non")) {
		 	    			 deconnecter();
		 	    		 }
		 	    		 else if (rep3.equalsIgnoreCase("oui")) {
		 	    			 choixJoueur();
		 	    		 }
		 	    	     break;
		     	 case "4":
		     		boolean a=afficherEvaluationPourUnJeu
	    			   ();
	    	   if(a==false) {
	    		   System.out.println("Vous ne posseder pas de jeu ou la liste des evaluations pour ce jeu est vide");
	    		   
	    	   }
	    	   else if(a==true) {
	    		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
	    		   Scanner sc41= new Scanner(System.in);
	        	   String repA = sc41.nextLine();
	        	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
	        	   		System.out.println("Veuillez repondre par oui ou par non");
	        	   		String newRep = sc41.nextLine();
	        	   		repA=newRep;
	        	   }
	        	   if(repA.equalsIgnoreCase("oui")) { 
	        		   if(utilisateur.getJeux().isEmpty()) {
	        			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
	        		   }
	        		   else {
	        			   evaluerEvaluation();
	        		   }
	        	   }
	    	   }
		     	   System.out.println(" Souhaitez vous faire autre chose?");
		     		 System.out.println(" - Si oui, repondez oui ");
		     		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		     		 Scanner sc44=new Scanner(System.in);
		     		 System.out.println("Votre choix : ");
		     		 String rep4 = sc44.nextLine();
		     		 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
		     			 System.out.println("Veuillez repondre par oui ou par non");
		     			 String reponseEncore = sc44.nextLine();
		     			 rep4 = reponseEncore;
		     	    }
		     		 if(rep4.equalsIgnoreCase("non")) {
		     			 deconnecter();
		     		 }
		     		 else if (rep4.equalsIgnoreCase("oui")) {
		     			 choixJoueur();
		     		 }
		     	     break;
		     	 case "5" :
		     		 System.out.println("Vous avez choisi de vous deconnecter");
		     		 deconnecter();
		     		 return;
		     	 case "7" :
		     		 System.out.println("Vous avez choisi d'afficher les informations sur un jeu");
		     		 System.out.println("De quel jeu voulez vous avoir les informations ?");
		     		 Scanner sc45=new Scanner(System.in);
		     		 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
		     		 String rep6 = sc45.nextLine();
		     		 System.out.println("Sur quelle plateforme ?");
		     		 String support = sc45.nextLine();
		     		 afficherInfosJeuxPourJoueur(rep6,support);
		     		 System.out.println(" Souhaitez vous faire autre chose?");
		     		 System.out.println(" - Si oui, repondez oui ");
		     		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		     		 Scanner sc42=new Scanner(System.in);
		     		 System.out.println("Votre choix : ");
		     		 String rep42 = sc42.nextLine();
		     		 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
		     			 System.out.println("Veuillez repondre par oui ou par non");
		     			 String reponseEncore = sc42.nextLine();
		     			 rep4 = reponseEncore;
		     	    }
		     		 if(rep42.equalsIgnoreCase("non")) {
		     			 deconnecter();
		     		 }
		     		 else if (rep42.equalsIgnoreCase("oui")) {
		     			 choixJoueur();
		     		 }
		     	     break;
		     	 case "8":
		     		 System.out.println("Vous avez choisi de placer un ou des jetons sur un jeu ");
		     		 placerJetons();
		     		 System.out.println(" Souhaitez vous faire autre chose?");
		     		 System.out.println(" - Si oui, repondez oui ");
		     		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		     		 Scanner sc40=new Scanner(System.in);
		     		 System.out.println("Votre choix : ");
		     		 String rep45 = sc40.nextLine();
		     		 while (!rep45.equalsIgnoreCase("non")&&!rep45.equalsIgnoreCase("oui")) {
		     			 System.out.println("Veuillez repondre par oui ou par non");
		     			 String reponseEncore = sc40.nextLine();
		     			 rep45 = reponseEncore;
		     	    }
		     		 if(rep45.equalsIgnoreCase("non")) {
		     			 deconnecter();
		     		 }
		     		 else if (rep45.equalsIgnoreCase("oui")) {
		     			 choixJoueur();
		     		 }
		     	     break;
		     	 	}
		            }
		            else if(profil.getRole().equalsIgnoreCase("invite")) { //Cas ou le joueur est bloque (et est donc un invite)
		            	System.out.println("Bonjour "+pseudo+", vous etes connecte en tant qu'invite (bloque)");
				    	 utilisateur = new Invite();
				    	 System.out.println("En tant qu'invite, choisissez ce que vous voulez faire");
			    	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
			    	 		System.out.println(" - Vous deconnecter : 2");
			    	 		String choix;
			    	 		Scanner sc3 = new Scanner(System.in);
			    	 		System.out.println("Votre choix : ");
			    	 		choix = sc3.nextLine();
			    	 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2")) {
			    	 			do {
			    	 			System.out.println("Veuillez choisi un chiffre entre 1 et 2 par rapport à ce que vous voulez faire");
				   	    	 	System.out.println("En tant qu'invite voila ce que pouvez faire, choisissez ce que vous voulez faire");
			   	    	 		System.out.println(" - Voire les evaluations d'un jeu : 1 ");
			   	    	 		System.out.println(" - Vous deconnecter : 2");
			   	    	 		choix = sc3.nextLine();
			    	 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2"));
			    	 		}
				   	    	 switch (choix) {
				   	    	 case "1":
				   	    		 System.out.println(" Vous avez choisi de voire les evaluations d'un jeu");
				   	    		 afficherEvaluationPourUnJeu(); 
				   	    		 System.out.println(" Souhaitez vous faire autre chose?");
				   	    		 System.out.println(" - Si oui, repondez oui ");
				   	    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
				   	    		 Scanner sc4=new Scanner(System.in);
				   	    		 System.out.println("Votre choix : ");
				   	    		 String rep = sc3.nextLine();
				   	    		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
				   	    		System.out.println("Veuillez repondre par oui ou par non");
				   	    		String reponseEncore = sc.nextLine();
				   	  	    	rep = reponseEncore;
				   	  	    }
				   	    		 if(rep.equalsIgnoreCase("non")) {
				   	    			 deconnecter();
				   	    		 }
				   	    		 else if (rep.equalsIgnoreCase("oui")) {
				   	    			 choixInvite();
				   	    		 }
				   	    		 break;
				   	    	 case "2":
				   	    		System.out.println("Vous avez choisi de vous deconnecter");
				   	    		deconnecter();
				   	    		break;
				   	    	 	}
				            return;
				    
		            }
		        }
	    }
	}
	
	//methode qui permet d'inscrire un utilisateur et de l'ajouter dans la liste des membres (profils)
	private void inscrire() throws IOException {
		Scanner sc=new Scanner(System.in);
		System.out.println("Entrez le pseudo que vous souhaitez avoir : ");
		String pseudo = sc.nextLine(); 
		while(chercherPseudo(pseudo)!= null) { //Verifie si le pseudo est deja present dans la liste des membres, auquel cas il doit en choisir un autre
			System.out.println("Le pseudo "+pseudo+ " est deja utilise par un autre utilisateur, veuillez en choisir un autre : "); 
			pseudo = sc.nextLine();
		}
		utilisateur = new Joueur(pseudo); //On devient forcement un joueur en s'inscrivant
		utilisateur.InitialisationJetons(); //InitialisationJetons() met le nombre de jetons d'un joueur à 3
		profils.add(utilisateur); //le joueur est ajoute à la liste des membres
		fichierMembre.sauvegarderMembres(profils); //permet d'ecrire dans le fichier texte et donc de sauvegarder le fait qu'il y a un nouveau membre
		System.out.println("Inscription reussie ! Bienvenue sur notre plateforme "+pseudo);
		System.out.println("Vous avez "+utilisateur.getJetons()+" jetons");
		System.out.println("En tant que joueur voila ce que pouvez faire, choisissez ce que vous voulez faire");
		System.out.println(" - Afficher les informations sur un membre : 1 ");
 		System.out.println(" - Ajouter un jeu : 2 ");		   	    	 		
 		System.out.println(" - Evaluer un jeu : 3 ");
 		System.out.println(" - Voire les evaluations d'un jeu : 4 ");
 		System.out.println(" - Vous deconnecter : 5");
 		System.out.println(" - Se desinscrire : 6 ");
 		System.out.println(" - Afficher les informations sur jeu : 7");
 		System.out.println(" - Placer un/des jetons pour un jeu afin de demander un test : 8");
 		String choix;
 		Scanner sc2 = new Scanner(System.in);
 		System.out.println("Votre choix : ");
 		choix = sc2.nextLine();
 		if (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7") && !choix.equalsIgnoreCase("8")) {
 			do {
 				System.out.println("Veuillez choisi un chiffre entre 1 et 6 par rapport à ce que vous voulez faire");
 				System.out.println("En tant que joueur voila ce que pouvez faire, choisissez ce que vous voulez faire");
 				System.out.println(" - Afficher les informations sur un membre : 1 ");
 				System.out.println(" - Ajouter un jeu : 2 ");		   	    	 		
 				System.out.println(" - Evaluer un jeu : 3 ");
 				System.out.println(" - Voire les evaluations d'un jeu : 4 ");
 				System.out.println(" - Vous deconnecter : 5");
 				System.out.println(" - Se desinscrire : 6 ");
 				System.out.println(" - Afficher les informations sur jeu : 7");
 				System.out.println(" - Placer un/des jetons pour un jeu afin de demander un test : 8");
 				choix = sc2.nextLine();
 			} while (!choix.equalsIgnoreCase("1") && !choix.equalsIgnoreCase("2") && !choix.equalsIgnoreCase("3") && !choix.equalsIgnoreCase("4") && !choix.equalsIgnoreCase("5")&& !choix.equalsIgnoreCase("6")&& !choix.equalsIgnoreCase("7")&& !choix.equalsIgnoreCase("8"));
 		}
    	 switch (choix) {
    	case "1":
		 System.out.println("Vous avez choisi d'afficher les informations sur un membre");
	     System.out.println("Entrez le pseudo du joueur dont vous voulez voire les informations");
	     String pseudo2 = sc2.nextLine();
	     afficherInfos(pseudo2);
	     System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc4=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep = sc4.nextLine();
		 while (!rep.equalsIgnoreCase("non")&&!rep.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc4.nextLine();
			 rep = reponseEncore;
	    }
		 if(rep.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep.equalsIgnoreCase("oui")) {
			 choixJoueur();
		 }
	     break;
	 case "2":
	     System.out.println("Vous avez choisi d'ajouter un jeu ");
	     ajouterJeu();
	     System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc3=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep2 = sc3.nextLine();
		 while (!rep2.equalsIgnoreCase("non")&&!rep2.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc3.nextLine();
			 rep2 = reponseEncore;
	    }
		 if(rep2.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep2.equalsIgnoreCase("oui")) {
			 choixJoueur();
		 }
	     break;
	 case "6":
	      System.out.println("Vous avez choisi de vous desinscrire ");
	      desinscrire();
	      choix();
		  return;
	 case "3":
	      System.out.println("Vous avez choisi d'evaluer un jeu ");
	      evaluer();
	      System.out.println(" Souhaitez vous faire autre chose?");
    		 System.out.println(" - Si oui, repondez oui ");
    		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
    		 Scanner sc33=new Scanner(System.in);
    		 System.out.println("Votre choix : ");
    		 String rep3 = sc33.nextLine();
    		 while (!rep3.equalsIgnoreCase("non")&&!rep3.equalsIgnoreCase("oui")) {
    			 System.out.println("Veuillez repondre par oui ou par non");
    			 String reponseEncore = sc33.nextLine();
    			 rep3 = reponseEncore;
    	    }
    		 if(rep3.equalsIgnoreCase("non")) {
    			 deconnecter();
    		 }
    		 else if (rep3.equalsIgnoreCase("oui")) {
    			 choixJoueur();
    		 }
    	     break;
	 case "4":
		 System.out.println("Vous avez choisi de voire les evaluations d'un jeu");
  	   
  	   boolean a=afficherEvaluationPourUnJeu
  			   ();
  	   if(a==false) {
  		   System.out.println("Vous ne posseder pas de jeu");
  		   
  	   }
  	   else if(a==true) {
  		   System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur un des ses evaluations ? Veuillez repondre par oui ou par non");
  		   Scanner sc41= new Scanner(System.in);
      	   String repA = sc41.nextLine();
      	   while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
      	   		System.out.println("Veuillez repondre par oui ou par non");
      	   		String newRep = sc41.nextLine();
      	   		repA=newRep;
      	   }
      	   if(repA.equalsIgnoreCase("oui")) { 
      		   if(utilisateur.getJeux().isEmpty()) {
      			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
      		   }
      		   else {
      			   evaluerEvaluation();
      		   }
      	   }
  	   }
	   System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc44=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep4 = sc44.nextLine();
		 while (!rep4.equalsIgnoreCase("non")&&!rep4.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc44.nextLine();
			 rep4 = reponseEncore;
	    }
		 if(rep4.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep4.equalsIgnoreCase("oui")) {
			 choixJoueur();
		 }
	     break;
	 case "5" :
		 System.out.println("Vous avez choisi de vous deconnecter");
		 deconnecter();
		 return;
	 case "7" :
		 System.out.println("Vous avez choisi d'afficher les informations sur un jeu");
		 System.out.println("De quel jeu voulez vous avoir les informations ?");
		 Scanner sc45=new Scanner(System.in);
		 System.out.println("Jeu sur lequel vous voulez avoir des informations : ");
		 String rep6 = sc45.nextLine();
		 System.out.println("Sur quelle plateforme ?");
		 String support = sc45.nextLine();
		 afficherInfosJeuxPourJoueur(rep6,support);
		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc42=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep42 = sc42.nextLine();
		 while (!rep42.equalsIgnoreCase("non")&&!rep42.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc42.nextLine();
			 rep4 = reponseEncore;
	    }
		 if(rep42.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep42.equalsIgnoreCase("oui")) {
			 choixJoueur();
		 }
	     break;
	 case "8":
		 System.out.println("Vous avez choisi de placer un ou des jetons sur un jeu ");
		 placerJetons();
		 System.out.println(" Souhaitez vous faire autre chose?");
		 System.out.println(" - Si oui, repondez oui ");
		 System.out.println(" - Si non, repondez non, et vous allez être deconnecte ");
		 Scanner sc40=new Scanner(System.in);
		 System.out.println("Votre choix : ");
		 String rep45 = sc40.nextLine();
		 while (!rep45.equalsIgnoreCase("non")&&!rep45.equalsIgnoreCase("oui")) {
			 System.out.println("Veuillez repondre par oui ou par non");
			 String reponseEncore = sc40.nextLine();
			 rep45 = reponseEncore;
	    }
		 if(rep45.equalsIgnoreCase("non")) {
			 deconnecter();
		 }
		 else if (rep45.equalsIgnoreCase("oui")) {
			 choixJoueur();
		 }
	     break;
	 	}
	}

	//Methode qui permet de chercher un membre parmi la liste des membres (profils) à partir de son pseudo, et renvoi le membre s'il existe, null sinon
	public Membre chercherPseudo(String pseudo) {
	    for (Membre m : profils) {
	        if (m.getPseudo().equalsIgnoreCase(pseudo)) {
	            return m;
	        }
	    }
	    return null;
	}
	
	//Methode qui permet de chercher un joueur parmi la liste des membres (profils) à partir de son pseudo, et renvoi le joueur (membre) s'il existe, null sinon
	public Membre chercherJoueur(String pseudo) {
		for (Membre m: profils) {
			if (m.getPseudo().equalsIgnoreCase(pseudo)&& m.getRole().equalsIgnoreCase("joueur")) {
	            return m;
	        }
		}
		return null;
	}
	
	//Methode qui permet d'afficher tous les membres (leur pseudo) de la liste des membres (profils), ainsi que leur "role" (Joueur, Invite, testeur, administrateur)
	public void afficherMembres() {
	    System.out.println("Liste des membres inscrits :");
	    for (Membre m : profils) {
	        System.out.println("- " + m.getPseudo()+" : "+m.getRole());
	    }
	}
	
	//Methode qui permet d'afficher tous les joueurs (leur pseudo) de la liste des membres (profils) (et donc de la plateforme), ainsi le fait qu'ils soient joueurs
	public void afficherJoueurs() {
		System.out.println("Liste des joueurs inscrits :");
	    for (Membre m : profils) {
	    	if(m.getRole().equalsIgnoreCase("joueur"))
	        System.out.println("- " + m.getPseudo()+" : "+m.getRole());
	    }
	}
	
	//Methode qui permet à un administrateur de se désinscrire, sauf pour le compte admin qui ne peut pas se désinscrire
	public void desinscrireSoisMemeAdmin() throws IOException {
		if(utilisateur.getPseudo().equalsIgnoreCase("admin")) {
			System.out.println("Vous ne pouvez pas desinscrire le compte admin");
			return;
		}
		Membre membre;
		Scanner sc3= new Scanner(System.in);
		String reponse2;
		do {
			System.out.println(utilisateur.getPseudo()+", etes vous sur de vouloir vous desinscrire ? Repondez oui si oui, sinon non");
			reponse2=sc3.nextLine();
			if(reponse2.equalsIgnoreCase("oui")) {
				System.out.println("Vous avez ete desinscrit");
				membre = chercherPseudo(utilisateur.getPseudo());
				profils.remove(membre);
				fichierMembre.sauvegarderMembres(profils);
		        profils = fichierMembre.chargerMembres();
			}
		} while (!reponse2.equalsIgnoreCase("non")&&!reponse2.equalsIgnoreCase("oui"));
	}
	
	//Methode qui permet aux adminstrateurs de désinscrire un joueur, et aux autres membres de se désinscrire
	public void desinscrire() throws IOException {
		Membre membre;
		if(utilisateur.getRole().equals("administrateur")) { //Verifie si l'utilisateur (celui qui est actuellement connecté sur la plateforme) est un administrateur
			
			Scanner sc44= new Scanner(System.in);
			String choix;
			
			Scanner sc= new Scanner(System.in);
			String pseudo;
			do {
				System.out.println("Vous souhaitez desinscrire un joueur. Veuillez entrer son pseudo. Si vous ne voulez finalement pas, mettez non ");
				pseudo=sc.nextLine();
				membre = chercherPseudo(pseudo); //Renvoi le membre correspondant 
				if (membre==null) {
					System.out.println("Le pseudo que vous avez entre n'appartient a aucun utilisateur");
				}
				else if(!membre.getClass().equals(Joueur.class)) {
					System.out.println(pseudo.getClass()); //
					System.out.println("Le pseudo que vous avez entre n'appartient pas a un joueur, vous ne pouvez desinscrire que des joueurs");
				}
			} while (membre==null || !membre.getClass().equals(Joueur.class) ||pseudo.equalsIgnoreCase("non")); //Verifie si le membre est un joueur ou s'il existe
			if(pseudo.equalsIgnoreCase("non")) {
				System.out.println("Vous avez annuler la descrinscription du joueur "+pseudo);
				
			}
				
			Scanner sc2= new Scanner(System.in);
			String reponse;
			do {
				System.out.println("Etes-vous sur de desinscrire le joueur "+pseudo+" ? Repondez oui pour oui, sinon non");
				reponse=sc2.nextLine();
			} while(!reponse.equalsIgnoreCase("non")&&!reponse.equalsIgnoreCase("oui"));
			if(reponse.equalsIgnoreCase("oui")) {
				System.out.println("Le joueur "+pseudo+" a ete descrincrit");
				profils.remove(membre); //Le joueur est enleve de la liste des membres(profils)
				fichierMembre.sauvegarderMembres(profils);  //permet d'ecrire dans le fichier texte et donc de sauvegarder le fait que le joueur a ete supprime
		        profils = fichierMembre.chargerMembres(); //permet de remettre les donnes stockees dans le fichier membre, dans les differentes données (evaluatoins,membre,ect..).
		        return;
			}
			else if(reponse.equalsIgnoreCase("non")) {
				System.out.println("Vous avez annuler la descrinscription du joueur "+pseudo);
				return;
			}
		}
		else if (utilisateur.getClass().equals(Joueur.class) || utilisateur.getClass().equals(Testeur.class)) { //Verifie que l'utilisateur qui veut se désinscrire est bien un joueur ou un testeur
			Scanner sc3= new Scanner(System.in);
			String reponse2;
			do {
				System.out.println(utilisateur.getPseudo()+", etes vous sur de vouloir vous desinscrire ? Repondez oui si oui, sinon non");
				reponse2=sc3.nextLine();
				if(reponse2.equalsIgnoreCase("oui")) {
					System.out.println("Vous avez ete desinscrit");
					membre = chercherPseudo(utilisateur.getPseudo());
					profils.remove(membre);
					fichierMembre.sauvegarderMembres(profils);
			        profils = fichierMembre.chargerMembres();
			        return;
				}
				else if(reponse2.equalsIgnoreCase("non")) {
					System.out.println("Votre desinscription a ete annule "+utilisateur.getPseudo());
					return;
				}
			} while(!reponse2.equalsIgnoreCase("non")&&!reponse2.equalsIgnoreCase("oui"));
		}
	}
	
	//methode qui permet aux administrateurs de promouvoir un testeur en administrateur ou un joueur en testeur
	public void promotion() throws IOException {
		Membre membre;
		if(utilisateur.getRole().equalsIgnoreCase("administrateur")) { //verifie que l'utilisateur est un administrateur
			Scanner sc= new Scanner(System.in);
			String pseudo;
			do {
				System.out.println("Souhaitez vous promouvoir un joueur ou un testeur ? (Repondez joueur ou testeur)");
				String choix = sc.nextLine();
				if(choix.equalsIgnoreCase("testeur")) {
					System.out.println("Veuillez entrez pseudo du testeur que vous voulez promouvoir en tant qu'administrateur :");
				}
				else if(choix.equalsIgnoreCase("joueur")) {
				    System.out.println("Nous allons vous afficher la liste des joueurs, comment souhaitez-vous que l'on vous affiche cette liste ?");
				    System.out.println(" - Par tri du nombre d'evaluations realisees par le joueur (decroissant) : 1");
				    System.out.println(" - Par tri du nombre de likes totales de ces evaluations (decroissant) : 2");
				    System.out.println(" - Par tri du nombre d'evaluations d'abord (decroissant) puis ensuite par tri du nombre de likes totales de ces evaluations (decroissant) : 3");
				    System.out.println(" - Par tri du nombre de likes de ces evaluations d'abord (decroissant) puis ensuite par tri du nombre d'evaluations (decroissant) : 4");

				    String nombre = sc.nextLine();
				    while (!nombre.equalsIgnoreCase("1") && !nombre.equalsIgnoreCase("2") && !nombre.equalsIgnoreCase("3") && !nombre.equalsIgnoreCase("4")) {
				        System.out.println("Veuillez repondre par 1, 2, 3 ou 4");
				        nombre = sc.nextLine();
				    }

				    switch (nombre) {
				        case "1":
				            System.out.println("Très bien, voici la liste triee par le nombre d'evaluations realisees par les joueurs (decroissant)");
				            afficherMembresPlusGrandNombreEvaluations(); //methode qui affiche les membres (testeurs et joueurs) par nombre d'evaluations décroissant
				            break;
				        case "2":
				            System.out.println("Très bien, voici la liste triee par le nombre de likes totales des evaluations realisees par les joueurs (decroissant)");
				            afficherMembresPlusGrandNombreLikes(); //methode qui affiche les membres (testeurs et joueurs) par nombre d'evaluations positives de leurs évaluations décroissant
				            break;
				        case "3":
				            System.out.println("Très bien, voici la liste triee du nombre d'evaluations d'abord (decroissant) puis ensuite par tri du nombre de likes totales de ces evaluations (decroissant)");
				            afficherMembresPlusGrandNombreEvaluationsEtLikes(); //methode qui affiche les membres (testeurs et joueurs) par leur nombre décroissant d'evaluations réalisées puis ensuite de leur nombre  décroissant d'évaluations positives
				            break;
				        case "4":
				            System.out.println("Très bien, voici la liste triee du nombre de likes totales de ces evaluation d'abord (decroissant) puis ensuite par tri du nombre total d'evaluations (decroissant)");
				            afficherMembresPlusGrandNombreLikesEtEvaluations();//methode qui affiche les membres (testeurs et joueurs) par leur nombre décroissant d'evaluations positives  puis ensuite de leur nombre  décroissant d'évaluations réalisées 
				            break;
				    }

				    System.out.println("Veuillez donc entrer le pseudo du joueur ou du testeur que vous souhaitez promouvoir en tant que testeur ou en tant qu'administateur");
				}

				pseudo=sc.nextLine();
				membre = chercherPseudo(pseudo);
				if (membre==null) {
					System.out.println("Le pseudo que vous avez entre n'appartient a aucun utilisateur");
				}
				if(!membre.getClass().equals(Joueur.class) && !membre.getClass().equals(Testeur.class)) {
					System.out.println(pseudo.getClass());
					System.out.println("Vous ne pouvez promouvoir que des joueur ou des testeurs");
				}
			} while (membre==null || (!membre.getClass().equals(Joueur.class)) && !membre.getClass().equals(Testeur.class)); //On ne peut promouvoir que des joueurs et des testeurs et pas de membre nul, evidemment
			if(membre.getRole().equalsIgnoreCase("testeur")) { //Si le membre en question est un testeur
				
				//Stockage de toutes ses informations avant d'etre supprime puis recree en tant qu'administrateur
				ArrayList<Test> listeTests = utilisateur.getListeTest();
				HashMap<Evaluation,Jeu> listeEvalsJeux = utilisateur.getEvaluations();
				HashMap<Jeu,Integer> listeJeux = utilisateur.getJeux();
				int tempsDeJeuTotale = utilisateur.getTempsDeJeuTotale();
				int nombEvaluations= utilisateur.getNombEvaluations();
				int nombTests= utilisateur.getNombTests();
				int nombEvalPos = utilisateur.getNombEvalQueJaiLike();
				int nombEvalNeutres = utilisateur.getNombEvalNeutresQueJaiFaite();
				int nombEvalNeg = utilisateur.getNombEvalQueJaiDislike();
				int jetons = utilisateur.getJetons();
				HashMap<Jeu,Integer> jetonsPlaces = utilisateur.getJetonsPlaces();
				ArrayList<Evaluation> evals= utilisateur.getListeEval();
				ArrayList<EvalPositives> evalPos = utilisateur.getEvalPositives();
				ArrayList<EvalNeutres> evalNeut=utilisateur.getListeEvalNeutres();
				ArrayList<EvalNegatives> evalNeg=utilisateur.getListeEvalNegatives();
				profils.remove(membre); //Supprime le testeur de la liste des membres
			
				Administrateur admin = new Administrateur(pseudo);
				profils.add(admin); //Pour le remettre en tant qu'administrateur
				System.out.println("Le joueur "+pseudo+" a bien ete promu");
				
				//On remet les donnees pour ce membre
				admin.setListeEvals(evals);
				admin.setListeTest(listeTests);
				admin.setJeux(listeJeux);
				admin.setListeEvalJeux(listeEvalsJeux);
				admin.setTempsDeJeuTotale(tempsDeJeuTotale);
				admin.setEval(nombEvaluations);
				admin.setNombTests(nombTests);
				admin.setNombEvalQueJaiLike(nombEvalPos);
				admin.setNombEvalNeutresQueJaiFaite(nombEvalNeutres);
				admin.setNombEvalQueJaiDislike(nombEvalNeg);
				admin.SetJetons(jetons);
				admin.setListeEvalLike(evalPos);
				admin.setListeEvalNeutres(evalNeut);
				admin.setListeEvalDislike(evalNeg);
				admin.setJetonsPlaces(jetonsPlaces);
				fichierMembre.sauvegarderMembres(profils); //cela sauvegarde le fait que ce testeur soit devenu administrateur
				System.out.println("Vous avez promu "+pseudo+" en tant qu'administrateur");
				return;
			}
			else if(membre.getRole().equalsIgnoreCase("joueur")) {//Si le membre en question est un joueur pour le promouvoir en tant que testeur
				
				//Comme avant, on stocke les donnees avant de le supprimer
				HashMap<Jeu,Integer> jetonsPlaces = utilisateur.getJetonsPlaces();
				HashMap<Jeu,Integer> listeJeux = membre.getJeux();
				HashMap<Evaluation,Jeu> listeEvalsJeux = membre.getEvaluations();
				int tempsDeJeuTotale = membre.getTempsDeJeuTotale();
				int nombEvaluations=membre.getNombEvaluations();
				int nombTests= membre.getNombTests();
				int nombEvalPos = membre.getNombEvalQueJaiLike();
				int nombEvalNeutres = membre.getNombEvalNeutresQueJaiFaite();
				int nombEvalNeg = membre.getNombEvalQueJaiDislike();
				int jetons = membre.getJetons();
				ArrayList<Evaluation> evals= membre.getListeEval();
				ArrayList<EvalPositives> evalPos = membre.getEvalPositives();
				ArrayList<EvalNeutres> evalNeut=membre.getListeEvalNeutres();
				ArrayList<EvalNegatives> evalNeg=membre.getListeEvalNegatives();
				profils.remove(membre); //Joueur supprimer de la liste des membres					 
				
				Testeur testeur = new Testeur(pseudo);				
				
				profils.add(testeur);	//Pour etre remlis en tant que testeur			
				
				//nouveau testeur qui recupere ses donnees
				testeur.setListeEvals(evals);				
				testeur.setJeux(listeJeux);
				testeur.setListeEvalJeux(listeEvalsJeux);				
				testeur.setTempsDeJeuTotale(tempsDeJeuTotale);
				testeur.setEval(nombEvaluations);
				testeur.setNombTests(nombTests);
				testeur.setNombEvalQueJaiLike(nombEvalPos);
				testeur.setNombEvalNeutresQueJaiFaite(nombEvalNeutres);
				testeur.setNombEvalQueJaiDislike(nombEvalNeg);
				testeur.SetJetons(jetons);
				testeur.setListeEvalLike(evalPos);
				testeur.setListeEvalNeutres(evalNeut);
				testeur.setListeEvalDislike(evalNeg);
				testeur.setJetonsPlaces(jetonsPlaces);
				fichierMembre.sauvegarderMembres(profils); //cela sauvegarde le fait que ce joueur soit devenu testeur				
				System.out.println("Vous avez promu "+pseudo+" en tant que testeur");
				return;
			}
		}
	}
	
	//Methode qui permet d'afficher les infos d'un joueur, et qui prend son pseudo en parametre
	public void afficherInfos(String pseudo) {
		
		Membre membre = chercherPseudo(pseudo);
		if(utilisateur.getRole().equals("joueur")) { //Si l'utilisateur est un joueur il ne peut pas voir toutes les memes infos q'un testeur ou un administrateur
			
			if(membre==null) {
				
				System.out.println("Ce joueur n'existe pas");
			}
			else {
				
				System.out.println("Voici les informations sur "+pseudo+" : ");
				System.out.println("Type de profil : "+membre.getRole()); 
				System.out.println("Jeux possedes : " + membre.getJeux2()); //Liste des jeux d'un joueur
				System.out.println("Duree de jeu totale : " + membre.getTempsDeJeuTotale()); 
				System.out.println("Nombre d'evaluations : " + membre.getNombEvaluations());
				System.out.println("Nombre de tests : " + membre.getNombTests());
				
			}
		}
		else if(utilisateur.getRole().equals("testeur") || utilisateur.getRole().equals("administrateur")) { //L'utilisateur est un testeur ou un administrateur et a donc a toutes les infos
			
			System.out.println("Voici les informations sur "+pseudo);
			System.out.println("Type de profil : "+membre.getRole());
			System.out.println("Duree de jeu totale : " + membre.getTempsDeJeuTotale());
			System.out.println("Nombre d'evaluations : " + membre.getNombEvaluations());
			System.out.println("Nombre de tests : " + membre.getNombTests());
			System.out.println("Evaluations positives postees: "+membre.getNombEvalQueJaiLike());
			System.out.println("Evaluations positives de ses evaluations postees : "+membre.getNombEvalPositivesQuonMaFaite());
			System.out.println("Evaluations neutre postees : "+membre.getNombEvalNeutresQueJaiFaite());
			System.out.println("Evaluations neutres de ses evaluations postees : "+membre.getNombEvalNeutresQuonMaFaite());
			System.out.println("Evaluations negatives postees : "+membre.getNombEvalNegativesQuonMaFaite());
			System.out.println("Evaluations negatives de ses evaluations postees : "+membre.getNombEvalQueJaiDislike());
			System.out.println("Jeux possedes par ordre decroissant de duree de jeu : ");
			membre.afficherJeuxParDureeDecroissante(); // methode qui affiche les jeux par duree de temps de jeu decroissante
			
		}
	}
	
	//cette methode affiche les infos d'un jeu avec son nom et son support en parametre que peut voir un invite 
	public void afficherInfosJeuxPourInvite(String nomJeu,String support) throws IOException {
		Jeu jeu= new Jeu(nomJeu,support);
		System.out.println(jeu.afficherInformations()); //affiche les informations de base d'un jeu(notes, ventes, categorie, ...)
		afficherEvaluations(jeu); //affiche les évaluations d'un jeu en mettant les mieux nottes d'abord puis les plus anciennes 
		System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant qu'invite ont ete affichees");
	}
		
	//cette methode affiche les infos d'un jeu avec son nom et son support en parametre que peut voir un joueur 
	public void afficherInfosJeuxPourJoueur(String nomJeu,String support) throws IOException {
		Jeu jeu= new Jeu(nomJeu,support);
		System.out.println(jeu.afficherInformations()); //idem
		afficherEvaluations(jeu); //idem
		String test=afficherTestPourUnJeu(jeu); 
		System.out.println(test); //affiche les test pour un jeu passé parametre
		afficherEvaluationsPourUnJeu(jeu); //affiche toutes les evaluations pour un jeu, pour en evaluer une ou non
		System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur une des ses evaluations ? Veuillez repondre par oui ou par non");
   	 	Scanner sc41= new Scanner(System.in);
   	 	String repA = sc41.nextLine();
   	 	while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
   	   		System.out.println("Veuillez repondre par oui ou par non");
   	   		String newRep = sc41.nextLine();
   	   		repA=newRep;
   	 	}
   	 	if(repA.equalsIgnoreCase("non")) {
   	 		System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant que joueur ont ete affichees");
   	 		return;
   	 	}
   	 	else if(repA.equalsIgnoreCase("oui")) {
   		   if(utilisateur.getJeux().isEmpty()) { //L'utilisateur n'a pas de jeux
   			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
   			   System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant que joueur ont ete affichees");
   			   return;
   		   }
   		   else {
   			   evaluerEvaluation();
   			   return;
   		   }
   	 	}
   	   }
	
	//cette methode affiche les infos d'un jeu avec son nom et son support en parametre que peut voir un testeur
	public void afficherInfosJeuxPourTesteur(String nomJeu, String support) throws IOException {
		Jeu jeu= new Jeu(nomJeu,support);
		System.out.println(jeu.afficherInformations());
		ArrayList<Evaluation> evalsJeu = new ArrayList<Evaluation>();
		evalsJeu=afficherEvaluations(jeu);
		String test=afficherTestPourUnJeu(jeu);
		System.out.println(test);
		afficherEvaluationsPourUnJeu(jeu);
		System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur une des ses evaluations ? Veuillez repondre par oui ou par non");
   	 	Scanner sc41= new Scanner(System.in);
   	 	String repA = sc41.nextLine();
   	 	while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
   	   		System.out.println("Veuillez repondre par oui ou par non");
   	   		String newRep = sc41.nextLine();
   	   		repA=newRep;
   	 	}
   	 	if(repA.equalsIgnoreCase("non")) {
	 		System.out.println("Très bien, voici les prochaines informations sur le jeu");
	 	}
	 	else if(repA.equalsIgnoreCase("oui")) {
		   if(utilisateur.getJeux().isEmpty()) {
			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
	   	 		return;
		   }
		   else {
			   evaluerEvaluation();
			   return;
		   }
	 }
   	 afficherEvaluations(jeu);
   	 System.out.println("Trouvez vous une de ces evaluations juste au dessus problematiques ? Si oui repondez oui, sinon non");
   	 Scanner sc43= new Scanner(System.in);
	 String prob = sc43.nextLine();
	 while(!prob.equalsIgnoreCase("oui")&& !prob.equalsIgnoreCase("non")) {
		System.out.println("Veuillez repondre par oui ou par non");
	   	String newRep2 = sc43.nextLine();
	   	prob=newRep2;
	 }
	
	 if(prob.equalsIgnoreCase("oui")) {
		 System.out.println("Veuillez inscrire le titre de l'evaluation que vous trouvez problematique : ");
		 String evalProb= sc43.nextLine();
		 Evaluation eval = chercherEvaluationParTitre(evalProb,evalsJeu); //cherche une Evaluation parmi une liste d'évaluations et avec son titre qui permet de la trouver 
		 while(eval==null) {
			 System.out.println("Cette evaluation n'existe pas");
			 System.out.println("Voulez vous toujours marque une evaluation comme problematique (oui pour oui et non pour non)? Pour rappel, voici les evaluations pour ce jeu :");
			 afficherEvaluations(jeu);
			 String rep69=sc43.nextLine();
			 while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
		   	   		System.out.println("Veuillez repondre par oui ou par non");
		   	   		String newRep = sc41.nextLine();
		   	   		rep69=newRep;
		   	 	}
			 if(rep69.equalsIgnoreCase("oui")) {
				System.out.println("Veuillez inscrire le titre de l'evaluation que vous trouvez problematique : ");
				evalProb=sc43.nextLine();
			 }
				if(rep69.equalsIgnoreCase("non")) {
					System.out.println("D'accord, vous ne voulez plus marque une evaluation comme problematique");
					System.out.println("Voila toutes les informations sur ce jeu ont ete affichees");
					return;
				}	 
			 }
		 System.out.println("Etes vous sûr de vouloir marquer cette evaluation comme problematique?");
		 System.out.println("titre de l'evaluation : " + eval.getTitre());
		 System.out.println("contenu de l'evaluation : " + eval.getCommentaire());
		 String rep69=sc43.nextLine();
		 while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
			 
	   	   		System.out.println("Veuillez repondre par oui ou par non");
	   	   		String newRep = sc41.nextLine();
	   	   		rep69=newRep;
	   	   		
	   	 	}
		 if(rep69.equalsIgnoreCase("oui")) {
			 
			System.out.println("Cette evaluation a bien ete marquee comme problematique"); 
			eval.newSignalements(); //Chaque evaluation a un compteur du nombre de signalement, ici ce compteur prend un +1
			fichierMembre.sauvegarderMembres(profils);// Sauvegarde le fait que cette evaluation prend un signalement
			
		 }
		 if(rep69.equalsIgnoreCase("non")) {
			 System.out.println("Annulation : cette evaluation n'a pas ete marquee comme problematique");
		 }
		 }
	 System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant que testeur ont ete affichees"+"\n");
	 return;
	 }
 
	//cette methode affiche les infos d'un jeu avec son nom et son support en parametre que peut voir un administrateur
	public void afficherInfosJeuxPourAdministrateur(String nomJeu, String support) throws IOException {
		Jeu jeu= new Jeu(nomJeu,support);
		System.out.println(jeu.afficherInformations());
		ArrayList<Evaluation> evalsJeu = new ArrayList<Evaluation>();
		evalsJeu=afficherEvaluations(jeu);
		String afficherTests = afficherTestPourUnJeu(jeu);
		System.out.println(afficherTests);
		afficherEvaluationsPourUnJeu(jeu);
		System.out.println("Souhaitez vous mettre un like, un avis neutre ou un avis negatif sur une des ses evaluations ? Veuillez repondre par oui ou par non");
   	 	Scanner sc41= new Scanner(System.in);
   	 	String repA = sc41.nextLine();
   	 	while(!repA.equalsIgnoreCase("oui")&&!repA.equalsIgnoreCase("non")) {
   	   		System.out.println("Veuillez repondre par oui ou par non");
   	   		String newRep = sc41.nextLine();
   	   		repA=newRep;
   	 	}
   	 	if(repA.equalsIgnoreCase("non")) {
	 		System.out.println("Très bien, voici les prochaines informations sur le jeu");
	 	}
	 	else if(repA.equalsIgnoreCase("oui")) {
		   if(utilisateur.getJeux().isEmpty()) {
			   System.out.println("Desole mais vous ne possedez pas de jeu, vous ne pouvez donc pas evaluer l'evaluation d'un jeu que vous n'avez pas");
	   	 		return;
		   }
		   else {
			   evaluerEvaluation();
			   return;
		   }
	 }
   	 afficherEvaluations(jeu);
   	 System.out.println("Trouvez vous une de ces evaluations juste au dessus problematiques ou qui meriterait d'etre supprimees ? Si oui repondez oui, sinon non");
   	 Scanner sc43= new Scanner(System.in);
	 String prob = sc43.nextLine();
	 while(!prob.equalsIgnoreCase("oui")&& !prob.equalsIgnoreCase("non")) {
		System.out.println("Veuillez repondre par oui ou par non");
	   	String newRep2 = sc43.nextLine();
	   	prob=newRep2;
	 }
	 if(prob.equalsIgnoreCase("oui")) {
		 System.out.println("Voulez vous juste la marquer comme etant problematique ? Ou voulez-vous carrement la supprimer ?");
		 System.out.println("Tapez masquer si vous voulez la masquer, tapez supprimer si vous voulez la supprimer");
		 String choix = sc43.nextLine();
		 while(!choix.equalsIgnoreCase("supprimer")&& !choix.equalsIgnoreCase("masquer")) {//possibilite de supprimer une evaluation en tant qu'administrateur
				System.out.println("Veuillez repondre par supprimer ou par masquer");
			   	String newRep2 = sc43.nextLine();
			   	choix=newRep2;
			 }
		 if(choix.equalsIgnoreCase("masquer")) {
			 System.out.println("Veuillez inscrire le titre de l'evaluation que vous trouvez problematique : ");
			 String evalProb= sc43.nextLine();
			 Evaluation eval = chercherEvaluationParTitre(evalProb,evalsJeu);
			 while(eval==null) {
				 System.out.println("Cette evaluation n'existe pas");
				 System.out.println("Voulez vous toujours marque une evaluation comme problematique (oui pour oui et non pour non)? Pour rappel, voici les evaluations pour ce jeu :");
				 afficherEvaluations(jeu);
				 String rep69=sc43.nextLine();
				 while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
			   	   		System.out.println("Veuillez repondre par oui ou par non");
			   	   		String newRep = sc41.nextLine();
			   	   		rep69=newRep;
			   	 	}
				 if(rep69.equalsIgnoreCase("oui")) {
					System.out.println("Veuillez inscrire le titre de l'evaluation que vous trouvez problematique : ");
					evalProb=sc43.nextLine();
				 }
					if(rep69.equalsIgnoreCase("non")) {
						System.out.println("D'accord, vous ne voulez plus marque une evaluation comme problematique");
						System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant qu'administrateur ont ete affichees"+"\n");
						return;
					}	 
				 }
			 System.out.println("Etes vous sûr de vouloir marquer cette evaluation comme problematique?");
			 System.out.println("titre de l'evaluation : " + eval.getTitre());
			 System.out.println("contenu de l'evaluation : " + eval.getCommentaire());
			 String rep69=sc43.nextLine();
			 while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
		   	   		System.out.println("Veuillez repondre par oui ou par non");
		   	   		String newRep = sc41.nextLine();
		   	   		rep69=newRep;
		   	 	}
			 if(rep69.equalsIgnoreCase("oui")) {
					System.out.println("Cette evaluation a bien ete marquee comme problematique");
					eval.newSignalements();
					fichierMembre.sauvegarderMembres(profils);	
					return;
			 }
			 if(rep69.equalsIgnoreCase("non")) {
				 System.out.println("Annulation : cette evaluation n'a pas ete marquee comme problematique");
				 System.out.println("Preferez vous la supprimer ? (oui ou non)");
				 String rep12=sc43.nextLine();
				 while(!rep12.equalsIgnoreCase("oui")&&!rep12.equalsIgnoreCase("non"))
				 if(rep12.equalsIgnoreCase("oui")) {
					 choix="supprimer";
				 }
				 else if(rep12.equalsIgnoreCase("non")) {
					 System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant qu'administrateur ont ete affichees"+"\n");
					 return;
				 }
			 } 
		 }
		 if(choix.equalsIgnoreCase("supprimer")) {
			 System.out.println("Veuillez inscrire le titre de l'evaluation que vous voulez supprimer : ");
			 String evalSupp= sc43.nextLine();
			 Evaluation eval = chercherEvaluationParTitre(evalSupp,evalsJeu); //cherche une evaluation avec son titre parmi la liste d'évaluations de tous les membres
			 while(eval==null) {
				 System.out.println("Cette evaluation n'existe pas");
				 System.out.println("Voulez vous toujours supprimer une evaluation (oui pour oui et non pour non)? Pour rappel, voici les evaluations pour ce jeu :");
				 afficherEvaluations(jeu);
				 String rep69=sc43.nextLine();
				 while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
			   	   		System.out.println("Veuillez repondre par oui ou par non");
			   	   		String newRep = sc41.nextLine();
			   	   		rep69=newRep;
			   	 	}
				 if(rep69.equalsIgnoreCase("oui")) {
					System.out.println("Veuillez inscrire le titre de l'evaluation que vous voulez supprimer : ");
					evalSupp=sc43.nextLine();
					eval=chercherEvaluationParTitre(evalSupp,evalsJeu);
				 }
					if(rep69.equalsIgnoreCase("non")) {
						System.out.println("D'accord, vous ne voulez plus supprimer cette evaluation");
						System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant qu'administrateur ont ete affichees"+"\n");
						return;
					}	 
				 }
			 	System.out.println("Etes vous sûr de vouloir supprimer cette evaluation ?");
			 	System.out.println("titre de l'evaluation : " + eval.getTitre());
			 	System.out.println("contenu de l'evaluation : " + eval.getCommentaire());
			 	String rep69=sc43.nextLine();
			 	while(!rep69.equalsIgnoreCase("oui")&&!rep69.equalsIgnoreCase("non")) {
		   	   		System.out.println("Veuillez repondre par oui ou par non");
		   	   		String newRep = sc41.nextLine();
		   	   		rep69=newRep;
		   	 	}
			 	if(rep69.equalsIgnoreCase("oui")) {
					 System.out.println("Cette evaluation a bien ete supprimee");
					 verificationEval(eval.getTitre(),eval.getCommentaire(),eval);//permet de gerer les evaluations positives, neutre, negatives par rapport a cette suppression
					 eval.getAuteur().supprimerEval(eval);//permet d'enlever l'evaluation de la liste d'evaluations ecrite par l'auteur de l'évaluation
					 fichierMembre.sauvegarderMembres(profils);//permet de sauvergarder le fait d'avoir supprimer l'évaluation et ses conséquences
			 	}
			 	if(rep69.equalsIgnoreCase("non")) {
				 System.out.println("Annulation : cette evaluation n'a pas ete supprimee");
			 	} 
		 	}
		}
	 System.out.println("Voila, toutes les informations sur ce jeu que vous pouvez voir en tant qu'administrateur ont ete affichees"+"\n");
	 return;
	 }
	
	//methode qui permet de gerer les conséquences de la suppression de l'évaluation sur les evaluations positives, neutre, negatives
	public void verificationEval(String titreEval, String comEval, Evaluation evaluation) {
		for(Membre membre : profils) {
			for(EvalPositives eval : membre.getEvalPositives()) {
				if(eval.getEval().getTitre().equalsIgnoreCase(titreEval) && eval.getEval().getTitre().equalsIgnoreCase(comEval)) {
					membre.supprimerEvalPositive(evaluation); //methode qui permet de supprimer cette evaluation des evaluations likes par les membres et d'enlever 1 au nombre de leur evaluations likees
				}
			}
			
			for(EvalNegatives eval : membre.getListeEvalNegatives()) {
				if(eval.getEval().getTitre().equalsIgnoreCase(titreEval) && eval.getEval().getTitre().equalsIgnoreCase(comEval)) {
					membre.supprimerEvalNegative(evaluation);//methode qui permet de supprimer cette evaluation des evaluations negatives par les membres et d'enlever 1 au nombre de leur evaluations negatives
				}
			}
			
			for(EvalNeutres eval : membre.getListeEvalNeutres()) {
				if(eval.getEval().getTitre().equalsIgnoreCase(titreEval) && eval.getEval().getTitre().equalsIgnoreCase(comEval)) {
					membre.supprimerEvalNeutre(evaluation);//methode qui permet de supprimer cette evaluation des evaluations neutres par les membres et d'enlever 1 au nombre de leur evaluations neutres
				}
			}
		}
	}
	
	//methode qui permet à l'utilisateur d'ajouter un jeu
	public void ajouterJeu() throws IOException {
		Scanner sc1=new Scanner(System.in);
		System.out.println("Voulez vous chercher les jeux par prefixe ?"); //Aider pour pouvoir affiner sa recherche en prenant en compte un prefixe
		String reponse= sc1.nextLine();
		while (!reponse.equalsIgnoreCase("oui") && ! reponse.equalsIgnoreCase("non")) {
			System.out.println("Veuillez répondre par oui ou par non");
			reponse = sc1.nextLine();
		}
		if(reponse.equalsIgnoreCase("oui")) {
			System.out.println("Mettez le prefixe");
			String prefixe = sc1.nextLine();
			ArrayList<Jeu> jeux =rechercherJeuxParPrefixe(prefixe);
			afficherListeJeux(jeux); //affiche les jeux ayant le prefixe definit par l'utilisateur
		}
		else if(reponse.equalsIgnoreCase("non")) {
			System.out.println("Vous avez refusé de chercher les jeux par préfixe");
		}
		System.out.println("Quel jeu souhaitez vous ajouter ?");
		Scanner sc= new Scanner(System.in);
		String jeu=sc.nextLine();
		System.out.println("Sur quelle plateforme avez vous ce jeu ?");
		String plateforme=sc.nextLine();
	    Jeu jeuEntier = new Jeu(jeu, plateforme); //Cherche en fonction de son nom et de la plateforme souhaite par l'utilisateur
	    while(jeuEntier.getNomJeu().equalsIgnoreCase("null") || jeuEntier.getSupport().equalsIgnoreCase("null")) {
	    	System.out.println(jeuEntier.getNomJeu());
	    	if(jeuEntier.getNomJeu().equalsIgnoreCase("null")) { //verifie si le jeu existe ou pas
	    		
	    		System.out.println("Ce jeu n'existe pas");
	    		System.out.println("Voulez-vous toujours ajouter ce jeu à votre liste de jeux ?");
	    		String rep=sc.nextLine();
	    		while(!rep.equalsIgnoreCase("oui")&&!rep.equalsIgnoreCase("non")) {
	    			System.out.println("Veuillez repondre par oui ou par non");
	    			rep=sc.nextLine();
	    		}
	    		if(rep.equalsIgnoreCase("oui")) {
	    			System.out.println("Indiquez alors quel jeu souhaitez vous ajouter ?");
	    			jeu=sc.nextLine();
	    			System.out.println("Sur quelle plateforme avez vous ce jeu ?");
	    			plateforme=sc.nextLine();
	    		    jeuEntier = new Jeu(jeu, plateforme);
	    		}
	    		else if(rep.equalsIgnoreCase("non")) {
	    			System.out.println("Très bien, vous ne voulez plus ajouter de jeu ");
	    			return;
	    		}
	    	}
	    	
	    	else if(jeuEntier.getSupport().equalsIgnoreCase("null")) { //verifie si le jeu en question existe bel et bien mais si c'est la bonne plateforme
	    		System.out.println("Ce jeu existe mais pas sur cette plateforme");
	    		System.out.println("Voulez-vous toujours ajouter ce jeu à votre liste de jeux ?");
	    		String rep=sc.nextLine();
	    		while(!rep.equalsIgnoreCase("oui")&&!rep.equalsIgnoreCase("non")) {
	    			System.out.println("Veuillez repondre par oui ou par non");
	    			rep=sc.nextLine();
	    		}
	    		if(rep.equalsIgnoreCase("oui")) {
	    			System.out.println("Indiquez alors à nouveau sur quelle plateforme voulez vous ajoutez jeu ?");
	    			plateforme=sc.nextLine();
	    		    jeuEntier = new Jeu(jeu, plateforme);
	    		}
	    		else if(rep.equalsIgnoreCase("non")) {
	    			System.out.println("Très bien, vous ne voulez plus ajouter de jeu ");
	    			fichierMembre.sauvegarderMembres(profils);
	    			return;
	    		}
	    	}
	    }
	    
	    Jeu jeu2 = chercherJeu(utilisateur,jeu,plateforme); //verifie si l'utilisateur possède le jeu sur la meme plateforme avec le nom du jeu et la plateforme en parametre
	    while(jeu2!=null) {
	    	System.out.println("Vous possedez dejà le jeu sur cette plateforme");
	    	System.out.println("Voulez-vous toujours ajouter un nouveau jeu à votre liste de jeux ?");
    		String rep=sc.nextLine();
    		while(!rep.equalsIgnoreCase("oui")&&!rep.equalsIgnoreCase("non")) {
    			System.out.println("Veuillez repondre par oui ou par non");
    			rep=sc.nextLine();
    		}
    		if(rep.equalsIgnoreCase("oui")) {
    			ajouterJeu();
    		}
    		else if(rep.equalsIgnoreCase("non")) {
    			System.out.println("Très bien, vous ne voulez plus ajouter de jeu ");
    			return;
    		}
	    	
	    }
	    System.out.println("Combien de temps as-tu joue à ce jeu (en heures arrondis à l'heure)");
	    Scanner sc2= new Scanner(System.in);
	    String duree=sc2.nextLine();
	    if (!duree.matches("\\d+")) {
	        System.out.println("Erreur : le temps de jeu doit être un nombre entier.");
	        return;
	    }
		int dureeDeJeu = Integer.parseInt(duree);
		utilisateur.PlusTempsDeJeuTotale(dureeDeJeu); //additione le temps de jeu totale de l'utilisateur
		utilisateur.AjouterJeux(jeuEntier, dureeDeJeu);//stocke le jeu et la duree de jeu associee dans une HashMap
		fichierMembre.sauvegarderMembres(profils); //permet de sauvegarder toutes ces nouvelles informations 
		return;
	}
	
	//cherche si un membre possède un jeu, avec en paramètre, le membre en question, le nom du jeu et le support également
	public Jeu chercherJeu(Membre membre, String nomJeu,String support) {
	    HashMap<Jeu, Integer> jeux = membre.getJeux();
	    for (Jeu jeu : jeux.keySet()) {
	        if (jeu.getNomJeu().equalsIgnoreCase(nomJeu) && jeu.getSupport().equalsIgnoreCase(support)) {
	            return jeu;
	        }
	    }
	    return null;
	}
	
	//Methode qui permet d'évaluer un jeu	
	public void evaluer ( ) {
				System.out.println("Indiquez de quel jeu vous voulez faire l'evaluation");
				Scanner sc= new Scanner(System.in);
				String rep=sc.nextLine();
				System.out.println("Indiquez maintenant le support");
				String sup=sc.nextLine();
				Jeu jeu=chercherJeu(utilisateur,rep,sup); //methode juste au dessus pour avoir le jeu
				String temps=verifierTempsDeJeu(rep,sup); // verifie si l'utilisateur a assez de temps de temps de jeu pour pouvoir evaluer ce jeu (ici 2 heures minimum)
				if (jeu==null) {
					System.out.println("Desole, mais vous ne possedez pas ce jeu");
					return;
				}
				else if(temps.equalsIgnoreCase("null")) {
					System.out.println("Vous n'avez pas le temps de jeu necessaire pour evaluer ce jeu");
					System.out.println("Vous devez avoir jouer durant au minimum 2 heures pour pouvoir evaluer ce jeu \n");
					return;
				}
				else {
				System.out.println("Tres bien, veuillez donner une note entre 0 et 10 pour le jeu : "+jeu.getNomJeu());
				Scanner sc2= new Scanner(System.in);
				int note=Integer.parseInt(sc.nextLine());
				
				System.out.println("Tres bien, veuillez maintenant ecrire un titre pour votre commentaire sur ce meme jeu");			
				String titre=sc2.nextLine();
				System.out.println("Tres bien, veuillez maintenant ecrire un commentaire sur ce meme jeu");			
				String rep2=sc2.nextLine();
				ordre=ordre+1; //L'evaluation est plus recente que l'ancienne donc c'est la "ordre"-ième
				
				Evaluation eval = new Evaluation(note,titre,rep2,jeu,utilisateur,ordre,0); //creation de la nouvelle évaluation qui prend en parametre la note du jeu, le titre de l'évaluation, le contenu de l'évaluation, l'auteur, son ordre, et le nombre de signalements (ici 0 )
				eval.setOrdre(ordre); // ordre+1
				
				System.out.println(utilisateur.AjouterEval(eval,jeu)); //ajoute l'evaluation avec son jeu associé dans la Map de l'auteur de l'évaluation
				
				utilisateur.nouvelleEvaluation(); //Nombre d'évaluations écrites par l'auteur qui augmente de 1
				utilisateur.ajouterListeEval(eval); //Ajoute cette evaluation à la liste d'évaluation de l'auteur
				fichierMembre.sauvegarderMembres(profils); // sauvegarde toutes les données ajoutées juste au dessus 
				return;
				}
		
	}
	
	//methode qui verifie le temps de jeu pour pouvoir evaluer
	public String verifierTempsDeJeu(String nomJeu, String support) {
	    for (Map.Entry<Jeu, Integer> entry : utilisateur.getJeux().entrySet()) {
	        Jeu jeu = entry.getKey();
	        Integer tempsDeJeu = entry.getValue();

	        if (jeu.getNomJeu().equals(nomJeu) && jeu.getSupport().equals(support)) {
	            if (tempsDeJeu < 2) { //temps minimal de jeu pour pouvoir evaluer un jeu
	                System.out.println("Vous n'avez pas assez de temps pour evaluer le jeu " + nomJeu + " sur " + support + ".");
	                return "null";
	            }
	        }
	    }	 
	    return "Le jeu " + nomJeu + " sur " + support + " n'est pas associe à votre liste de jeux.";
	}

	//verifie que le testeur a un temps de jeu suffisant élevé pour pouvoir tester le jeu (ici 5 heures)
	public String verifierTempsDeJeuPourTesteur(String nomJeu, String support) {
	    for (Map.Entry<Jeu, Integer> entry : utilisateur.getJeux().entrySet()) {
	        Jeu jeu = entry.getKey();
	        Integer tempsDeJeu = entry.getValue();

	        if (jeu.getNomJeu().equals(nomJeu) && jeu.getSupport().equals(support)) {
	            if (tempsDeJeu < 5) {
	                System.out.println("Vous n'avez pas assez de temps pour evaluer le jeu " + nomJeu + " sur " + support + ".");
	                return "null";
	            }
	        }
	    }	 
	    return "Le jeu " + nomJeu + " sur " + support + " n'est pas associe à votre liste de jeux.";
	}

	
	//methode qui permet de liker, laisser un avis neutre ou disliker une évaluation
	public void evaluerEvaluation( ) {	
			System.out.println("Indiquez de quel jeu vous voulez faire l'evaluation de l'evaluation : ");
			Scanner sc= new Scanner(System.in);
			String rep=sc.nextLine();
			System.out.println("Sous quelle plateforme ?");
			String rep2=sc.nextLine();
			Jeu jeu;
			try {
				jeu = new Jeu(rep,rep2);
				if (jeu.getNomJeu().equalsIgnoreCase("null")) {
					System.out.println("Desole, mais ce jeu n'existe pas");
					return;
				}
				else {
					System.out.println("Choisissez parmi cette liste, mettez le titre de l'evaluation que vous souhaitez evaluer");
					ArrayList<Evaluation> evals=ajouterEvaluationsPourJeu(rep);
				    System.out.println(afficherEvaluationsPourJeu(evals,rep)); //permet d'afficher toutes les evaluations pour un jeu donné
				    System.out.println("Mettez ici le titre de l'evaluation que vous voulez evaluer");
					Scanner sc2= new Scanner(System.in);
					String choixEval=sc2.nextLine();
					Evaluation eval=chercherEvaluationParTitre(choixEval,evals);
					if(eval==null) {
						System.out.println("Il n'y a pas d'evaluation pour ce jeu correspondant a ce titre");
						return;
					}
					else {
						System.out.println("Voici l'evaluation : "+ eval.titre +" ,du jeu : "+ eval.getJeu().getNomJeu()  + " redige par le "+ eval.getAuteur().getRole()+ " suivant "+eval.getAuteur().getPseudo() + " : "+"\n");
						System.out.println("evaluation : " +eval.getCommentaire()+ " et note du jeu : "+eval.getNote()+"/10");
						System.out.println("Trouvez vous cette evaluation pertinente (tapez 1), neutre (tapez 2) ou pas interessante (tapez 3) ?");
						Scanner sc3= new Scanner(System.in);
						String choixTypeEval=sc3.nextLine();
						if(choixTypeEval.equalsIgnoreCase("1")) {
							eval.newEvalPos(); //Ajoute à une évaluation le nombre de likes qu'elle a
							EvalPositives evalPos= new EvalPositives(eval); //crée une nouvelle evaluation positive
							utilisateur.ajoutEvalLike(evalPos); // ajoute une evaluation positive à la liste des evaluations qu'un membre a liké
							utilisateur.newNombEvalQueJaiLike(); //ajoute le nombre de like de la part de celui qui vient de liker l'évaluation
							verificationNombreJetons(); //regarder si le nombre de likes l'évaluation de celui qui a posté est de 10, auquel cas il faut lui ajouter 1 jeton
							fichierMembre.sauvegarderMembres(profils); //permet de sauvegarder les données ajoutées au dessus
							return;
						}
						else if(choixTypeEval.equalsIgnoreCase("2")) {
							eval.newEvalNeutre();//Ajoute à une évaluation le nombre d'avis neutres qu'elle a
							EvalNeutres evalNeut= new EvalNeutres(eval);
							utilisateur.ajoutEvalNeutre(evalNeut);// ajoute une evaluation neutre à la liste des evaluations qu'un membre a jugé neutre
							utilisateur.newNombEvalQueJaifaiteNeutres();//ajoute le nombre d'evaluation neutre de la part de celui qui vient de mettre l'avis neutre l'évaluation
							
							fichierMembre.sauvegarderMembres(profils);
							return;
						}
						else if(choixTypeEval.equalsIgnoreCase("3")) {
							eval.newEvalNeg(); //ajoute à une évaluation le nombre de dislike qu'elle a
							EvalNegatives evalNeg= new EvalNegatives(eval);
							utilisateur.ajoutEvalDislikes(evalNeg);// ajoute une evaluation négative à la liste des evaluations qu'un membre a jugé negative
							utilisateur.newNombEvalQueJaiDislike();			 //ajoute le nombre de dislike de la part de celui qui vient de disliker l'évaluation
							fichierMembre.sauvegarderMembres(profils);
							return;
						}
					}
				
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	//methode qui permet de tester un jeu
	public void Tester() {
		System.out.println("Voici la liste des jeux tries par nombre de jetons (et donc de demande de test)");
		afficherJeuxParNombreJetons(utilisateur);
		System.out.println("Quel jeu voulez vous tester ?");
		Scanner sc= new Scanner(System.in);
		String nomJeu=sc.nextLine();
		System.out.println("Sur quel support ?");
		String support=sc.nextLine();
		Jeu jeu= chercherJeu(utilisateur,nomJeu,support);
		String verifTemps= verifierTempsDeJeuPourTesteur(nomJeu,support);
		if(jeu==null) {
			System.out.println("Vous ne possedez pas ce jeu");
			return;
		}
		else if(verifTemps==null) {
			System.out.println("Vous devez depasser les 5 heures de jeu pour pouvoir prodiguer ce test");
		}
		else {
			System.out.println("Indiquez la date à laquelle vous souhaitez realiser le test (mettez sous la forme : AAAA-MM-JJ"); //Disons qu'on peut choisir le temps
			String repDate= sc.nextLine();
			LocalDate date = LocalDate.parse(repDate);
			System.out.println("Quel est le titre de votre test ?");
			String titre = sc.nextLine();
			System.out.println("Et quel est votre commentaire ?");
			String commentaire = sc.nextLine();
			System.out.println("Donnez une note sur 10 pour l'opmisisation : ");
			int noteOpt = Integer.parseInt(sc.nextLine());
			System.out.println("Donnez une note sur 10 pour l'interface : ");
			int noteInter = Integer.parseInt(sc.nextLine());
			System.out.println("Donnez une note sur 10 pour le gameplay : ");
			int noteGameplay = Integer.parseInt(sc.nextLine());
			float noteGlobale = (noteOpt+noteInter+noteGameplay)*100/30;
			int noteFinale= (int)noteGlobale;
			System.out.println("Voici votre notre finale : "+noteFinale+"/100");
			Test test = new Test(titre,commentaire, noteInter, noteGameplay, noteOpt, date,noteFinale,utilisateur,jeu);
			utilisateur.ajouterListeTest(test); // cela ajoute le test dans la liste des tests de l'utilisateur
			utilisateur.nouveauTest();// cela incremente le nombre test effectué par l'utilisateur
			rendreJetons(jeu); // Cela permet de rendre les jetons qui etaient posés pour le test de ce jeu à leurs utilisateurs
			System.out.println("Bravo, suite à la publication de ce jeu, vous venez de gagner 5 jetons");
			fichierMembre.sauvegarderMembres(profils);
		}
	}
	
	//methode qui permet de re distributé les jetons posés sur un jeu donnée en paramètre
	public void rendreJetons(Jeu jeu) {
		for (Membre membre : profils) {
			System.out.println("Salut"+membre.getPseudo());
			HashMap<Jeu, Integer> jetons = membre.getJetonsPlaces();
			System.out.println(membre.getJetonsPlaces());
			for (HashMap.Entry<Jeu, Integer> entry : jetons.entrySet()) {
		        if((entry.getKey().getNomJeu().equalsIgnoreCase(jeu.getNomJeu()) && (entry.getKey().getSupport().equalsIgnoreCase(jeu.getSupport())))) {  	
		        	membre.SetJetons(entry.getValue()+membre.getJetons());
		        	membre.supprimerJeton(jeu.getNomJeu(),jeu.getSupport());		        			        	
		        }   
			}	
		}
		fichierMembre.sauvegarderMembres(profils);
	}
	
	//methode qui permet de chercher une evaluation grace à son titre dans une liste d'évaluations et qui renvoie l'évaluation
	public Evaluation chercherEvaluationParTitre(String titre, ArrayList<Evaluation> evaluations) {
	    for (Evaluation n  : evaluations) {
	        if (n.getTitre().equalsIgnoreCase(titre)) {
	            return n;
	        }
	    }
	    return null;
	}

	//methode qui ajoute  une evaluation dans une liste d'évaluation pour un jeu donné en paramètre grace à son nom
	public ArrayList<Evaluation> ajouterEvaluationsPourJeu(String nomJeu) {
	    System.out.println("Evaluations pour le jeu " + nomJeu + " :");
	    boolean evaluationsTrouvees = false;
	    ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
	    for (Membre membre : profils) {
	        ArrayList<Evaluation> evaluations2 = membre.getListeEval();
	        for (Evaluation n:  evaluations2) {
	            if (n.getNom().getNomJeu().equalsIgnoreCase(nomJeu)) {
	                evaluationsTrouvees = true;
	                evaluations.add(n);
	            }
	        }
	    }
	    if (!evaluationsTrouvees) {
	        System.out.println("Aucune evaluation trouvee pour le jeu " + nomJeu);
	    }
	    return evaluations;
	}

	//methode qui affiche les evaluation pour un jeu pour une liste d'evaluation passé en paramètre par rapport au nomb d'un jeu
	public String afficherEvaluationsPourJeu(ArrayList<Evaluation> evals, String nom) {
		StringBuilder sb = new StringBuilder();
        
        if (evals.isEmpty()) {
            sb.append("Aucune evaluation pour ce jeu.");
        } else {
            int i = 1;
            for (Evaluation n :evals) {
                sb.append("       Evaluation ").append(i).append(" :\n");
                sb.append("-Titre de l'evaluation : ").append(n.getTitre()).append("\n");
                sb.append("-Commentaire : ").append(n.getCommentaire()).append("\n");
                sb.append("-Publiee par le ").append(n.getAuteur().getRole()).append(" suivant : ").append(n.getAuteur().getPseudo()).append("\n");
                i++;
                n.getAuteur().ajouterMembre(n.getAuteur());
            }
        }
        
        return sb.toString();
    }

	//methode qui affiche tous les test pour un jeu donné en paramètre avec son nom
	public String afficherTestsPourJeu(ArrayList<Evaluation> evals, String nom) {
		StringBuilder sb = new StringBuilder();
        if (evals.isEmpty()) {
            sb.append("Aucune evaluation pour ce jeu.");
        } else {
            int i = 1;
            for (Evaluation n :evals) {
                sb.append("       Evaluation ").append(i).append(" :\n");
                sb.append("-Titre de l'evaluation : ").append(n.getTitre()).append("\n");
                sb.append("-Commentaire : ").append(n.getCommentaire()).append("\n");
                sb.append("-Publiee par le ").append(n.getAuteur().getRole()).append(" suivant : ").append(n.getAuteur().getPseudo()).append("\n");
                i++;
                n.getAuteur().ajouterMembre(n.getAuteur());
            }
        }
        
        return sb.toString();
    }
	
	//regarder si parmi les membres de la plateforme, si il y en a un qui a une évaluation qui a 10 likes, auquel cas il gagne 1 jeton
	public void verificationNombreJetons() {
		for(Membre membre : profils) {
			for(Evaluation eval : membre.getListeEval()) {
				if(eval.getEvalPos()==10) {
					membre.SetJetons(membre.getJetons()+1);
				}
			}
			}
		fichierMembre.sauvegarderMembres(profils);
		}

	//methode qui permet de placer un jeton pour demander le test d'un jeu
	public void placerJetons() throws IOException {
		System.out.println("Pour quel jeu voulez vous placer des jetons afin de demander un test");
		Scanner sc = new Scanner(System.in);
		String nomJeu = sc.nextLine();
		System.out.println("Sur quelle plateforme ?");
		String support = sc.nextLine();
		Jeu jeu= new Jeu(nomJeu,support);
		if (jeu.getNomJeu().equalsIgnoreCase("null")) {
			System.out.println("Desole, mais ce jeu n'existe pas");
			return;
		}
		System.out.println("Combien de jetons voulez vous poser pour le test du jeu "+jeu.getNomJeu()+" ? (Pour rappel, vous avez "+utilisateur.getJetons()+" jetons"); 
		int nombJetons= Integer.parseInt(sc.nextLine());
		while(nombJetons>utilisateur.getJetons()) { //si le nombre de jetons de l'utilisateur est inférieur au nombre de jetons qu'il souhaite poser
			if(nombJetons>utilisateur.getJetons()) {
				System.out.println("Desole, mais vous n'avez pas assez de jetons. Pour rappel, vous avez "+utilisateur.getJetons()+" jetons");
				System.out.println("Voulez vous toujours poser des jetons pour un test ? Repondez par oui ou par non");
				String rep=sc.nextLine();
				while(!rep.equalsIgnoreCase(rep)&& !rep.equalsIgnoreCase(rep)) {
					System.out.println("Veuillez repondre par oui ou par non");
					String newRep= sc.nextLine();
					rep=newRep;
				}
				if(rep.equalsIgnoreCase("non")) {
					System.out.println("Très bien, vous n'avez poser auncun jetons.");
					System.out.println("Vous avez "+utilisateur.getJetons()+" jetons");
					return;
				}
				else if (rep.equalsIgnoreCase("oui")) {
					System.out.println("Combien de jetons voulez vous poser pour le test du jeu "+jeu.getNomJeu()+" ?");
					nombJetons= Integer.parseInt(sc.nextLine());
				}
			}
		}
		utilisateur.ajoutJetons(jeu, nombJetons); // ajoute dans la map de l'utilisateur, le jeu et le nombre de jetons placés dans ce jeu
		System.out.println("Felicitations, vous venez de placer "+nombJetons+" pour le test du jeu "+jeu.getNomJeu());
		utilisateur.SetJetons(utilisateur.getJetons()-nombJetons); //reduit le nombre de jetons de l'utilisateur par rapport au nombre de jetons qu'il a placé
		System.out.println("Vous avez maintenant "+utilisateur.getJetons()+" jetons");
		fichierMembre.sauvegarderMembres(profils);
	}

	//cette methode affiche les jeux par nombre de jetons décroissants 
	public void afficherJeuxParNombreJetons(Membre testeur) {
	    HashMap<Jeu, Integer> jeuxUniques = new HashMap<>();
	    for (Membre membre : profils) {
	    	//ajoute dans une map le nombre de jetons associés aux jeux et qui fait attention aux potentiels doublons et d'additioner les jetons
	        HashMap<Jeu, Integer> jetonsPlacesMembre = membre.getJetonsPlaces();        
	        for (Map.Entry<Jeu, Integer> entry : jetonsPlacesMembre.entrySet()) {
	            Jeu jeu = entry.getKey();
	            int jetons = entry.getValue();      
	            boolean doublon = false;
	            for (Map.Entry<Jeu, Integer> uniqueEntry : jeuxUniques.entrySet()) {
	                Jeu uniqueJeu = uniqueEntry.getKey();
	                if (uniqueJeu.getNomJeu().equals(jeu.getNomJeu()) && uniqueJeu.getSupport().equals(jeu.getSupport())) {
	                    doublon = true;	                    
	                    int jetonsExistants = uniqueEntry.getValue();
	                    uniqueEntry.setValue(jetonsExistants + jetons);
	                    break;
	                }
	            }	            
	            if (!doublon) {
	                jeuxUniques.put(jeu, jetons);
	            }
	        }
	        //rajoute les autres jeux qui ne possèdent de jetons placés sur eux et qui fait attention aux doublons
	    HashMap<Jeu, Integer> jeuxMembre = membre.getJeux();
	    for (Map.Entry<Jeu, Integer> entry : jeuxMembre.entrySet()) {
	        Jeu jeu = entry.getKey();
	        int jetons = entry.getValue();
	        boolean doublon = false;
	        for (Map.Entry<Jeu, Integer> uniqueEntry : jeuxUniques.entrySet()) {
	            Jeu uniqueJeu = uniqueEntry.getKey();
	            if (uniqueJeu.getNomJeu().equals(jeu.getNomJeu()) && uniqueJeu.getSupport().equals(jeu.getSupport())) {
	                doublon = true;
	                break;
	            }
	        } 
	        if (!doublon) {
	            jeuxUniques.put(jeu, 0);
	        }
	    }
	    }
	    //finalement, on affiche cette map qui affiche les jeux par nombre de jetons decroissants et qui indique au testeur s'il possède le jeu ou non
	    List<Map.Entry<Jeu, Integer>> sortedList = new ArrayList<>(jeuxUniques.entrySet());
	    sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
	    for (Map.Entry<Jeu, Integer> entry : sortedList) {
	        Jeu jeu = entry.getKey();
	        int jetons = entry.getValue();
	        Jeu jeuxTesteurs=chercherJeu(utilisateur,jeu.getNomJeu(),jeu.getSupport());
	        if(jeuxTesteurs==null) {
	        	System.out.println(" - Jeu : "+jeu.getNomJeu()+(" (Pas possede)"));
		        System.out.println(" - nombre de jetons poses dessus : "+jetons);
		        System.out.println("---------------");
	        }
	        else {
	        	System.out.println(" -Jeu : "+jeu.getNomJeu()+(" (Possede)"));
		        System.out.println(" - nombre de jetons poses dessus : "+jetons);
		        System.out.println("---------------");
        }	        
	    }
	}
	
	
	//methode qui affiche les membres (testeurs et joueurs) par nombre d'evaluations décroissant
	public void afficherMembresPlusGrandNombreEvaluations() {
	    List<Membre> membresTries = new ArrayList<>(profils);
	    membresTries.sort(Comparator.comparingInt(membre -> membre.getListeEval().size()));
	    Collections.reverse(membresTries);   
	    System.out.println("Membres avec le plus grand nombre d'evaluations :");
	    for (Membre membre : membresTries) {
	        System.out.println("- Membre : " + membre.getPseudo());
	        System.out.println("- Nombre d'evaluations : " + membre.getListeEval().size()+"\n");
	    }
	}

	//methode qui affiche les membres (testeurs et joueurs) par nombre d'evaluations positives de leurs évaluations décroissant
	public void afficherMembresPlusGrandNombreLikes() {
	    List<Membre> membresTries = new ArrayList<>(profils);  
	    membresTries.sort(Comparator.comparingInt(membre -> nombLikesEvaluations(membre.getListeEval())));
	    Collections.reverse(membresTries);	    
	    System.out.println("Membres avec le plus grand nombre de likes sur leurs evaluations :");
	    for (Membre membre : membresTries) {
	        int totalLikes = nombLikesEvaluations(membre.getListeEval());
	        System.out.println("- Membre : " + membre.getPseudo());
	        System.out.println("- Nombre de likes : " + totalLikes+"\n");
	    }
	}

	
	//methode qui affiche les membres (testeurs et joueurs) par leur nombre décroissant d'evaluations réalisées puis ensuite de leur nombre  décroissant d'évaluations positives
	public void afficherMembresPlusGrandNombreEvaluationsEtLikes() {
	    List<Membre> membresTries = new ArrayList<>(profils);
	   membresTries.sort(Comparator.comparingInt((Membre membre) -> membre.getListeEval().size()).thenComparingInt(membre -> nombLikesEvaluations(membre.getListeEval())).reversed());
	    System.out.println("Membres avec le plus grand nombre d'evaluations et de likes :");
	    for (Membre membre : membresTries) {
	        int totalEvaluations = membre.getListeEval().size();
	        int totalLikes = nombLikesEvaluations(membre.getListeEval());
	        System.out.println(" - Membre : " + membre.getPseudo());
	        System.out.println("- Nombre d'evaluations : " + totalEvaluations);
	        System.out.println("- Nombre de likes : " + totalLikes + "\n");
	    }
	}

	//methode qui affiche les membres (testeurs et joueurs) par leur nombre décroissant d'evaluations positives  puis ensuite de leur nombre  décroissant d'évaluations réalisées
	public void afficherMembresPlusGrandNombreLikesEtEvaluations() {
	    List<Membre> membresTries = new ArrayList<>(profils);
	    membresTries.sort(Comparator.comparingInt((Membre membre) -> nombLikesEvaluations(membre.getListeEval())).thenComparingInt(membre -> membre.getListeEval().size()).reversed());
	    System.out.println("Membres avec le plus grand nombre de likes et d'evaluations :");
	    for (Membre membre : membresTries) {
	        int totalLikes = nombLikesEvaluations(membre.getListeEval());
	        int totalEvaluations = membre.getListeEval().size();
	        System.out.println(" - Membre : " + membre.getPseudo());
	        System.out.println("- Nombre d'evaluations : " + totalEvaluations);
	        System.out.println("- Nombre de likes : " + totalLikes + "\n");
	    }
	}


	//methode qui compte le nombre de like d'une évaluation
	private int nombLikesEvaluations(List<Evaluation> evaluations) {
	    int totalLikes = 0;
	    for (Evaluation evaluation : evaluations) {
	        totalLikes += evaluation.getEvalPos();
	    }
	    return totalLikes;
	}

	//methode qui nous informe si il y a des evaluations qui ont ete signalees
	public boolean estVide() {
		ArrayList<Evaluation> evaluationsSignalees = new ArrayList<>();
	    for (Membre membre : profils) {
	        for (Evaluation evaluation : membre.getListeEval()) {
	            if (evaluation.getSignalements() > 0) {
	                evaluationsSignalees.add(evaluation);
	            }
	        }
	    }  
	    if(evaluationsSignalees.isEmpty()) {
	    	return false;
	    }
	    return true;
	}
	
	//methode qui affiche les evaluation qui ont ete signalees par nombre décroissant de nombre de signalements
	public ArrayList<Evaluation> afficherEvaluationsSignalees() {
	    ArrayList<Evaluation> evaluationsSignalees = new ArrayList<>();
	    for (Membre membre : profinoniyuls) {
	        for (Evaluation evaluation : membre.getListeEval()) {
	            if (evaluation.getSignalements() > 0) { //evaluations qui ont ete signalees au moins une fois
	                evaluationsSignalees.add(evaluation);
	            }
	        }
	    }    
	   	
		    evaluationsSignalees.sort(Comparator.comparingInt(Evaluation::getSignalements).reversed());
		    System.out.println("evaluations signalees par ordre decroissant du nombre de signalements :");
		    for (Evaluation evaluation : evaluationsSignalees) {
		        System.out.println(" - evaluation signale : " + evaluation.getTitre());
		        System.out.println("    .Auteur : " + evaluation.getAuteur().getPseudo());
		        System.out.println("    .Nombre de signalements : " + evaluation.getSignalements());
		        System.out.println("    .Contenu : " + evaluation.getCommentaire());
		        System.out.println("----------------------------------");
		    }
	    
	    return evaluationsSignalees;
	}

	
	public static void main(String[] args) throws IOException {
	    Plateforme plateforme = new Plateforme();
	    plateforme.choix();
	}
}