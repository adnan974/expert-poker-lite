/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import modele.Serveur;

/**
 *
 * @author borri
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("SERVEUR : Bienvenue sur le serveur Java ExperPoker Lite !");
        Serveur s = new Serveur();
      //  while (s.isRunning) {
            
            while (s.listeJoueurs.size() != 2) {
                s.waitForPlayers();
            }
            s.playGame();
      //  }        
    }
    //test
}

