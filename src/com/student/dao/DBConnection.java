package com.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class.
 * Provides a single reusable method to get a database connection.
 * Promotes code reuse — all DAO classes use this instead of duplicating connection logic.
 */
public class DBConnection {

    // ----- UPDATE THESE VALUES TO MATCH YOUR MYSQL SETUP -----
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";  // Change to your MySQL password

    /**
     * Returns a Connection object to the MySQL database.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC driver (optional for newer JDBC versions, but safe to include)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found! Add mysql-connector-j jar to build path.");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
