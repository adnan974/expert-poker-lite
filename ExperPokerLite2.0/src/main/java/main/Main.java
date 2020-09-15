/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import initgame.InitGameFrame;
import javax.swing.UIManager;

/**
 *
 * @author borri
 */
public class Main {
    
        public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("on rentre dans le catch");
        }

       // Croupier croupier = new Croupier();
       // croupier.obtenirNouveauDeck();
       // System.out.println(croupier.getDeck().getListeCartes().size());

        InitGameFrame fp = new InitGameFrame();
        fp.setVisible(true);
   //     FenetrePrincipale fp = new FenetrePrincipale("Connexion") ;
    //    fp.add(panelconnexion);

    }
    
    
    
    
}
