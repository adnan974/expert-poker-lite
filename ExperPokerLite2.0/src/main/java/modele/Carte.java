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
public class Carte implements Serializable{

    public long id;
    private String figure;
    private String nom;
    private Integer valeur;
    private String cheminImage;
    private String forme;
    //    public Image image;

    public Carte(String figure, String nom, Integer valeur, String cheminImage, String forme) {
        this.figure = figure;
        this.nom = nom;
        this.valeur = valeur;
        this.cheminImage = cheminImage;
        this.forme = forme;
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }
    
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

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }

    @Override
    public String toString(){

        return this.nom;
    }
}
