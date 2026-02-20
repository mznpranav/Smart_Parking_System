package com.parking.system;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/smart_parking";
    private static final String USER = "root";
    private static final String PASSWORD = "iyan@2005";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Static block to load the driver class
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
