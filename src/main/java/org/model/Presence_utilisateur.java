package org.model;

public class Presence_utilisateur extends DB {

    // ATTRIBUTS
    private int id_utlisateur;
    private int id_presence;
    private String status;

    // CONSTRUCTEUR
    public Presence_utilisateur(int id_utlisateur, int id_presence, String status) {
        this.id_utlisateur = id_utlisateur;
        this.id_presence = id_presence;
        this.status = status;
    }

    // GETTER ET SETTER
    public int getId_utlisateur() {
        return id_utlisateur;
    }

    public void setId_utlisateur(int id_utlisateur) {
        this.id_utlisateur = id_utlisateur;
    }

    public int getId_presence() {
        return id_presence;
    }

    public void setId_presence(int id_presence) {
        this.id_presence = id_presence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
