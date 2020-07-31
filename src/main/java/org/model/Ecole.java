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

    public Ecole(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
}
