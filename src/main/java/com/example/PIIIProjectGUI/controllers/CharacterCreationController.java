package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.database.CharacterDAO;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Club;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CharacterCreationController {

    @FXML
    private TextField nameField;

    public static void createCharacter(String name) {
        // Get the ID of the Next Character
        int characterID = CharacterDAO.getNextCharacterID();

        //Default Items
        Weapon fists = new Fists();
        Weapon startingWeapon = new Club();

        GameState.character = new Character(characterID , name, 500, 500, 1, startingWeapon);

        GameState.inventory = new Inventory(GameState.character);
        GameState.exploration = new Explore(GameState.character, GameState.inventory.inventoryController);

        GameState.inventory.inventoryController.addItemToInventory(fists);
        GameState.inventory.inventoryController.addItemToInventory(startingWeapon);
    }

    @FXML
    public void onCreateCharacterButton(ActionEvent event) {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            nameField.setPromptText("Name cannot be empty");
            return;
        }

        try {
            // Create Character
            createCharacter(name);
            GameState.saveGame();
            // Transition to Main Menu Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/main-menu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
