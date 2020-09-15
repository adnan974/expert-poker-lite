/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author borri
 */
public class Serveur {

    private String nomServeur;
    public Dealer dealer;
    public HashMap<Socket, Joueur> listeJoueurs;
    private ServerSocket serverSock;
    public boolean isRunning;
    public boolean partieEnCours;

    public Serveur() {

        this.dealer = new Dealer();
        this.listeJoueurs = new HashMap<Socket, Joueur>();
 

        try {
            this.serverSock = new ServerSocket(4444);
            this.isRunning = true;
            this.partieEnCours = true;
            System.out.println("SERVEUR : En ecoute sur port 4444...");
        } catch (Exception e) {
            System.out.println("SERVEUR ERREUR : Echec lors du démarrage du serveur");
        };

    }

    public void waitForPlayers() {

        // System.out.println("SERVEUR : En attente de joueurs...");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (listeJoueurs.size() != 2) {

                        Socket client = serverSock.accept();
                        System.out.println("SERVEUR : un client s'est connecté ! ...");
                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        Object objetRecu = ois.readObject();
                        System.out.println("SERVEUR : Je recois un objet");
                        if (objetRecu instanceof Joueur) {
                            System.out.println("SERVEUR : l'objet recu est un Joueur");
                            Joueur joueur = (Joueur) objetRecu;
                            System.out.println("SERVEUR INFO : infos du joueur : ");
                            System.out.println("NOM : " + joueur.getNom());
                            System.out.println("INFO PARTIE : " + joueur.getInfoPartie());
                            if (listeJoueurs.size() < 2) {
                                listeJoueurs.put(client, joueur);
                            }

                            Iterator it = listeJoueurs.entrySet().iterator();

                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                System.out.println(pair.getKey() + " = " + pair.getValue());
                            }

                        };
                    }
                } catch (Exception e) {
                    System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la connexion client...");
                    System.out.println(e.getMessage());
                }
            }
        });
        t.start();

    }
    
    public void waitForNewStatusJoueurs() {

        System.out.println("SERVEUR : Attente de nouveaux statuts joueurs...");
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                ObjectInputStream ois = new ObjectInputStream(((Socket) pair.getKey()).getInputStream());
                Object objetRecu = ois.readObject();
                System.out.println("SERVEUR : reception d'un objet !");
                if (objetRecu instanceof Joueur) {
                    System.out.println("SERVEUR : l'objet est un joueur !");
                    Joueur joueur = (Joueur) objetRecu;
                     System.out.println("SERVEUR INFO : infos du joueur : ");
                     System.out.println("NOM : " + joueur.getNom());
                     System.out.println("INFO PARTIE : " + joueur.getInfoPartie());

                    listeJoueurs.replace(((Socket) pair.getKey()), joueur);                  
                   
                }

            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la reception d'objets client...");
                System.out.println(e.getMessage());
            }
        }
    }

    public void waitForFirstMise() {
int miseMini = 0 ;
String joueurMisemini = "";
String infoPartie = "ooo";
        System.out.println("SERVEUR : Attente de la premiere mise ...");
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {            
            Map.Entry pair = (Map.Entry) it.next();            
            try {
                
                if( ((Joueur) pair.getValue()).getPremier_a_miser() == true){
                    System.out.println("SERVEUR : Attente de la mise de  " + ((Joueur) pair.getValue()).getNom());
                ObjectInputStream ois = new ObjectInputStream(((Socket) pair.getKey()).getInputStream());
                Object objetRecu = ois.readObject();
                System.out.println("SERVEUR : reception d'un objet !");
                if (objetRecu instanceof Joueur) {
                    System.out.println("SERVEUR : l'objet est un joueur !");
                    System.out.println("SERVEUR : je recois la mise de  " + ((Joueur) pair.getValue()).getNom() + " qui est de " + ((Joueur) pair.getValue()).getMise() );
                    Joueur joueur = (Joueur) objetRecu;
                    miseMini = joueur.getMise();
                    joueur.setMiseMinimum(miseMini);
                    joueurMisemini = joueur.getNom();
                    infoPartie = joueurMisemini + ", vous avez misé " + miseMini + " !";
                    joueur.setInfopartie(infoPartie);
                    System.out.println("SERVEUR : " + infoPartie );
                    listeJoueurs.replace(((Socket) pair.getKey()), joueur);                    
                }
               
                };
                
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la reception d'objets client...");
                System.out.println(e.getMessage());
            }
               }
            it = listeJoueurs.entrySet().iterator(); 
            
            
                    while (it.hasNext()) {
            Map.Entry pair  = (Map.Entry) it.next();
            try {
                
                if( ((Joueur) pair.getValue()).getMiseMinimum() != miseMini){
                infoPartie = joueurMisemini + " a misé " + miseMini + ". A vous de jouer, " +((Joueur) pair.getValue()).getNom() + " !";
                ((Joueur) pair.getValue()).setMiseMinimum(miseMini);
                ((Joueur) pair.getValue()).setInfopartie(infoPartie);              
                }
               
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la reception d'objets client...");
                System.out.println(e.getMessage());
            }
        }
     
    }
    
    public void waitForOtherPlayers() {

        System.out.println("SERVEUR : Attente des mises des autres joueurs...");
        Iterator it = listeJoueurs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                if (((Joueur) pair.getValue()).getMise() == 0){
                
                
                ObjectInputStream ois = new ObjectInputStream(((Socket) pair.getKey()).getInputStream());
                Object objetRecu = ois.readObject();
                System.out.println("SERVEUR : reception d'un objet !");
                if (objetRecu instanceof Joueur) {
                    System.out.println("SERVEUR : l'objet est un joueur !");
                    Joueur joueur = (Joueur) objetRecu;

                    listeJoueurs.replace(((Socket) pair.getKey()), joueur);                  
                    //pair.getValue() = joueur;
                }
                }
            } catch (Exception e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de la reception d'objets client...");
                System.out.println(e.getMessage());
            }
            
        }
    }

    public void sendPlayersStatusToClients() {
        System.out.println("SERVEUR : début envoi statut joueurs aux clients");        
        Iterator it = listeJoueurs.entrySet().iterator();
        /* Envoi des cartes plateau*/
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(((Socket) pair.getKey()).getOutputStream());
                oos.writeObject(((Joueur) pair.getValue()));
                System.out.println(((Joueur) pair.getValue()).getNom());
                oos.flush();
            } catch (IOException e) {
                System.out.println("SERVEUR ERREUR : une erreur est survenue lors de lenvoi de l'etat joueurs aux clients ...");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("SERVEUR : fin envoi statut joueurs aux clients");
     
    }

    public void playGame() {
        Integer tour = 1 ;
        boolean partie_terminee = false;
        System.out.println("SERVEUR : *** début du tour " + tour);
        while (partie_terminee == false) {           
            Joueur gagnantDuTour = null;
            this.dealer.obtenirNouveauDeck();
            this.dealer.distribuerCartes(this.listeJoueurs);
            this.dealer.definirMeilleurCombiAChaqueJoueur(this.listeJoueurs);
            this.dealer.determiner_premier_a_miser(this.listeJoueurs);            
            this.sendPlayersStatusToClients();
            
            this.waitForFirstMise();
            this.sendPlayersStatusToClients();
            
            this.waitForOtherPlayers();
            
            gagnantDuTour = this.dealer.definirGagnant(listeJoueurs);
            if (gagnantDuTour != null) {
                dealer.donnerMisesAuGagantDuTour(this.listeJoueurs, gagnantDuTour);
            } else {
                dealer.redistribuerLesMises(this.listeJoueurs);
            }
            this.sendPlayersStatusToClients();
             this.dealer.reinitialiser(listeJoueurs);

         
           
        
        System.out.println("SERVEUR : *** fin du tour " + tour);
         tour ++;
         }
    }
    

    


}
