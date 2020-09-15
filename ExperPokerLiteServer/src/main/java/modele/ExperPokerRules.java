/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;

/**
 *
 * @author borri
 */
public class ExperPokerRules {

    public static ArrayList<Carte> fusionListeCartes(ArrayList<Carte> listeCartePlateau, ArrayList<Carte> listeCarteJoueur) {
       
        ArrayList<Carte> fusionListeCartes = new ArrayList<Carte>();
        try {
            for (int i = 0; i < listeCartePlateau.size(); i++) {
                fusionListeCartes.add(listeCartePlateau.get(i));
            }            
            for (int i = 0; i < listeCarteJoueur.size(); i++) {
                fusionListeCartes.add(listeCarteJoueur.get(i));
            }
        } catch (Exception e) {
            System.out.println("SERVEUR ERREUR : erreur lors de la fusion des cartes main et plateau");            
            System.out.println(e.getMessage());
        }               
        
        return fusionListeCartes;
    }

    public static Combinaison checkPaire(ArrayList<Carte> fusionListeCartes) {        
        boolean hasPair = false;
        Carte carteAverifier;

        for (int i = 0; i < fusionListeCartes.size(); i++) {
            carteAverifier = fusionListeCartes.get(i);
            for (int j = i + 1; j < fusionListeCartes.size(); j++) {  
                if (carteAverifier.getValeur() == fusionListeCartes.get(j).getValeur()) {
                    hasPair = true;
                    return new Combinaison("Paire", 100);
                }
            }
            if (hasPair) {
                break;
            }
        }       
        return new Combinaison(null, 0);
    }

    public static void DeterminerMeilleureCombinaison(Joueur joueur) {

        ArrayList<Carte> fusionListeCartes = fusionListeCartes(joueur.getCartesPlateau(), joueur.getMainJoueur());
        joueur.setBestCombi(checkPaire(fusionListeCartes));
    }
}
