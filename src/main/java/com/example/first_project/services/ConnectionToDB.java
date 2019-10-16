package com.example.first_project.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDB {


    public Connection startConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection( "jdbc:mysql://localhost:3306/provaDb","provaDb","provaDb");
        return con;
    }
}
