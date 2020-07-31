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
    public Classe classe;
    public Ecole ecole;


    // CONSTRUCTEUR
    public Utilisateur(int id, String nom, String prenom, String mail, Classe classe, int gId, String gLabel, Ecole ecole) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.classe = classe;
        this.groupe = new Groupe(gId, gLabel);
        this.ecole = ecole;
    }

    public Utilisateur(int id, String nom, String prenom, String mail, int gId, String gLabel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.groupe = new Groupe(gId, gLabel);
    }

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

    public boolean declarePresence(int idPresence) {
        boolean rtn = false;
        if (this.classe == null) {
            return rtn;
        }

        for (Presence presence : this.classe.presences) {
            if (presence.getId() == idPresence) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                Timestamp debut = presence.getDateDebut();
                Timestamp fin = presence.getDateFin();

                String status = null;
                if (current.after(fin)) {
                    status = "Absent";
                } else if (current.after(debut) && current.before(fin)) {
                    status = "Retard";
                } else {
                    status = "Présent";
                }

                Connection bd = this.getConn();
                try {
                    PreparedStatement ps = bd.prepareStatement("UPDATE Presence_utilisateur SET Status = ?, Date = ? " +
                            "WHERE ID = ? AND ID_Presences = ?");
                    ps.setString(1, status);
                    ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    ps.setInt(3, this.id);
                    ps.setInt(4, presence.getId());
                    if (ps.executeUpdate() > 0) {
                        rtn=true;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return rtn;
    }

    public PresenceUtilisateur getUndeclaredPresence() {
        Connection db = this.getConn();
        PresenceUtilisateur presence = null;
        try {
            PreparedStatement ps = db.prepareStatement("SELECT ID, ID_Presences FROM Presence_utilisateur WHERE ID = ? AND Status = 'En attente' LIMIT 1");
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                presence = new PresenceUtilisateur(rs.getInt("ID_Presences"), rs.getInt("ID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return presence;
    }

    public ArrayList<PresenceUtilisateur> getUndeclaredPresences() {
        Connection db = this.getConn();
        ArrayList<PresenceUtilisateur> presences = new ArrayList<PresenceUtilisateur>();
        try {
            PreparedStatement ps = db.prepareStatement("SELECT ID, ID_Presences FROM Presence_utilisateur WHERE ID = ? AND Status = 'En attente'");
            ps.setInt(1, this.id);
            ResultSet rs = ps.executeQuery();
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
}