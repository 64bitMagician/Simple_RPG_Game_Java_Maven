package com.example.PIIIProjectGUI.GUI.GUIControllers;

import com.example.PIIIProjectGUI.GUI.utilities.GameState;
import com.example.PIIIProjectGUI.app.Game;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.SaveInventory;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.RetreiveSavedInventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    public void onInventory(ActionEvent e) {
        GameState.inventory.run();
    }

    @FXML
    public void onExplore(ActionEvent e) {
        GameState.exploration.run(GameState.character);
    }

    @FXML
    public void onShowStats(ActionEvent e) {
        GameState.character.displayStats();
    }

    @FXML
    public void onRetrieveInventory(ActionEvent e) {
        RetreiveSavedInventory.run(GameState.inventory);
    }

    @FXML
    public void onSaveInventory(ActionEvent e) {
        SaveInventory.run(GameState.inventory);
    }

    @FXML
    public void onExit(ActionEvent e) {
        System.exit(0);
    }
}
