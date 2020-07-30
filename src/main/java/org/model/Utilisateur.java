package org.model;

public class Utilisateur extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;

    private int id_groupe;
    private int id_ecole;

    // CONSTRUCTEUR
    public Utilisateur(int id, String nom, String prenom, String mail, String mdp, int id_groupe, int id_ecole) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.id_groupe = id_groupe;
        this.id_ecole = id_ecole;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    public int getId_ecole() {
        return id_ecole;
    }

    public void setId_ecole(int id_ecole) {
        this.id_ecole = id_ecole;
    }

    // METHODES
    public void addUtilisateur() {

    }

    public void modifUtilisateur() {

    }

    public void deleteUtilisateur() {

    }
}