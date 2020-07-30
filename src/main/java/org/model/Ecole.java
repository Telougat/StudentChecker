package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ecole extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;

    // CONSTRUCTEUR
    public Ecole(int id) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement ecole = db.prepareStatement("SELECT Nom FROM Ecoles WHERE ID = ?");
            ecole.setInt(1, id);
            ResultSet rs = ecole.executeQuery();
            rs.next();
            this.id = id;
            this.nom = rs.getString("Nom");
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
}
