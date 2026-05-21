package com.pao.project.cabinet_medical.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection(){
        try{
            Properties props = new Properties();
            //InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            InputStream input = new FileInputStream("resources/db.properties");
            props.load(input);
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connectat cu succes!");
        } catch (Exception e){
            throw new RuntimeException("Eroare conectare: "+e.getMessage());
        }
    }

    public static DatabaseConnection getInstance()
    {
        if (instance == null)
        {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection()
    {
        return connection;
    }
}
