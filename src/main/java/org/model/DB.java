package org.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
    private final String bdd;
    private final String url;
    private final String args;
    private final String user;
    private final String password;

    private Connection myConn;

    public DB()
    {
        Properties prop = readPropertiesFile("src\\main\\resources\\config.properties");
        this.bdd = prop.getProperty("bdd");
        this.url = prop.getProperty("url");
        this.args = prop.getProperty("args");
        this.user = prop.getProperty("username");
        this.password = prop.getProperty("password");
    }

    public Connection getConn()
    {
        try {
            myConn = DriverManager.getConnection(url+bdd+args,user,password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myConn;
    }

    public void closeConn()
    {
        try {
            myConn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Properties readPropertiesFile(String fileName) {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }
}