/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 *
 * @author borri
 */
public class Dealer implements Serializable {

    private long id;
    private ArrayList<Joueur> joueurs;
    public String nom;
    private ArrayList<Carte> deckDealer;
    public PokerRules pokerRules;

    public Dealer() {
        this.joueurs = new ArrayList<Joueur>();
        this.deckDealer = new ArrayList<Carte>();
        this.pokerRules = new PokerRules();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Carte> getDeckDealer() {
        return deckDealer;
    }

    public void setDeckDealer(ArrayList<Carte> deckDealer) {
        this.deckDealer = deckDealer;
    }

    public void ajouter(Joueur joueur) {
        this.joueurs.add(joueur);

    }

    public void obtenirNouveauDeck() {
        ArrayList<Carte> newDeck = new ArrayList<Carte>();
        String[] figures = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
        Integer[] valeurs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        String[] couleurs = {"hearts", "diamonds", "spades", "clubs"};

        int valIndex = 0;
        for (String f : figures) {
            for (String c : couleurs) {
                newDeck.add(new Carte(f,f + " de " + c, valeurs[valIndex], f + "_of_" + c + ".png", f));
            }
            valIndex++;
        }
        Collections.shuffle(newDeck);
        this.deckDealer = newDeck;

    }

    public ArrayList<Carte> tirer5CartesPlateau() {

        ArrayList<Carte> fiveCardsPlateau = new ArrayList<Carte>();
        for (int i = 0; i < 5; i++) {
            fiveCardsPlateau.add(this.deckDealer.get(1));
            this.deckDealer.remove(1);
        }
        return fiveCardsPlateau;

    }

    public ArrayList<Carte> tirer2CartesJoueur() {
        ArrayList<Carte> twoCardsPlayer = new ArrayList<Carte>();
        for (int i = 0; i < 2; i++) {
            twoCardsPlayer.add(this.deckDealer.get(1));
            this.deckDealer.remove(1);
        }
        return twoCardsPlayer;
    }

    public void distribuerCartes(HashMap<Socket, Joueur> listeJoueurs) {

        /* Distribution des cartes plateau*/
        ArrayList<Carte> cartesPlateau = this.tirer5CartesPlateau();
        Iterator it = listeJoueurs.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                ((Joueur) pair.getValue()).setCartesPlateau(cartesPlateau);
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de lenvoi des cartes plateau aux joueurs cote client ...");
                System.out.println(e.getMessage());
            }
        }
        /* Distribution des cartes en main joueurs*/
        it = listeJoueurs.entrySet().iterator();

        while (it.hasNext()) {
            ArrayList<Carte> cartesJoueur = this.tirer2CartesJoueur();
            Map.Entry pair = (Map.Entry) it.next();
            try {
                ((Joueur) pair.getValue()).setMainJoueur(cartesJoueur);
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue la distribution des cartes main aux joueurs ...");
                System.out.println(e.getMessage());
            }
        }
    }

    public void definirMeilleurCombiAChaqueJoueur(HashMap<Socket, Joueur> listeJoueurs) {
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {                
                Joueur unJoueur = new Joueur("");
                unJoueur = ((Joueur) pair.getValue());
                this.pokerRules.setCarteJoueur(unJoueur.getMainJoueur());
                this.pokerRules.setCartePlateau(unJoueur.getCartesPlateau());                
                this.pokerRules.definirMeilleureCombinaisonJoueur(((Joueur) pair.getValue()));  
                
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue pour definir la meilleure combi...");
                System.out.println(e.getMessage());
            }
        }
    }

    public Joueur definirGagnant(HashMap<Socket, Joueur> listeJoueurs) {
        
        HashMap<Joueur,Integer> joueurScoreCombinaison = new HashMap<Joueur,Integer>();
        ArrayList<Joueur> tableauJoueurGagnant = new ArrayList<Joueur>();
        
        Joueur joueurGagnant = new Joueur("noname");
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                
                joueurScoreCombinaison.put(((Joueur) pair.getValue()),((Joueur) pair.getValue()).getBestCombi().getValeur());
                 
                // a enlever après modif
                if (((Joueur) pair.getValue()).getBestCombi().getValeur() > joueurGagnant.getBestCombi().getValeur()) {
                    joueurGagnant = ((Joueur) pair.getValue());
                }

            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la determination du joueur gagnant...");
                System.out.println(e.getMessage());
            }
        }     
        System.out.println("*****************************************\nHashMap Joueur => Valeur Combi: "+joueurScoreCombinaison);
        int valeurMaxDansMap=(Collections.max(joueurScoreCombinaison.values())); 
        System.out.println("Valeur Max dans map: "+valeurMaxDansMap);
        for (Map.Entry<Joueur,Integer> uneEntre : joueurScoreCombinaison.entrySet()) {  
            if (uneEntre.getValue()==valeurMaxDansMap) {
                tableauJoueurGagnant.add(uneEntre.getKey());
            }
        }
        System.out.println("Joueur gagnant: "+tableauJoueurGagnant+"\n ************************************************");
        
        if (tableauJoueurGagnant.size() > 1){
           // Lance la fonction en cas d'égalité de combinaison
           joueurGagnant = this.pokerRules.definirMeilleureCombinaisonJoueurSiEgalite(tableauJoueurGagnant);
           // Recupere le joueur gagnant
        }
        joueurGagnant = tableauJoueurGagnant.get(0);
        return joueurGagnant;
    }

    public void donnerMisesAuGagantDuTour(HashMap<Socket, Joueur> listeJoueurs, Joueur gagnantDuTour) {        
        Integer sommeDesMises = 0;
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                sommeDesMises += ((Joueur) pair.getValue()).getMise();
                ((Joueur) pair.getValue()).setMise(0);
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la distribution des gains au joueur gagnant...");
                System.out.println(e.getMessage());
            }
        }      
        gagnantDuTour.setStack(gagnantDuTour.getStack() + sommeDesMises); 
        it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                if (((Joueur) pair.getValue()).getNom().equals(gagnantDuTour.getNom())){ 
                listeJoueurs.replace(((Socket) pair.getKey()), gagnantDuTour);
                }
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la distribution des gains au joueur gagnant...");
                System.out.println(e.getMessage());
            }
        }
    }

    public void redistribuerLesMises(HashMap<Socket, Joueur> listeJoueurs) {       
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                ((Joueur) pair.getValue()).setStack(((Joueur) pair.getValue()).getStack() + ((Joueur) pair.getValue()).getMise());
                ((Joueur) pair.getValue()).setMise(0);

            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la redistribution des mises aux joueurs");
                System.out.println(e.getMessage());
            }
        }
    }
    
        public void determiner_premier_a_miser (HashMap<Socket, Joueur> listeJoueurs) {
            Iterator it = listeJoueurs.entrySet().iterator();            
            boolean premier_joueur_a_miser_found = false;
            while (it.hasNext()) {
            
            Map.Entry pair = (Map.Entry) it.next();
            try {                
                if (premier_joueur_a_miser_found == true){                   
                    ((Joueur) pair.getValue()).setPremier_a_miser(true);
                    ((Joueur) pair.getValue()).setInfopartie("C'est à vous de miser, " + ((Joueur) pair.getValue()).getNom() );
                    System.out.println("mon info partie est : " + ((Joueur) pair.getValue()).getInfoPartie() );                    
                    return;
                };
                
                if( ((Joueur) pair.getValue()).getPremier_a_miser() == true){
                premier_joueur_a_miser_found = true;
                ((Joueur) pair.getValue()).setPremier_a_miser(false);
                ((Joueur) pair.getValue()).setInfopartie("Ce n'est pas à votre tour de miser. Veuillez patienter");
                
                    if(!(it.hasNext())){ 
                        definirPremierDeLaListeCommePremierAMiser(listeJoueurs);
                    }
                };
     

            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la determination du premier joueur à miser");
                System.out.println(e.getMessage());
            }
        }
        
        if(premier_joueur_a_miser_found == false){            
            it = listeJoueurs.entrySet().iterator();
            Map.Entry firstPlayer = (Map.Entry) it.next();
            ((Joueur) firstPlayer.getValue()).setPremier_a_miser(true); 
            ((Joueur) firstPlayer.getValue()).setInfopartie("c'est à vous de miser, " + ((Joueur) firstPlayer.getValue()).getNom() );
        
        }else {
        
            premier_joueur_a_miser_found = false;
        }
            
    
    }
        public void definirPremierDeLaListeCommePremierAMiser (HashMap<Socket, Joueur> listeJoueurs) {            
            Iterator it = listeJoueurs.entrySet().iterator();
            Map.Entry firstPlayer = (Map.Entry) it.next();
            ((Joueur) firstPlayer.getValue()).setPremier_a_miser(true); 
            ((Joueur) firstPlayer.getValue()).setInfopartie("c'est à vous de miser, " + ((Joueur) firstPlayer.getValue()).getNom() );
        
        
        }
        
        public void reinitialiser(HashMap<Socket, Joueur> listeJoueurs) {
            System.out.println("SERVEUR : Reinitialisation des parametres");
            Iterator it = listeJoueurs.entrySet().iterator();
           
        while (it.hasNext()) {
            
            Map.Entry pair = (Map.Entry) it.next();
            try {                
                    ((Joueur) pair.getValue()).setMise(0);
                    ((Joueur) pair.getValue()).setMiseMinimum(0);                   
                   
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la reinitialisation");
                System.out.println(e.getMessage());
            }
        }
          
          }

    @Override
    public String toString() {

        return this.nom;
    }

}
