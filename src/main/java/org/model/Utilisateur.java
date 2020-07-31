package org.model;

import java.sql.*;
import java.util.ArrayList;

public class Utilisateur extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private String prenom;
    private String mail;

    public Groupe groupe;
    public Ecole ecole;

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

    public ArrayList<PresenceUtilisateur> getUndeclaredPresences() {
        Connection db = this.getConn();
        ArrayList<PresenceUtilisateur> presences = new ArrayList<PresenceUtilisateur>();
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID, ID_Presences FROM Presence_utilisateur WHERE Status = `En attente`");

            while (rs.next()) {
                presences.add(new PresenceUtilisateur(rs.getInt("ID_Presences"), rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return presences;
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    // METHODES
    public void addUtilisateur() {

    }

    public void modifUtilisateur() {

    }

    public void deleteUtilisateur() {

    }
}