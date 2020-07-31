package org.model;

import java.sql.*;
import java.util.ArrayList;

public class PresenceUtilisateur extends DB {
    private String status;
    private Timestamp present;

    public Presence presence;
    public Utilisateur utilisateur;

    private void initPresence(int presence, int utilisateur) {
        Connection db = this.getConn();
        try {
            PreparedStatement presenceUtilisateur = db.prepareStatement("SELECT * FROM Presence_utilisateur WHERE ID = ? AND ID_Presences = ?");
            presenceUtilisateur.setInt(1, utilisateur);
            presenceUtilisateur.setInt(2, presence);
            ResultSet rs = presenceUtilisateur.executeQuery();
            rs.next();
            this.status = rs.getString("Status");
            this.present = rs.getTimestamp("Date");
            this.presence = new Presence(rs.getInt("ID_Presences"));
            this.utilisateur = new Utilisateur(rs.getInt("ID"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public PresenceUtilisateur(int presence, int utilisateur) {
        super();
        this.initPresence(presence, utilisateur);
    }

    public PresenceUtilisateur() {
        super();
    }

    /*public ArrayList<PresenceUtilisateur> getPresencesToDeclare() {
        Connection db = this.getConn();
        try {
            PreparedStatement ps = db.prepareStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }*/

    @Override
    public String toString() {
        return "Présence à déclarer avant le " + this.presence.getDateDebut() + " retard comptabilisé jusqu'au " + this.presence.getDateFin() + " au delà Absent";
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getPresent() {
        return present;
    }
}
