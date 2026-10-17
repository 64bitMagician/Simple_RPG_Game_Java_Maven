package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class MainMenuController {

    @FXML
    public void onInventory(ActionEvent e) {
        // Transition to Inventory View
            ViewNavigator.switchScene("inventory-view.fxml");
    }


    @FXML
    public void onExplore(ActionEvent e) {
        GameState.explore.run(GameState.character);
    }

    @FXML
    public void onShowStats(ActionEvent e) {
            ViewNavigator.switchScene("playerStats-view.fxml");
    }

    @FXML
    public void onExit(ActionEvent e) {
        // Saving Game Before Existing
        OutputDevice.printMessage("Saving game before exiting...");
        GameState.saveGame();
        System.exit(0);
    }
}
