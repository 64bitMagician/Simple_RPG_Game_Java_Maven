package com.example.PIIIProjectGUI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private TextField nameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    @FXML
    private void handleRegister() {
        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String password = passwordField.getText();

        if (!isValidEmail(email)) {
            messageLabel.setText("Invalid email.");
            return;
        }

        if (name.isEmpty()) {
            messageLabel.setText("Name cannot be empty.");
            return;
        }

        if (password.isEmpty()) {
            messageLabel.setText("Password cannot be empty.");
            return;
        }

        try {
            saveUser(email, name, password);
            messageLabel.setStyle("-fx-text-fill: green");
            messageLabel.setText("Registration successful!");

            // Clear fields
            emailField.clear();
            nameField.clear();
            passwordField.clear();
        } catch (IOException e) {
            messageLabel.setText("Error saving data.");
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/login-view.fxml"));

        Scene scene = new Scene(loader.load(), 400, 300);
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
    }



    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private void saveUser(String email, String name, String password) throws IOException {
        FileWriter writer = new FileWriter("users.txt", true); // Append mode
        writer.write(String.format("%s,%s,%s%n", email, name, password));
        writer.close();
    }
}
