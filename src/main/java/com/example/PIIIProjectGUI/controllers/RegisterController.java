package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.database.AccountsDAO;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML private TextField emailField;
    @FXML private TextField nameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-z]{2,}$", Pattern.CASE_INSENSITIVE);

    @FXML private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        // Populate roles when screen loads
        roleComboBox.getItems().addAll("user", "accounts_administrator");
        roleComboBox.setValue("user"); // default
    }

    @FXML
    private void handleRegister() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (!isValidEmail(email)) {
            messageLabel.setText("Invalid email.");
            return;
        }

        if (password.isEmpty()) {
            messageLabel.setText("Password cannot be empty.");
            return;
        }

        if (role == null || role.isEmpty()) {
            messageLabel.setText("Please select a role.");
            return;
        }

        String registrationStatusMessage = saveUser(email, password, role);

        if (registrationStatusMessage.equals("Registration Successful!")) {
            OutputDevice.printMessage("!Account Registered Successfully!");

            messageLabel.setStyle("-fx-text-fill: green");
            messageLabel.setText("Registration successful!");

            emailField.clear();
            passwordField.clear();
            roleComboBox.setValue("user");
        } else if (registrationStatusMessage.equals("Email already exists.")) {
            messageLabel.setText("Email already exists.");
        } else {
            messageLabel.setText("Something went wrong. It might be that there is no connection to the database...");
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/views/login-view.fxml"));

        Scene scene = new Scene(loader.load(), 400, 300);
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
    }



    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private String saveUser(String email, String password, String role) {
        return AccountsDAO.register(email, password, role);
    }
}
