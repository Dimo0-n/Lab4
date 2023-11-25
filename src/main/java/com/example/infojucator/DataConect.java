package com.example.infojucator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConect {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Lab4";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "dima123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
