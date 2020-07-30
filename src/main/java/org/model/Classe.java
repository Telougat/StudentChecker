package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Classe extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private Ecole ecole;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Ecole getEcole() {
        return ecole;
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
            System.out.println("ID = " + this.id + " nom = " + this.nom + " ecole = " + this.ecole.getNom());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Classe classe = new Classe(1);
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

    public int getEcole() {
        return ecole;
    }

    public void setEcole(int ecole) {
        this.ecole = ecole;
    }
}
