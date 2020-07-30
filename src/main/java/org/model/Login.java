package org.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends DB {
    public Login() {
        super();
    }

    public int check(String mail, String mdp) {
        int id = -1;
        try {
            Connection bd = this.getConn();
            PreparedStatement check = bd.prepareStatement("SELECT ID FROM Utilisateurs WHERE Mail = ? AND Mot_de_passe = ?");
            check.setString(1, mail);
            check.setString(2, mdp);
            ResultSet log = check.executeQuery();
            if (log.next()) {
                id = log.getInt("ID");
            }
            bd.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
}
