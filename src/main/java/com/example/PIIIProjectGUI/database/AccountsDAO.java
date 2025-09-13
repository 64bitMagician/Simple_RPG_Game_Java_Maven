package com.example.PIIIProjectGUI.database;

import com.example.PIIIProjectGUI.implementation.classes.database.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDAO {

    // Register new account (default role = user)
    public static String register(String email, String password, String role) {
        String sql = "INSERT INTO accounts (email, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, role);

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

    //  Fetch the role for a given account
    public static String getRole(int accountId) {
        String sql = "SELECT role FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "user"; // default fallback
    }

    //  Delete account by ID
    public static boolean deleteAccount(int accountId) {
        String sql = "DELETE FROM accounts WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //  Fetch all Accounts (for admin listing)
    public static List<Account> getAllAccounts() {
        String sql = "SELECT account_id, email, role FROM accounts";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("account_id");
                String email = rs.getString("email");
                String role = rs.getString("role"); // <-- needs a role column in your accounts table
                accounts.add(new Account(id, email, role));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static boolean checkifnewgame(int accountId) {
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
