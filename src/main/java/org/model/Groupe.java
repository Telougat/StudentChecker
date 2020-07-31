package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Groupe extends DB {

    // ATTRIBUTS
    private int id;
    private String label;

    // CONSTRUCTEUR

    public Groupe(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public Groupe(int id) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement groupe = db.prepareStatement("SELECT Label FROM Groupe WHERE ID = ?");
            groupe.setInt(1, id);
            ResultSet rs = groupe.executeQuery();
            rs.next();
            this.id = id;
            this.label = rs.getString("Label");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Groupe getGroupByName(String nom) {
        Groupe grp = null;
        Connection db = new DB().getConn();
        try {
            PreparedStatement groupe = db.prepareStatement("SELECT ID FROM Groupe WHERE Label = ?");
            groupe.setString(1, nom);
            ResultSet rs = groupe.executeQuery();
            rs.next();
            grp = new Groupe(rs.getInt("ID"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return grp;
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
