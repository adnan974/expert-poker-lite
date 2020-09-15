/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.io.Serializable;

/**
 *
 * @author borri
 */
public class Combinaison implements Serializable{

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getValeur() {
        return valeur;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public Combinaison(String nom, Integer valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }
    
    private String nom;
    private Integer valeur;
    
}
