package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ecole extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;

    public ArrayList<Classe> classe;

    // CONSTRUCTEUR
    public Ecole(int id) {
        super();
        this.classe = new ArrayList<Classe>();
        Connection db = this.getConn();
        try {
            PreparedStatement ecole = db.prepareStatement("SELECT Nom FROM Ecoles WHERE ID = ?");
            ecole.setInt(1, id);
            ResultSet rs = ecole.executeQuery();
            rs.next();
            this.id = id;
            this.nom = rs.getString("Nom");

            this.initClassesList();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initClassesList() {
        Connection db = this.getConn();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT ID FROM Classes WHERE ID_Ecoles = ?");
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                this.classe.add(new Classe(rs.getInt("ID")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
}
