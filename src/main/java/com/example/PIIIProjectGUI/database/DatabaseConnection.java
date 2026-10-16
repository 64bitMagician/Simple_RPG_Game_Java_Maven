package com.example.PIIIProjectGUI.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Credentials are read from environment variables so they never get committed to the repository.
    //   GAME_DB_URL      (default: jdbc:mysql://localhost:3306/game_db)
    //   GAME_DB_USER     (default: root)
    //   GAME_DB_PASSWORD (default: empty)
    private static final String URL = getEnvOrDefault("GAME_DB_URL", "jdbc:mysql://localhost:3306/game_db");
    private static final String USER = getEnvOrDefault("GAME_DB_USER", "root");
    private static final String PASSWORD = getEnvOrDefault("GAME_DB_PASSWORD", "");

    private static String getEnvOrDefault(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
