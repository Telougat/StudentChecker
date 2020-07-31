package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Presence extends DB {

    // ATTRIBUTS
    private int id;
    private Date date_debut;
    private Date date_fin;

    public Classe classe;
    public ArrayList<PresenceUtilisateur> presencesUtilisateurs;

    // CONSTRUCTEUR
    public Presence(int id) {
        super();
        Connection db = this.getConn();
        try {
            PreparedStatement presence = db.prepareStatement("SELECT p.Debut, p.Fin, pc.ID_Classes FROM Presences AS p INNER JOIN Presence_classe as pc ON pc.ID = p.ID WHERE p.ID = ?");
            presence.setInt(1, id);
            ResultSet rs = presence.executeQuery();
            rs.next();
            this.id = id;
            this.date_debut = rs.getDate("Debut");
            this.date_fin = rs.getDate("Fin");
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

    public Date getDateDebut() {
        return date_debut;
    }

    public Date getDateFin() {
        return date_fin;
    }
}
