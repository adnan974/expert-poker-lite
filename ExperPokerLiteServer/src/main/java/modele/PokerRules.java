/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


/**
 *
 * @author Adnan
 */
public class PokerRules {
    
    private ArrayList<Carte> carteJoueur; 
    private ArrayList<Carte> cartePlateau;
    private Hashtable<String, Integer> dictionnaireValeurCarte = new Hashtable<>();
   
    

    public PokerRules() {
        dictionnaireValeurCarte.put("2",0);
        dictionnaireValeurCarte.put("3",1);
        dictionnaireValeurCarte.put("4",2);
        dictionnaireValeurCarte.put("5",3);
        dictionnaireValeurCarte.put("6",4) ;       
        dictionnaireValeurCarte.put("7",5);        
        dictionnaireValeurCarte.put("8",6) ;       
        dictionnaireValeurCarte.put("9",7) ;       
        dictionnaireValeurCarte.put("10",8)  ;      
        dictionnaireValeurCarte.put("jack",9)  ;      
        dictionnaireValeurCarte.put("queen",10);
        dictionnaireValeurCarte.put("king",11) ;       
        dictionnaireValeurCarte.put("ace",12);
                
    }

    public ArrayList<Carte> getCarteJoueur() {
        return carteJoueur;
    }

    public void setCarteJoueur(ArrayList<Carte> carteJoueur) {
        this.carteJoueur = carteJoueur;
    }

    public ArrayList<Carte> getCartePlateau() {
        return cartePlateau;
    }

    public void setCartePlateau(ArrayList<Carte> cartePlateau) {
        this.cartePlateau = cartePlateau;
    }  
    
    public Integer donneValeurCarte(String figure){
        return dictionnaireValeurCarte.get(figure);
    }
    
   public Integer donneScoreCarteLaPlusForte( ArrayList<Carte> carteJoueur) {
       Integer score = 0;
       ArrayList<Integer> valeurCartesJoueur = new ArrayList<Integer>();
      
        for (Carte uneCarteJoueur : carteJoueur) {
            valeurCartesJoueur.add(uneCarteJoueur.getValeur());
        }

        System.out.println("Table des cartes"+valeurCartesJoueur);
        Collections.sort(valeurCartesJoueur);
        Collections.reverse(valeurCartesJoueur);
        
        score = Collections.max(valeurCartesJoueur);
        valeurCartesJoueur.clear();
         System.out.println("****************************************************");
        return score;
   }
    
   public ArrayList<String> fusionFigurePlateauEtJoueur(){
        //déclarations et affectation des tableaux
        ArrayList<ArrayList> FiguresCartesPlateauEtJoueur = new ArrayList();
        FiguresCartesPlateauEtJoueur = this.recupFigureCartePlateauEtJoueur();

        ArrayList<String> listeFiguresCartesPlateau = new ArrayList();
        listeFiguresCartesPlateau = FiguresCartesPlateauEtJoueur.get(0);

        ArrayList<String> listeFiguresCartesJoueur = new ArrayList();
        listeFiguresCartesJoueur = FiguresCartesPlateauEtJoueur.get(1);

        ArrayList<String> listeFigurePlateauEtJoueur = new ArrayList();

        listeFigurePlateauEtJoueur.addAll(listeFiguresCartesJoueur);
        listeFigurePlateauEtJoueur.addAll(listeFiguresCartesPlateau);
        
        return listeFigurePlateauEtJoueur;
   }
   
   public ArrayList<Carte> recupCartePlateauEtJoueur() {
       ArrayList<Carte> CartesPlateauEtJoueur = new ArrayList();
       CartesPlateauEtJoueur.addAll(this.cartePlateau);
       CartesPlateauEtJoueur.addAll(this.carteJoueur);
       return CartesPlateauEtJoueur;
   }
// Cette méthode récupère les les noms des cartes du plateau et du joueur et les ajoutent dans une ArrayList
   public ArrayList<ArrayList> recupFigureCartePlateauEtJoueur() {
        

        ArrayList<ArrayList> FiguresCartesPlateauEtJoueur = new ArrayList();

        ArrayList<String> FiguresCartesPlateau = new ArrayList();
        ArrayList<String> FiguresCartesJoueur = new ArrayList();

        for (Carte uneCartePlateau : this.cartePlateau) {
            FiguresCartesPlateau.add(uneCartePlateau.getFigure());
        }

        for (Carte uneCarteJoueur : this.carteJoueur) {
            FiguresCartesJoueur.add(uneCarteJoueur.getFigure());
        }
        FiguresCartesPlateauEtJoueur.add(FiguresCartesPlateau);
        FiguresCartesPlateauEtJoueur.add(FiguresCartesJoueur) ;       
       
        return FiguresCartesPlateauEtJoueur;

    }

    // Cette méthode gardes d'une ArrayListe qui ont des noms 
    public ArrayList<String> recupFigureCarteSimilaire(){

        //déclarations et affectation des tableaux
        ArrayList<String> listeFiguresCarteSimilaire = new ArrayList();
        listeFiguresCarteSimilaire = this.fusionFigurePlateauEtJoueur();       

        // supprime les cartes uniques dans listeNomsCarteSimilaire
        ArrayList<String> tempListeFiguresCarteSimilaire = new ArrayList();
        tempListeFiguresCarteSimilaire.addAll(listeFiguresCarteSimilaire);
        

        ArrayList<String> tempListeFiguresCarteUnique = new ArrayList();
        tempListeFiguresCarteUnique.addAll(listeFiguresCarteSimilaire);

        for (String uneCarteUnique : tempListeFiguresCarteUnique) {

            tempListeFiguresCarteSimilaire.remove(uneCarteUnique);
            if (!tempListeFiguresCarteSimilaire.contains(uneCarteUnique)) {
                listeFiguresCarteSimilaire.remove(uneCarteUnique);
            }
            tempListeFiguresCarteSimilaire.clear();
            tempListeFiguresCarteSimilaire.addAll(tempListeFiguresCarteUnique);
        }

        return listeFiguresCarteSimilaire;
    }

    public ArrayList<ArrayList> regroupFigureCarteMemeValeur() {
      
        ArrayList<String> listeNomsCarte = new ArrayList();
        listeNomsCarte = recupFigureCarteSimilaire();

        Set<String> listeCarteUnique = new HashSet(listeNomsCarte);

        ArrayList<ArrayList> regroupCarteSimilaire = new ArrayList();

        ArrayList<String> tempCarteaAjouter = new ArrayList();

        for (String s : listeCarteUnique) {
            for (int i = 0; i < Collections.frequency(listeNomsCarte, s); i++) {             
                tempCarteaAjouter.add(s);               
            }
            regroupCarteSimilaire.add(new ArrayList<String>(tempCarteaAjouter));
            tempCarteaAjouter.clear();
        }
        return regroupCarteSimilaire;

    }
    
//    public Combinaison donneValeurHauteurLaPlusForte(){
//        ArrayList<ArrayList> regroupListeCarte = new ArrayList();
//        regroupListeCarte = this.regroupFigureCarteMemeValeur();
//        
//        ArrayList<ArrayList> listeFigureCarteEtPlateau = new ArrayList();
//        listeFigureCarteEtPlateau = this.fusionFigurePlateauEtJoueur()
//        
//        if (regroupListeCarte.isEmpty() == true ){
//              for()
//            }
//
//    }

    public Combinaison checkPaire() {
        // A rajouter si temps:                   
        //                      -Cas ou il y'a 3 paires -> prendre les deux les plus fortes

        
        //déclarations des tableaux
        ArrayList<ArrayList> regroupListeCarte = new ArrayList();
        regroupListeCarte = this.regroupFigureCarteMemeValeur();
        
        

        //recherche d'un paire
        for(ArrayList uneCombinaison : regroupListeCarte ){
            if (uneCombinaison.size() == 2){
               
                return new Combinaison("Paire",100);    // ajouter ici le score de la hauteur de la paire à 100
            }
            
        }
        return new Combinaison("No Combinaison",0);
    }

    public Combinaison checkDoublePaires() {
        // A rajouter si temps: -Cas ou on a une paire dans la main et dans la main
        //     

        //déclarations des tableaux
        ArrayList<ArrayList> regroupListeCarte = new ArrayList();
        regroupListeCarte = this.regroupFigureCarteMemeValeur();

        //recherche d'un paire
        int i = 0;
        for(ArrayList uneCombinaison : regroupListeCarte ){
            if (uneCombinaison.size() == 2){
                i ++;
            }
            
        }
        if(i == 2){
            return new Combinaison("Double Paire",200);
        }
        return new Combinaison("No Combinaison",0);
    }

    public Combinaison checkBrelan() {

       //déclarations des tableaux
        ArrayList<ArrayList> regroupListeCarte = new ArrayList();
        regroupListeCarte = this.regroupFigureCarteMemeValeur();

        //recherche d'un paire
        
        for(ArrayList uneCombinaison : regroupListeCarte ){
            if (uneCombinaison.size() == 3){
                return new Combinaison("Brelan",300);
            }
        }
        return new Combinaison("No Combinaison",0);
    }
    
    public Combinaison checkCarre() {

       //déclarations des tableaux
        ArrayList<ArrayList> regroupListeCarte = new ArrayList();
        regroupListeCarte = this.regroupFigureCarteMemeValeur();

        //recherche d'un paire
        
        for(ArrayList uneCombinaison : regroupListeCarte ){
            if (uneCombinaison.size() == 4){
                return new Combinaison("Carré",600);
            }
        }
        return new Combinaison("No Combinaison",0);
    }
    
    
    public Combinaison checkFull() {

        if((this.checkBrelan().getValeur() !=0) && (this.checkPaire().getValeur() != 0) ){
            return new Combinaison("Full", 500);
        }
        return new Combinaison("No Combinaison",0);
    }
    
    
    
    public void definirMeilleureCombinaisonJoueur(Joueur joueur ){    
    
        Combinaison meilleureCombinaison = new Combinaison("No Combinaison",0);        
        Combinaison combiProvisoire = new Combinaison("No Combinaison",0);        
        
        combiProvisoire = checkPaire();        
        meilleureCombinaison = combiProvisoire.getValeur()>meilleureCombinaison.getValeur()?combiProvisoire:meilleureCombinaison;
        
        combiProvisoire = checkDoublePaires();        
        meilleureCombinaison = combiProvisoire.getValeur()>meilleureCombinaison.getValeur()?combiProvisoire:meilleureCombinaison;
        
        combiProvisoire = checkBrelan();        
        meilleureCombinaison = combiProvisoire.getValeur()>meilleureCombinaison.getValeur()?combiProvisoire:meilleureCombinaison;
        
         combiProvisoire = checkCarre();        
        meilleureCombinaison = combiProvisoire.getValeur()>meilleureCombinaison.getValeur()?combiProvisoire:meilleureCombinaison;
        
        combiProvisoire = checkFull();        
        meilleureCombinaison = combiProvisoire.getValeur()>meilleureCombinaison.getValeur()?combiProvisoire:meilleureCombinaison;
        
        System.out.println("meilleur combinaison: "+meilleureCombinaison.getValeur());
        joueur.setBestCombi(meilleureCombinaison);   
    }
    
    public Joueur definirMeilleureCombinaisonJoueurSiEgalite(ArrayList<Joueur> joueurAvecCombiSimilaire){ 
        Joueur joueurGagnant = new Joueur("");
        Integer scoreJoueurGagnant = 0;
        Integer scoreJoueurTemp;
        HashMap<Joueur,Integer> mapJoueurScoreCombinaison = new HashMap<Joueur,Integer>();
        // recupere la combinaison des joueur
        String combinaisonJoueurs = joueurAvecCombiSimilaire.get(0).getBestCombi().getNom();
        System.out.println("**********************EGALITE******************************");
        System.out.println("combinaisonJoueurs : "+combinaisonJoueurs);
        // Pour chaque combinaison, on prend les cartes des joueurs et on traite l'égalité
        switch(combinaisonJoueurs){
            
            // rq La regle ici a été simplifié: C'est celui qui a la carte la plus forte dans sa main qui gagne
            case "No Combinaison":
            case "Paire":

                  for(Joueur unJoueur:joueurAvecCombiSimilaire){
                       System.out.println("Nom du joueur courant : "+unJoueur.getNom());
                      System.out.println("Main du joueur courant: "+unJoueur.getMainJoueur());
                     
                      // On recupère le score des 5 cartes le splus forte du joueur
                      scoreJoueurTemp =  this.donneScoreCarteLaPlusForte(unJoueur.getMainJoueur());
                      mapJoueurScoreCombinaison.put(unJoueur,scoreJoueurTemp);
                      
                      System.out.println("mapJoueurScoreCombinaison : "+mapJoueurScoreCombinaison);
                      System.out.println("Score joueur courant : "+scoreJoueurTemp);
                      if(scoreJoueurTemp > scoreJoueurGagnant){
                             scoreJoueurGagnant = scoreJoueurTemp;    
                      }    
                  }
                  int maxValueInMap=(Collections.max(mapJoueurScoreCombinaison.values()));  
                  System.out.println("maxValueInMap: "+maxValueInMap);
                  for (Map.Entry<Joueur, Integer> entry : mapJoueurScoreCombinaison.entrySet()) {  
                  if (entry.getValue()==maxValueInMap) {
                    System.out.println("Joueur gagnant: "+entry.getKey().getNom());  
                    return entry.getKey();
                  }
            }
 
        }
        
        return joueurGagnant;
    }
    
    
    
    
    
    
}
