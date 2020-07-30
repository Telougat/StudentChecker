package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilisateur extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private String prenom;
    private String mail;

    private Groupe groupe;
    private Ecole ecole;

    // CONSTRUCTEUR
    public Utilisateur(Integer id) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement classe = db.prepareStatement("SELECT Nom, Prenom, Mail, ID_Groupe, ID_Ecoles FROM Utilisateurs WHERE ID = ?");
            classe.setInt(1, id);
            ResultSet rs = classe.executeQuery();
            rs.next();
            this.id = id;
            this.nom = rs.getString("Nom");
            this.prenom = rs.getString("Prenom");
            this.mail = rs.getString("Mail");
            this.groupe = new Groupe(rs.getInt("ID_Groupe"));
            this.ecole = new Ecole(rs.getInt("ID_Ecoles"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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