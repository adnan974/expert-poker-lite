/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author borri
 */
public class Joueur extends Observable implements Serializable {

    private long id;
    private String nom;
    private ArrayList<Carte> mainJoueur;
    private ArrayList<Carte> cartesPlateau;
    private Integer score;
    private Integer stack;
    private Integer mise;
        private Integer miseMinimum;
    private Combinaison bestCombi;
    public String testServeur;
    public boolean premier_a_miser;
    public String infoPartie;

    public ArrayList<Carte> getMainJoueur() {
        return mainJoueur;
    }

    public void setMainJoueur(ArrayList<Carte> mainJoueur) {
        this.mainJoueur = mainJoueur;
    }

    public ArrayList<Carte> getCartesPlateau() {
        return cartesPlateau;
    }

    public void setCartesPlateau(ArrayList<Carte> cartesPlateau) {
        this.cartesPlateau = cartesPlateau;
    }

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }

    public Integer getMise() {
        return mise;
    }

    public void setMise(Integer mise) {
        this.mise = mise;
    }
        public void setMiseMinimum(Integer mise) {
        this.miseMinimum = mise;
    }
    
        public Integer getMiseMinimum() {
        return miseMinimum;
    }

    public Combinaison getBestCombi() {
        return bestCombi;
    }

    public void setBestCombi(Combinaison bestCombi) {
        this.bestCombi = bestCombi;

    }

    public Joueur(String nom) {
        this.nom = nom;
        this.bestCombi = new Combinaison(null, 0);
        this.mainJoueur = new ArrayList<Carte>();
        this.cartesPlateau = new ArrayList<Carte>();
        this.score = 0;
        this.stack = 1000;
        this.mise = 0;
        this.miseMinimum = 0;
        this.testServeur = "TEST CLIENT-SERVEUR : " + this.nom;
        this.premier_a_miser = false;
        this.infoPartie = "";
    }
    
        public boolean getPremier_a_miser() {
        return premier_a_miser;
    }

    public void setPremier_a_miser(boolean premier_a_miser) {
        this.premier_a_miser = premier_a_miser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;

    }

    public void ajouterCarte(Carte carte) {
        this.mainJoueur.add(carte);
    }
    
        public String getInfoPartie() {
        return this.infoPartie;
    }

    public void setInfopartie(String info) {
        this.infoPartie = info;
    }
    
    public void changerStatutJoueur(Joueur newEtatJoueur){
    
        this.setCartesPlateau(newEtatJoueur.getCartesPlateau());
        this.setMainJoueur(newEtatJoueur.getMainJoueur());
        this.setStack(newEtatJoueur.getStack());
        this.setBestCombi(newEtatJoueur.getBestCombi());
        this.setScore(newEtatJoueur.getScore());
        this.setMise(newEtatJoueur.getMise());
        this.setPremier_a_miser(newEtatJoueur.getPremier_a_miser());
        this.setMiseMinimum(newEtatJoueur.getMiseMinimum());
        this.setInfopartie(newEtatJoueur.getInfoPartie());
        this.setChanged();
        this.notifyObservers();

    }

    @Override
    public String toString() {

        return this.nom;
    }
}
