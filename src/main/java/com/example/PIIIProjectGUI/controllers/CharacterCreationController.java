package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.database.CharacterDAO;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.Club;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
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
        OutputDevice.printMessage("Creating New Character...");

        //Default Items
        Weapon fists = new Fists();
        Weapon startingWeapon = new Club();

        GameState.character = new Character(name, 500, 500, 1, startingWeapon);



        GameState.inventory = new Inventory(GameState.character);
        GameState.explore = new Explore(GameState.character, GameState.inventory.inventoryController);

        OutputDevice.printMessage("Adding Starting Weapons...");
        GameState.inventory.inventoryController.addItemToInventory(fists);
        GameState.inventory.inventoryController.addItemToInventory(startingWeapon);

        int characterID = CharacterDAO.insertNewCharacterToTable(GameState.currentAccountID);
        GameState.character.characterID = characterID;
        OutputDevice.printMessage("Character has local ID " +  characterID);
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

            // This is the first save, after the character is created. So we mark the account as not being in a new game state anymore.
            // We do this such that the progress will carry on, and continue to use the character created, not recreate a new one each run.
            GameState.markAccountAsNonNewGame();

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
