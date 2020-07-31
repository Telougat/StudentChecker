package org.model;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Classe extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;

    public Ecole ecole;
    public ArrayList<Presence> presences;
    public ArrayList<Utilisateur> eleves;

    // CONSTRUCTEUR
    public Classe(int id, String nom, Ecole ecole) {
        this.id = id;
        this.nom = nom;
        this.ecole = ecole;
        this.generateUtilisateursList();
    }

    public Classe(Integer id) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement classe = db.prepareStatement("SELECT Nom, ID_Ecoles FROM Classes WHERE ID = ?");
            classe.setInt(1, id);
            ResultSet rs = classe.executeQuery();
            rs.next();
            this.id = id;
            this.nom = rs.getString("Nom");
            this.ecole = new Ecole(rs.getInt("ID_Ecoles"));

            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Classe (Utilisateur utilisateur) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT c.ID, c.Nom, c.ID_Ecoles FROM Classes AS c INNER JOIN Composition_classes AS cc ON cc.ID = c.ID WHERE cc.ID_Utilisateurs = ?");
            ps.setInt(1, utilisateur.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.id = rs.getInt("ID");
            this.nom = rs.getString("Nom");
            this.ecole = new Ecole(rs.getInt("ID_Ecoles"));

            this.generateUtilisateursList();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<Utilisateur> getUtilisateursListByClasseName(String nom) {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        Connection db = new DB().getConn();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT u.ID as uId, u.Nom as uNom, u.Prenom as uPrenom, u.Mail as uMail, g.ID as gId, g.Label as gLabel FROM Utilisateurs as u " +
                    "INNER JOIN Composition_classes as cc ON cc.ID_Utilisateurs = u.ID " +
                    "INNER JOIN Classes as c ON c.ID = cc.ID " +
                    "INNER JOIN Groupe as g ON g.ID = u.ID_Groupe " +
                    "WHERE c.Nom = ? GROUP BY u.ID");

            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int uId = rs.getInt("uId");
                String uNom = rs.getString("uNom");
                String prenom = rs.getString("uPrenom");
                String mail = rs.getString("uMail");
                int gId = rs.getInt("gId");
                String gLabel = rs.getString("gLabel");
                utilisateurs.add(new Utilisateur(uId, uNom, prenom, mail, gId, gLabel));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return utilisateurs;
    }

    public void generateUtilisateursList() {
        Connection db = this.getConn();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT u.ID as uId, u.Nom as uNom, u.Prenom as uPrenom, u.Mail as uMail, g.ID as gId, g.Label as gLabel FROM Utilisateurs as u " +
                    "INNER JOIN Composition_classes as cc ON cc.ID_Utilisateurs = u.ID AND cc.ID = ? " +
                    "INNER JOIN Groupe as g ON g.ID = u.ID_Groupe " +
                    "GROUP BY u.ID");
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();
            this.eleves = new ArrayList<Utilisateur>();
            while (rs.next()) {
                int uId = rs.getInt("uId");
                String nom = rs.getString("uNom");
                String prenom = rs.getString("uPrenom");
                String mail = rs.getString("uMail");
                int gId = rs.getInt("gId");
                String gLabel = rs.getString("gLabel");
                Utilisateur user = new Utilisateur(uId, nom, prenom, mail, this, gId, gLabel, this.ecole);
                this.eleves.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addPresence(Timestamp debut, Timestamp fin) {
        Connection db = this.getConn();
        try {
            PreparedStatement ps = db.prepareStatement("INSERT INTO Presences(Debut, Fin) VALUES (? , ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, debut);
            ps.setTimestamp(2, fin);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            PreparedStatement linkClasse = db.prepareStatement("INSERT INTO Presence_classe(ID, ID_Classes) VALUES (?, ?)");
            linkClasse.setInt(1, id);
            linkClasse.setInt(2, this.id);
            linkClasse.executeUpdate();

            addUtilisateursToPresence(id);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private void addUtilisateursToPresence(int id) {
        Connection db = this.getConn();
        try {
            for (Utilisateur user : this.eleves) {
                PreparedStatement ps = db.prepareStatement("INSERT INTO Presence_utilisateur(ID, ID_Presences, Status) VALUES (? , ? , ?)");
                ps.setInt(1, user.getId());
                ps.setInt(2, id);
                ps.setString(3, "En attente");
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }
}
