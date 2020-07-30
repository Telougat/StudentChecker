package org.model;

public class Ecole extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;

    // CONSTRUCTEUR
    public Ecole(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Ecole(int id, String nom) {
        this.id = id;
        this.nom = nom;
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
}
