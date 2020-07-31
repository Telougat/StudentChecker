package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDClasse extends DB {

    public boolean createClass(String nom, Ecole ecole) {
        try {
            Connection db = this.getConn();

            PreparedStatement ps = db.prepareStatement("INSERT INTO Classes(Nom, Id_Ecoles) VALUES (? , ?)");
            ps.setString(1, nom);
            ps.setInt(2, ecole.getId());
            int rows = ps.executeUpdate();
            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public boolean deleteClasse(Classe classe) {
        try {
            Connection db = this.getConn();

            PreparedStatement ps = db.prepareStatement("DELETE FROM Classes WHERE ID = ?");
            ps.setInt(1, classe.getId());
            int rows = ps.executeUpdate();

            PreparedStatement dUsers = db.prepareStatement("DELETE FROM Composition_classes WHERE ID = ?");
            dUsers.setInt(1, classe.getId());
            dUsers.executeUpdate();

            if (rows <= 0) {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
