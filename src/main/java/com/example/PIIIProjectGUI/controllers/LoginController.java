package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.database.CharacterDAO;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import com.example.PIIIProjectGUI.database.AccountsDAO;

import javax.swing.text.View;
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
        GameState.currentAccountRole = AccountsDAO.getRole(GameState.currentAccountID);


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
        OutputDevice.printSuccess("!Logged In Successfully!");

        // Handle program arguments
        //      You can pass arguments to the program, such that you enter the game with a cheater run
        //  or with a normal run. [ "cheatMode" - cheat run, "default" - normal run   ]

        FXMLLoader loader = null;

        //  Transition to according screen based on Role
        if (GameState.currentAccountRole.equals("user")) // If user account
        {
            if(GameState.args.length==0 || GameState.args[0].equals("default")) // if default mode of the game
            {
                if (AccountsDAO.checkifnewgame(GameState.currentAccountID))
                {
                    // Transition to Character Creation Screen
                    ViewNavigator.switchScene("character-creation-view.fxml");
                }
                else
                {
                    // Load the saved data before entering the main menu
                    GameState.loadSavedData();
                    // Not a new game. No new Character Creation Needed. Instead, we fetched all saved data, and move to main menu screen.
                    ViewNavigator.switchScene("main-menu.fxml");
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
                OutputDevice.printInfo("Character has local ID " +  characterID);

                // Transition to Main Menu Screen
                ViewNavigator.switchScene("main-menu.fxml");
            }
            else
            {
                System.out.println("Invalid Program Arguments");
                System.exit(0);
            }
        }
        else if (GameState.currentAccountRole.equals("accounts_administrator")) // If account_administrator account
        {
            ViewNavigator.switchScene("accounts_admin_screen.fxml");
        }
        else // Something is wrong with the role of the account
        {
            OutputDevice.printError("The role of account is neither 'user' nor 'account_administrator', thus is invalid\nCurrent Role: " + GameState.currentAccountRole);
        }
    }

    @FXML
    private void switchToRegister(ActionEvent event) throws IOException {
        ViewNavigator.switchScene("register-view.fxml");
    }
}
