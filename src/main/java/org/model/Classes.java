package org.model;

import java.util.List;

public class Classes extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private int ecole;

    // CONSTRUCTEUR
    public Classes(int id, String nom, int ecole) {
        this.id = id;
        this.nom = nom;
        this.ecole = ecole;
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEcole() {
        return ecole;
    }

    public void setEcole(int ecole) {
        this.ecole = ecole;
    }
}
