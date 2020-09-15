/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import modele.*;
import java.util.ArrayList;

/**
 *
 * @author agoulamaly
 */
public class MainPourTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Ouverture de la zone de test PokerRules");
        
        //Dealer d = new Dealer();
       
        
        //d.obtenirNouveauDeck();
        //ArrayList<Carte> CartesPlateau =  d.tirer5CartesPlateau();
        //ArrayList<Carte> CartesJoueur = d.tirer2CartesJoueur();
        
        //System.out.println(CartesPlateau);
       PokerRules p = new PokerRules();
//        p.setCarteJoueur(d.tirer2CartesJoueur());
//        p.setCartePlateau(d.tirer5CartesPlateau()); 
//        
//        System.out.println(p.getCartePlateau());
       Carte c1 = new Carte("roi","",0,"","");
       Carte c2 = new Carte("roi","",0,"","");
       ArrayList<Carte> carteJoueur = new ArrayList();
       carteJoueur.add(c1);
       carteJoueur.add(c2);   
       p.setCarteJoueur(carteJoueur);
       
       ArrayList<Carte> cartePlateau = new ArrayList();
       Carte c3 = new Carte("4","",0,"","");
       Carte c4 = new Carte("4","",0,"","");
       Carte c5 = new Carte("4","",0,"","");
       Carte c6 = new Carte("4","",0,"","");
       Carte c7 = new Carte("8","",0,"","");
       cartePlateau.add(c3);
       cartePlateau.add(c4);
       cartePlateau.add(c5);
       cartePlateau.add(c6);
       cartePlateau.add(c7);
       p.setCartePlateau(cartePlateau);
       
       System.out.println(p.recupFigureCartePlateauEtJoueur());
       System.out.println(p.recupFigureCarteSimilaire()); 
       System.out.println(p.regroupFigureCarteMemeValeur());
       System.out.println(p.checkPaire()); 
       System.out.println(p.checkDoublePaires());
       System.out.println(p.checkBrelan());
       System.out.println(p.checkCarre());
       System.out.println(p.checkFull());
       
       System.out.println(p.fusionFigurePlateauEtJoueur());
      
    }

}
