package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Classe extends DB {

    // ATTRIBUTS
    private int id;
    private String nom;
    private Ecole ecole;
    private ArrayList<Utilisateur> eleves;

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
            // Get all users of classe
            classe = db.prepareStatement("SELECT ID_Utilisateurs FROM Composition_classes WHERE ID = ?");
            rs = classe.executeQuery();
            while(rs.next()) {
                this.eleves.add(new Utilisateur(rs.getInt("ID_Utilisateurs")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }}
