package org.model;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            PreparedStatement utilisateurs = db.prepareStatement("SELECT ID_Utilisateurs FROM Composition_classes WHERE ID = ?");
            utilisateurs.setInt(1, id);
            ResultSet utilisateur = utilisateurs.executeQuery();
            this.eleves = new ArrayList<Utilisateur>();
            while(utilisateur.next()) {
                this.eleves.add(new Utilisateur(utilisateur.getInt("ID_Utilisateurs")));
            }
            db.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }}
