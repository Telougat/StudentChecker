package org.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Presence extends DB {

    // ATTRIBUTS
    private int id;
    private Timestamp date_debut;
    private Timestamp date_fin;

    public Classe classe;
    public ArrayList<PresenceUtilisateur> presencesUtilisateurs;

    // CONSTRUCTEUR

    public Presence (int id, Timestamp debut, Timestamp fin) {
        this.id = id;
        this.date_debut = debut;
        this.date_fin = fin;
    }

    public Presence(int id) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement presence = db.prepareStatement("SELECT p.Debut, p.Fin, pc.ID_Classes FROM Presences AS p INNER JOIN Presence_classe as pc ON pc.ID = p.ID WHERE p.ID = ?");
            presence.setInt(1, id);
            ResultSet rs = presence.executeQuery();
            rs.next();
            this.id = id;
            this.date_debut = rs.getTimestamp("Debut");
            this.date_fin = rs.getTimestamp("Fin");
            this.classe = new Classe(rs.getInt("ID_Classes"));

            PreparedStatement getPresenceUtilisateur = db.prepareStatement("SELECT ID, ID_Presences FROM Presence_utilisateur WHERE ID_Presences = ?");
            getPresenceUtilisateur.setInt(1, id);
            ResultSet list = getPresenceUtilisateur.executeQuery();

            this.presencesUtilisateurs = new ArrayList<PresenceUtilisateur>();

            while (rs.next()) {
                this.presencesUtilisateurs.add(new PresenceUtilisateur(rs.getInt("ID"), rs.getInt("ID_Presences")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // GETTER ET SETTER
    public int getId() {
        return id;
    }

    public Timestamp getDateDebut() {
        return date_debut;
    }

    public Timestamp getDateFin() {
        return date_fin;
    }
}
