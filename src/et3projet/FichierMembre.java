package et3projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

/**
 * La classe FichierMembre permet de charger et sauvegarder les membres et leurs données dans un fichier texte.
 */

public class FichierMembre {
    private String nomFichier;
    
    /**
    * Constructeur de la classe FichierMembre.
    * @param nomFichier Le nom du fichier utilisé pour sauvegarder les membres.
    */
    
    public FichierMembre(String nomFichier) {
        this.nomFichier = nomFichier;
    }
    
    
    //Cette methode permet de lire le fichier membres.txt et avec les données renseignées qui ont été ajoutés dans sauvegardeMembres(), va ajouter dans les données respectives par rapport aux membres, Jeux, évaluations,...
    public ArrayList<Membre> chargerMembres() throws IOException {
        ArrayList<Membre> membres = new ArrayList<>();
        try {
            File fichier = new File(nomFichier);
            Scanner scanner = new Scanner(fichier);
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] champs = ligne.split(";");
                String pseudo = champs[0]; //CHAMPS[0] : Pseudo
                String type = champs[1];  //CHAMPS[1] : Role
                int nbJetons = Integer.parseInt(champs[2]); // CHAMPS[2] : Nombre de jetons
                
                Membre membre = null;
                if (type.equalsIgnoreCase("invite")) { //pour les joueurs bloques
                	membre = new Invite(pseudo);
                }
                else if (type.equalsIgnoreCase("Joueur")) {
                    membre = new Joueur(pseudo);
                } else if (type.equalsIgnoreCase("Testeur")) {
                    membre = new Testeur(pseudo);
                } else if (type.equalsIgnoreCase("Administrateur")) {
                    membre = new Administrateur(pseudo);
                }
                
                membre.SetJetons(nbJetons); // mise à jour du nombre de jetons 
                
                if (!membre.getRole().equalsIgnoreCase("invite")) {
                
	                if (!champs[3].equalsIgnoreCase("null")) {	//CHAMPS[3] :  nom de Jeu, temps de jeu, support de jeu
	                    String[] jeuxChamps = champs[3].split(";");
	                    for (String jeuChamp : jeuxChamps) {
	                    	String[] jeuEtTemps = jeuChamp.split("\\|");
	                        String nomJeu = jeuEtTemps[0];
	                        int tempsJeu = Integer.parseInt(jeuEtTemps[1]);
	                        Jeu jeu;
	                        try {
	                        	//conservation des données
	                        	 jeu = new Jeu(nomJeu, jeuEtTemps[2]);
	                        	 membre.AjouterJeux(jeu, tempsJeu);
	                        } catch (IOException e) {
	                            System.out.println("Le fichier de support pour le jeu " + nomJeu + "n'a pas ete trouve");
	                        }
	                        
	                    }
	                }
	                if (!champs[4].equalsIgnoreCase("0")) { //CHAMPS[4] : temps de jeu totale
	                    try {
	                        int nbHeures = Integer.parseInt(champs[4]);
	                        membre.setTempsDeJeuTotale(nbHeures); //mise à jour du nombre de temps de jeu total
	                    } catch (NumberFormatException e) {
	                        System.out.println("Erreur lors de la conversion du temps de jeu total : " + e.getMessage());
	                    }
	                }
	
	                if (!champs[5].equalsIgnoreCase("null")) {	//CHAMPS[5] : titre evaluation, commentaire, note, nomJeu, support, ordre, nombre de like, nombre d'avis neutre, nombre de dislike, nombre de signalements
	                	String[] evalChamps = champs[5].split(";");
	                    for (String evalChamp : evalChamps) {
	                    	String[] evalEtJeu = evalChamp.split("\\|");
	                
	                        
	                        String titre = evalEtJeu[0];
	                        String com = evalEtJeu[1];
	                        int note= Integer.parseInt(evalEtJeu[2]);
	                        Evaluation eval;
	                        String nomJeu2=evalEtJeu[3];
	                        String support2=evalEtJeu[4];
	                        int ordre = Integer.parseInt(evalEtJeu[5]);
	                        int evalPos = Integer.parseInt(evalEtJeu[6]);
	                        int evalNeutres = Integer.parseInt(evalEtJeu[7]);
	                        int evalNeg = Integer.parseInt(evalEtJeu[8]);
	                        int sig = Integer.parseInt(evalEtJeu[9]);
	                        
	                        Jeu jeu;
	                        try {
	                        	//conservation des données
	                        	jeu = new Jeu(nomJeu2, support2);
	                        	eval=new Evaluation(note,titre,com,jeu,membre,ordre,sig);
	                            membre.AjouterEval(eval, jeu);
	                            membre.ajouterListeEval(eval);
	                            eval.setEvalPos(evalPos);
	                            eval.setEvalNeutres(evalNeutres);
	                            eval.setEvalNeg(evalNeg);
	                            eval.setSignalements(sig);	                         
	                            
	                        } catch (IOException e) {
	                            System.out.println("Le fichier rencontre un probleme");
	                        }
	                 
	                }
	                }
	                
	                if (!champs[6].equalsIgnoreCase("0")) { //CHAMPS[6] : nombre d'évaluations totale
	                	int nmbEvalTotales = Integer.parseInt(champs[6]);
	                    membre.setEval(nmbEvalTotales); // mise à jour du nombre d'évaluation
	                }
	                
	               if(!champs[7].equalsIgnoreCase("null")) { //CHAMPS[7] :  (eval qui est liké) : titre evaluation, commentaire, note, nomJeu, pseudo Auteur, role Auteur,  support, ordre, nombre de like, nombre d'avis neutre, nombre de dislike, nombre de signalements
	                	String[] evalPosChamps = champs[7].split(",");
	                	for(String evalsPosChamps : evalPosChamps) {
	                		String[] evalEtTout= evalsPosChamps.split("\\|");               		            
	                		
	                		String titreEval = evalEtTout[0];
	                		String comEval = evalEtTout [1];
	                		int noteEval = Integer.parseInt(evalEtTout[2]);
	                		String nomJeu3=evalEtTout[3];
	                		String support3=evalEtTout[4];
	                		String pseudoAuteur2 = evalEtTout[5];
	                        String roleAuteur2 = evalEtTout [6];
	                        int ordre2 = Integer.parseInt(evalEtTout[7]);
	                        int evalPos2 = Integer.parseInt(evalEtTout[8]);
	                        int evalNeutres2 = Integer.parseInt(evalEtTout[9]);
	                        int evalNeg2 = Integer.parseInt(evalEtTout[10]);
	                        int sign = Integer.parseInt(evalEtTout[11]);
	                		Membre auteurEval2=null;
	                		if (roleAuteur2.equalsIgnoreCase("Joueur")) {
	                            auteurEval2 = new Joueur(pseudoAuteur2);
	                        } else if (roleAuteur2.equalsIgnoreCase("Testeur")) {
	                            auteurEval2 = new Testeur(pseudoAuteur2);
	                        } else if (roleAuteur2.equalsIgnoreCase("Administrateur")) {
	                            auteurEval2 = new Administrateur(pseudoAuteur2);
	                        }
	                		Jeu jeu2;
	                		 try {
	                			 //conservation des données
	                         	jeu2 = new Jeu(nomJeu3, support3);
	                         	Evaluation eval2=new Evaluation(noteEval,titreEval,comEval,jeu2,auteurEval2,ordre2,sign);
	                         	EvalPositives evalPos = new EvalPositives(eval2);
	                            membre.ajoutEvalLike(evalPos);
	                            eval2.setEvalPos(evalPos2);
	                            eval2.setEvalNeutres(evalNeutres2);
	                            eval2.setEvalPos(evalNeg2);
	                            eval2.setSignalements(sign);
	                         } catch (IOException e) {
	                             System.out.println("Le fichier rencontre un probleme");
	                         }
	                	}
	                }
	                
	                //Champs[8]
	               if(!champs[8].equalsIgnoreCase("null")) { //CHAMPS[8] :  (eval avec avis neutre) : titre evaluation, commentaire, note, nomJeu, pseudo Auteur, role Auteur,  support, ordre, nombre de like, nombre d'avis neutre, nombre de dislike, nombre de signalements
	               	String[] evalPosChamps2 = champs[8].split(",");
	               	for(String evalsPosChamps2 : evalPosChamps2) {
	               		String[] evalEtTout2= evalsPosChamps2.split("\\|");

	               String titreEval = evalEtTout2[0];
	       			String comEval = evalEtTout2 [1];
	       			int noteEval = Integer.parseInt(evalEtTout2[2]);
	       			String nomJeu3=evalEtTout2[3];
	       			String support3=evalEtTout2[4];
	       			String pseudoAuteur2 = evalEtTout2[5];
	               String roleAuteur2 = evalEtTout2 [6];
	               int ordre2 = Integer.parseInt(evalEtTout2[7]);
	               int evalPos2 = Integer.parseInt(evalEtTout2[8]);
	               int evalNeutres2 = Integer.parseInt(evalEtTout2[9]);
	               int evalNeg2 = Integer.parseInt(evalEtTout2[10]);
	               int sign = Integer.parseInt(evalEtTout2[11]);
	       		Membre auteurEval2=null;
	       		if (roleAuteur2.equalsIgnoreCase("Joueur")) {
	                   auteurEval2 = new Joueur(pseudoAuteur2);
	               } else if (roleAuteur2.equalsIgnoreCase("Testeur")) {
	                   auteurEval2 = new Testeur(pseudoAuteur2);
	               } else if (roleAuteur2.equalsIgnoreCase("Administrateur")) {
	                   auteurEval2 = new Administrateur(pseudoAuteur2);
	               }
	       		Jeu jeu2;
	       		 try {
	                	//conservations des données
	       			 jeu2 = new Jeu(nomJeu3, support3);
	                	Evaluation eval2=new Evaluation(noteEval,titreEval,comEval,jeu2,auteurEval2,ordre2,sign);
	                	EvalNeutres evalNeut = new EvalNeutres(eval2);
	                   membre.ajoutEvalNeutre(evalNeut);
	                   eval2.setEvalPos(evalPos2);
	                   eval2.setEvalNeutres(evalNeutres2);
	                   eval2.setEvalPos(evalNeg2);
	                   eval2.setSignalements(sign);
	                   
	                } catch (IOException e) {
	                    System.out.println("Le fichier rencontre un probleme");
	                }
	       	}
	       }
	             
	               //Champ[9]
	               
	               if(!champs[9].equalsIgnoreCase("null")) { //CHAMPS[9] : //CHAMPS[7] :  (eval qui est disliké) : titre evaluation, commentaire, note, nomJeu, pseudo Auteur, role Auteur,  support, ordre, nombre de like, nombre d'avis neutre, nombre de dislike, nombre de signalements
	                  	String[] evalPosChamps3 = champs[9].split(",");
	                  	for(String evalsPosChamps3 : evalPosChamps3) {
	                  		String[] evalEtTout3= evalsPosChamps3.split("\\|");              	
	                  
	                  String titreEval = evalEtTout3[0];
	          			String comEval = evalEtTout3 [1];
	          			int noteEval = Integer.parseInt(evalEtTout3[2]);
	          			String nomJeu3=evalEtTout3[3];
	          			String support3=evalEtTout3[4];
	          			String pseudoAuteur2 = evalEtTout3[5];
	                  String roleAuteur2 = evalEtTout3 [6];
	                  int ordre2 = Integer.parseInt(evalEtTout3[7]);
	                  int evalPos2 = Integer.parseInt(evalEtTout3[8]);
	                  int evalNeutres2 = Integer.parseInt(evalEtTout3[9]);
	                  int evalNeg2 = Integer.parseInt(evalEtTout3[10]);
	                  int sign = Integer.parseInt(evalEtTout3[11]);
	          		Membre auteurEval2=null;
	          		if (roleAuteur2.equalsIgnoreCase("Joueur")) {
	                      auteurEval2 = new Joueur(pseudoAuteur2);
	                  } else if (roleAuteur2.equalsIgnoreCase("Testeur")) {
	                      auteurEval2 = new Testeur(pseudoAuteur2);
	                  } else if (roleAuteur2.equalsIgnoreCase("Administrateur")) {
	                      auteurEval2 = new Administrateur(pseudoAuteur2);
	                  }
	          		Jeu jeu2;
	          		 try {
	                   	//conservation des données
	          			 jeu2 = new Jeu(nomJeu3, support3);
	                   	Evaluation eval2=new Evaluation(noteEval,titreEval,comEval,jeu2,auteurEval2,ordre2,sign);
	                   	EvalNegatives evalNeg = new EvalNegatives(eval2);
	                      membre.ajoutEvalDislikes(evalNeg);
	                      eval2.setEvalPos(evalPos2);
	                      eval2.setEvalNeutres(evalNeutres2);
	                      eval2.setEvalPos(evalNeg2);
	                      eval2.setSignalements(sign);

	                   } catch (IOException e) {
	                       System.out.println("Le fichier rencontre un probleme");
	                   }
	          	}
	          }
	                
	             //Champs[10] : nombre Evaluations Likes par un membre
	                    try {
	                        int nbEvalLikes = Integer.parseInt(champs[10]);
	                			membre.setNombEvalQueJaiLike(nbEvalLikes); //conservation de la donnée
	                    } catch (NumberFormatException e) {
	                        System.out.println("Erreur lors de la conversion du nombre d'evals positives postées : " + e.getMessage());
	                    }
	                    
	                  //Champs[11] : nombre Evaluations Neutre faites par un membre
	                    try {
	                        int nbEvalNeutres = Integer.parseInt(champs[11]);
	                        membre.setNombEvalNeutresQueJaiFaite(nbEvalNeutres); // conservation de la donnée 
	                    } catch (NumberFormatException e) {
	                        System.out.println("Erreur lors de la conversion du nombre d'evals neutres postées : " + e.getMessage());
	                    } 
	                    
	                   //Champs[12]  : nombre d'évaluations dislikés par un membre
	                    try {
	                        int nbEvalNeg = Integer.parseInt(champs[12]);
	                        membre.setNombEvalQueJaiDislike(nbEvalNeg); //conservation de la donnée
	                    } catch (NumberFormatException e) {
	                        System.out.println("Erreur lors de la conversion du nombre d'evals négatives postées : " + e.getMessage());
	                    } 
	                    
	                    //Champs[13]
	                    
	                    if(!champs[13].equalsIgnoreCase("null")) { //CHAMPS[13] : titre Test, com test, note Interface, note Gameplay, note Optimisation,  date du test,  note Globale, auteur test, nom du jeu testé, support du jeu testé 
	                      	String[] testChamps = champs[13].split(",");
	                      	for(String testsChamps2 : testChamps) {
	                      		String[] testEtTout= testsChamps2.split("\\|");             	
	                      
	                      String titreTest = testEtTout[0];
	              		String comTest = testEtTout [1];
	              		int noteInterface = Integer.parseInt(testEtTout[2]);
	              		int noteGameplay = Integer.parseInt(testEtTout[3]);
	              		int noteOptimisation = Integer.parseInt(testEtTout[4]);
	              		LocalDate date = LocalDate.parse(testEtTout[5]);
	                    int noteGlobale = Integer.parseInt(testEtTout[6]);
	                    String auteur = testEtTout[7];
	                    String nomJeu= testEtTout[8];
	                    String support=testEtTout[9];
	              		Jeu jeu;
	              		try {
	              			//conservation des données 
	                       	jeu = new Jeu(nomJeu, support);
	                       	Test test=new Test(titreTest,comTest,noteInterface,noteGameplay,noteOptimisation,date,noteGlobale,membre,jeu);
	                       	membre.ajouterListeTest(test);
	              		} catch (NumberFormatException e) {
	              			System.out.println("Erreur lors de la conversion du nombre d'evals négatives postées : " + e.getMessage());
	              		}
	                        	
	                      	}
	                    }
	                    
	                    //Champs[14]
	                    if (!champs[14].equalsIgnoreCase("null")) {	//CHAMPS[14] : nom du jeu, nombre de jetons, support
	                        String[] jeuxChamps = champs[14].split(";");
	                        for (String jeuChamp : jeuxChamps) {
	                        	String[] jeuEtTemps = jeuChamp.split("\\|");
	                           
	                            String nomJeu = jeuEtTemps[0];
	                            int nombJetons = Integer.parseInt(jeuEtTemps[1]);
	                            String support = jeuEtTemps[2];
	                            Jeu jeu;
	                            try {
	                            	//conservation des données
	                            	 jeu = new Jeu(nomJeu, support);
	                            	 membre.ajoutJetons(jeu,nombJetons);
	                            } catch (IOException e) {
	                                System.out.println("Le fichier de support pour le jeu " + nomJeu + "n'a pas ete trouve");
	                            }
	                            
	                        }
	                    }
                }  
                membres.add(membre);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + nomFichier + " n'existe pas.");
        }
        return membres;
    }

    /**
     * Sauvegarde les membres dans un fichier.
     * @param membres La liste des membres à sauvegarder.
     */
    public void sauvegarderMembres(ArrayList<Membre> membres) {
        try {
            FileWriter writer = new FileWriter(nomFichier);
            for (Membre membre : membres) {
            	String type = "";
            	if (membre.getRole().equalsIgnoreCase("invite")) {
            		 type = "Invite";
            	}
            	else if (membre.getRole().equalsIgnoreCase("Joueur")) {
                    type = "Joueur";
                } else if (membre.getRole().equalsIgnoreCase("Testeur")) {
                    type = "Testeur";
                } else if (membre.getRole().equalsIgnoreCase("Administrateur")) {
                    type = "Administrateur";
                }
                //champs[0],champs[1],champs[2]
                String ligne = membre.getPseudo() + ";" + type + ";" + membre.getJetons(); 
                
                if (!membre.getRole().equalsIgnoreCase("invite")) {
                
	                //champs[3]
	                HashMap<Jeu, Integer> jeux = membre.getJeux();
	                if (!jeux.isEmpty()) {
	                    ligne += ";";
	                    int i = 0;
	                    for (HashMap.Entry<Jeu, Integer> entry : jeux.entrySet()) {
	                        Jeu jeu = entry.getKey();
	                        int tempsJeu = entry.getValue();
	                        String support = jeu.getSupport();
	                        ligne += jeu.getNomJeu() + "|" + tempsJeu + "|" + support;
	                        if (i != jeux.size() - 1) {
	                            ligne += ",";
	                        }
	                        i++;
	                    }
	                }
	                else {
	                	ligne+=";"+"null";
	                }
	                
	               
	                    
	                //champs[4]
	                
	                ligne+=";"+membre.getTempsDeJeuTotale();
	                
	                   
	                	//champs[5]
	                	ArrayList<Evaluation> evaluations = membre.getListeEval(); 
	                    
	                        ligne += ";";
	                        int j=0;
	                        if (evaluations.isEmpty()) {
	                        	ligne+= "null";
	                        }
	                        else {for (Evaluation n:  evaluations) {
	                        		Jeu jeu = n.getJeu();
		                            String nomJeu = jeu.getNomJeu();
		                            String support2 = jeu.getSupport();
		                            int note=n.getNote();
		                            String titre = n.getTitre();
		                            String com = n.getCommentaire();
		                            ligne += titre + "|" + com + "|" + note + "|" + nomJeu + "|" + support2+ "|"+ n.getOrdre()+"|"+n.getEvalPos()+"|"+n.getEvalNeutre()+"|"+n.getEvalNeg()+"|"+n.getSignalements();// n.getAuteur().getPseudo()+ "|" + n.getAuteur().getRole(); 
		                            if (j != evaluations.size() - 1) {
		                                ligne += ",";
		                            }
		                            j++;
	                            
	                        }
	                        }
	                       
	                        
	                //champs[6]
	                ligne+=";"+membre.getNombEvaluations();
	                
	                
	                //champs[7]
	                ArrayList<EvalPositives> evalPos = membre.getEvalPositives();
	                int z=0;
	                ligne+=";";
	                if(evalPos.isEmpty()) {
	                	ligne+="null";
	                }
	                else {
	                	for(EvalPositives l : evalPos) {
	                    	ligne += l.getEval().getTitre() + "|" + l.getEval().getCommentaire() + "|" + l.getEval().getNote() + "|" + l.getEval().getNom().getNomJeu() + "|" + l.getEval().getJeu().getSupport() + "|" + l.getEval().getAuteur().getPseudo() + "|"+ l.getEval().getAuteur().getRole()+ "|" + l.getEval().getOrdre()+"|"+l.getEval().getEvalPos()+"|"+l.getEval().getEvalNeutre()+"|"+l.getEval().getEvalNeg()+"|"+l.getEval().getSignalements();                     	
	                    
	                	if (z != evalPos.size() - 1) {
	                        ligne += ",";
	                    }
	                    z++;
	                	}
	                }
	                
	                
	                
	              //champs[8]
	                ArrayList<EvalNeutres> evalNeutre = membre.getListeEvalNeutres();
	                int a=0;
	                ligne+=";";
	                if(evalNeutre.isEmpty()) {
	                	ligne+="null";
	                }
	                else {
	                	for(EvalNeutres l : evalNeutre) {
	                    	ligne += l.getEval().getTitre() + "|" + l.getEval().getCommentaire() + "|" + l.getEval().getNote() + "|" + l.getEval().getNom().getNomJeu() + "|" + l.getEval().getJeu().getSupport() + "|" + l.getEval().getAuteur().getPseudo() + "|"+ l.getEval().getAuteur().getRole()+ "|" + l.getEval().getOrdre()+"|"+l.getEval().getEvalPos()+"|"+l.getEval().getEvalNeutre()+"|"+l.getEval().getEvalNeg()+"|"+l.getEval().getSignalements();                     	
	                    
	                	if (a != evalPos.size() - 1) {
	                        ligne += ",";
	                    }
	                    a++;
	                	}
	                }
	                
	              //champs[9]
	                
	                ArrayList<EvalNegatives> evalNeg = membre.getListeEvalNegatives();
	                int b=0;
	                ligne+=";";
	                if(evalNeg.isEmpty()) {
	                	ligne+="null";
	                }
	                else {
	                	for(EvalNegatives l : evalNeg) {
	                    	ligne += l.getEval().getTitre() + "|" + l.getEval().getCommentaire() + "|" + l.getEval().getNote() + "|" + l.getEval().getNom().getNomJeu() + "|" + l.getEval().getJeu().getSupport() + "|" + l.getEval().getAuteur().getPseudo() + "|"+ l.getEval().getAuteur().getRole()+ "|" + l.getEval().getOrdre()+"|"+l.getEval().getEvalPos()+"|"+l.getEval().getEvalNeutre()+"|"+l.getEval().getEvalNeg()+"|"+l.getEval().getSignalements();                     	
	                    
	                	if (b != evalNeg.size() - 1) {
	                        ligne += ",";
	                    }
	                    b++;
	                	}
	                }
	                
	                
	              //champs[10]
	                ligne+=";"+membre.getNombEvalQueJaiLike();
	                
	                
	                //champs[11]
	                ligne+=";"+membre.getNombEvalNeutresQueJaiFaite();
	                
	                
	                //champs[12]
	                ligne+=";"+membre.getNombEvalQueJaiDislike();
	                
	               //champs[13] 
	                ArrayList<Test> listeTests = membre.getListeTest();
	                int r=0;
	                ligne+=";";
	                if(listeTests.isEmpty()) {
	                	ligne+="null";
	                }
	                else {
	                	for(Test p : listeTests) {
	                    	ligne += p.getTitre() + "|" + p.getCommentaire() + "|" + p.getNoteInterface() + "|" + p.getNoteGameplay() + "|" + p.getNoteOptimisation() + "|" + p.getDate() + "|"+ p.getNoteGlobale()+ "|" + p.getAuteur().getPseudo() +"|"+p.getJeu().getNomJeu()+"|"+p.getJeu().getSupport();                     	
	                    
	                	if (r != listeTests.size() - 1) {
	                        ligne += ",";
	                    }
	                    r++;
	                	}
	                }
	                
	
	                //Champs[14]
	                HashMap<Jeu,Integer> jetonsPlaces = membre.getJetonsPlaces();
	                if (jetonsPlaces.isEmpty()) {
	                	ligne+=";"+"null";
	                }
	                else {
	                    ligne += ";";
	                    int i = 0;
	                    for (HashMap.Entry<Jeu, Integer> entry : jetonsPlaces.entrySet()) {
	                        Jeu jeu = entry.getKey();
	                        int jetons = entry.getValue();
	                        ligne += jeu.getNomJeu() + "|" + jetons + "|" + jeu.getSupport();
	                        if (i != jetonsPlaces.size() - 1) {
	                            ligne += ",";
	                        }
	                        i++;
	                    }
	                }
                }
                ligne += "\n";
                writer.write(ligne);
                //System.out.println(ligne);
            }
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
