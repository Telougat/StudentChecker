package org.model;

public class Classe extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private int ecole;

    // CONSTRUCTEUR
    public Classe(Integer id) {
        super();
        this.getConn();
    }

    public static void main(String[] args) {
        Classe classe = new Classe(1);
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
