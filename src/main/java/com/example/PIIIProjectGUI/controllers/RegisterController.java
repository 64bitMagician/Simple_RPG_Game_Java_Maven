package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.database.AccountsDAO;
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
        String password = passwordField.getText();

        if (!isValidEmail(email)) {
            messageLabel.setText("Invalid email.");
            return;
        }

        if (password.isEmpty()) {
            messageLabel.setText("Password cannot be empty.");
            return;
        }

        // Perform DB Registration

        String registrationStatusMessage = saveUser(email, password);

        // Update message label and UI according to Registration Status

        if(registrationStatusMessage.equals("Registration Successful!"))
        {
            messageLabel.setStyle("-fx-text-fill: green");
            messageLabel.setText("Registration successful!");
            // Clear fields
            emailField.clear();
            passwordField.clear();
        }
        else if (registrationStatusMessage.equals("Email already exists."))
        {
            messageLabel.setText("Email already exists.");
        }
        else if (registrationStatusMessage.equals("Something went wrong."))
        {
            messageLabel.setText("Something went wrong");
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

    private String saveUser(String email, String password) {
        return AccountsDAO.register(email, password);
    }
}
