package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("All fields are required.");
            return;
        }

        if (credentialsMatch(email, password)) {
            try {
                loadNextScreen(event);
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Failed to load game.");
            }
        } else {
            messageLabel.setText("Incorrect email or password.");
        }
    }

    private boolean credentialsMatch(String email, String password) {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 3 && parts[0].equals(email) && parts[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadNextScreen(ActionEvent event) throws IOException {
        // Handle program arguments
        //      You can pass arguments to the program, such that you enter the game with a cheater run
        //  or with a normal run. [ "cheatMode" - cheat run, "default" - normal run   ]

        //  Character Creation
        if(GameState.args.length==0 || GameState.args[0].equals("default"))
        {
            // Transition to Character Creation Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/character-creation-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if(GameState.args[0].equals("cheatMode"))
        {
            //Character, inventory and exploration initialization
            Weapon fists = new Fists();
            GameState.character = new com.example.PIIIProjectGUI.implementation.Classes.player.Character("Cheater", 999, 999, fists);
            GameState.inventory = new Inventory(GameState.character);
            GameState.inventory.inventoryController.addItemToInventory(fists);
            GameState.exploration = new Explore(GameState.character, GameState.inventory.inventoryController);
            // Transition to Main Menu Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/main-menu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
        }
        else {
            System.out.println("Invalid Program Arguments");
            System.exit(0);
        }
    }

    @FXML
    private void switchToRegister(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
