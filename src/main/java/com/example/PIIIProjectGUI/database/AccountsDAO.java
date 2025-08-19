package com.example.PIIIProjectGUI.database;

import java.sql.*;

public class AccountsDAO {

    // Register new account
    public static String register(String email, String password) {
        String sql = "INSERT INTO accounts (email, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            stmt.executeUpdate();
            return "Registration Successful!";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "Email already exists.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Something went wrong.";
        }
    }

    // Login (returns account_id if success, -1 if fail)
    public static int login(String email, String password) {
        String sql = "SELECT account_id, password FROM accounts WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    if (storedPassword.equals(password)) {
                        return rs.getInt("account_id");
                    } else {
                        System.out.println("Invalid password.");
                    }
                } else {
                    System.out.println("Email not found.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean checkIfNewGame(int accountId) {
        String sql = "SELECT new_game FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("new_game");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateNewGameFieldToArgumentValue(int accountId, boolean value) {
        String sql = "UPDATE accounts SET new_game = ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, value);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
