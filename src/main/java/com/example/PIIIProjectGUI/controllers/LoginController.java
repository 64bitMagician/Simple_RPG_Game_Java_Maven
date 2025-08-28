package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.database.CharacterDAO;
import com.example.PIIIProjectGUI.database.InventoryDAO;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.example.PIIIProjectGUI.database.AccountsDAO;

import java.io.IOException;

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

        GameState.currentAccountID = AccountsDAO.login(email, password);

        if (GameState.currentAccountID!=-1) { // if login succesful
            try {
                loginSuccesful(event);
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Failed to load game.");
            }
        } else { // if login unsuccesful
            messageLabel.setText("Incorrect email or password.");
        }
    }

    private void loginSuccesful(ActionEvent event) throws IOException {
        OutputDevice.printMessage("!Logged In Successfully!");

        // Handle program arguments
        //      You can pass arguments to the program, such that you enter the game with a cheater run
        //  or with a normal run. [ "cheatMode" - cheat run, "default" - normal run   ]

        FXMLLoader loader = null;

        //  Character Creation
        if(GameState.args.length==0 || GameState.args[0].equals("default")) // if default mode of the game
        {
            if (AccountsDAO.checkIfNewGame(GameState.currentAccountID))
            {
                // Transition to Character Creation Screen
                loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/character-creation-view.fxml"));
            }
            else
            {
                // Load the saved data before entering the main menu
                GameState.loadSavedData();
                // Not a new game. No new Character Creation Needed. Instead, we fetched all saved data, and move to main menu screen.
                loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/main-menu.fxml"));
            }
        }
        else if(GameState.args[0].equals("cheatMode")) // if in cheatMode Program Argument.
        {
            // Don't matter the account. It will ignore the account associated and simply always start new game with New Cheating Character.

            //Character, inventory and exploration initialization
            Weapon fists = new Fists();
            GameState.character = new Character("Cheater", 999, 999, 999, fists);
            GameState.inventory = new Inventory(GameState.character);
            GameState.inventory.inventoryController.addItemToInventory(fists);
            GameState.explore = new Explore(GameState.character, GameState.inventory.inventoryController);

            int characterID = CharacterDAO.insertNewCharacterToTable(GameState.currentAccountID);
            GameState.character.characterID = characterID;
            OutputDevice.printMessage("Character has local ID " +  characterID);

            // Transition to Main Menu Screen
            loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/main-menu.fxml"));
        }
        else {
            System.out.println("Invalid Program Arguments");
            System.exit(0);
        }

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
