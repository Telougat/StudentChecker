package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDUtilisateur extends DB {

    public boolean createUtilisateur(String nom, String prenom, String mail, String mdp, Groupe groupe, Ecole ecole) {
        try {
            Connection db = this.getConn();
            PreparedStatement ps = db.prepareStatement("INSERT INTO Utilisateurs(Nom, Prenom, Mail, Mot_de_passe, ID_Groupe, ID_Ecoles) VALUES (? , ? , ? , ? , ? , ?)");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, mail);
            ps.setString(4, mdp);
            ps.setInt(5, groupe.getId());
            ps.setInt(6, ecole.getId());
            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public boolean removeFromClasse(Utilisateur utilisateur, Classe classe) {
        try {
            Connection db = this.getConn();

            PreparedStatement ps = db.prepareStatement("DELETE FROM Composition_classes WHERE ID = ? AND ID_Utilisateurs = ?");
            ps.setInt(1, classe.getId());
            ps.setInt(2, utilisateur.getId());

            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public boolean linkToClasse(Utilisateur utilisateur, Classe classe) {
        try {
            Connection db = this.getConn();

            PreparedStatement ps = db.prepareStatement("INSERT INTO Composition_classes(ID, ID_Utilisateurs) VALUES (? , ?)");
            ps.setInt(1, classe.getId());
            ps.setInt(2, utilisateur.getId());
            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public boolean updateUtilisateur(Utilisateur utilisateur, String nom, String prenom, String mail, String mdp, Ecole ecole) {
        try {
            Connection db = this.getConn();
            PreparedStatement ps = db.prepareStatement("UPDATE Utilisateurs SET Nom = ?, Prenom = ?, Mail = ?, Mot_depasse = ?, ID_Ecoles = ? WHERE ID = ?");
            ps.setString(1, nom);
            ps.setString(2, prenom);
            ps.setString(3, mail);
            ps.setString(4, mdp);
            ps.setInt(5, ecole.getId());
            ps.setInt(6, utilisateur.getId());
            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public boolean deleteUtilisateur(Utilisateur utilisateur) {
        try {
            Connection db = this.getConn();

            PreparedStatement ps = db.prepareStatement("DELETE FROM Utilisateurs WHERE ID = ?");
            ps.setInt(1, utilisateur.getId());
            int rows = ps.executeUpdate();

            PreparedStatement dPresence = db.prepareStatement("DELETE FROM Presence_utilisateur WHERE ID = ?");
            dPresence.setInt(1, utilisateur.getId());
            dPresence.executeUpdate();

            PreparedStatement dClasse = db.prepareStatement("DELETE FROM Composition_classes WHERE ID = ?");
            dClasse.setInt(1, utilisateur.getId());
            dClasse.executeUpdate();

            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

}
