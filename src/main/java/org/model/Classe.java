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
    public ArrayList<Utilisateur> eleves;

    public static void main(String[] args) {
        Classe classe = new Classe(1);
        System.out.println("Ecole : " + classe.ecole.getNom());
        System.out.println("Classe : " + classe.getNom());

        Iterator<Utilisateur> iter = classe.eleves.iterator();
        System.out.println("### Eleves ###");
        while (iter.hasNext()) {
            Utilisateur util = iter.next();
            System.out.println();
            System.out.println("Nom : " + util.getNom());
            System.out.println("Prenom : " + util.getPrenom());
            System.out.println("Mail : " + util.getMail());
            System.out.println();
        }

        classe.addPresence(new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()));
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Ecole getEcole() {
        return ecole;
    }

    private void initUtilisateursList() {
        Connection db = this.getConn();
        try {
            PreparedStatement utilisateurs = db.prepareStatement("SELECT ID_Utilisateurs FROM Composition_classes WHERE ID = ?");
            utilisateurs.setInt(1, this.id);
            ResultSet utilisateur = utilisateurs.executeQuery();
            this.eleves = new ArrayList<Utilisateur>();
            while(utilisateur.next()) {
                this.eleves.add(new Utilisateur(utilisateur.getInt("ID_Utilisateurs")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // CONSTRUCTEUR
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

            this.initUtilisateursList();

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

            this.initUtilisateursList();
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
