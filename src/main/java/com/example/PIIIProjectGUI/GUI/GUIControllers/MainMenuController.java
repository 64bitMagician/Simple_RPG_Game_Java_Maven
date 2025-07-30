package com.example.PIIIProjectGUI.GUI.GUIControllers;

import com.example.PIIIProjectGUI.GUI.utilities.GameState;
import com.example.PIIIProjectGUI.app.Game;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.SaveInventory;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.RetreiveSavedInventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    public void onInventory(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/inventory-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();  // Or reuse the current one if preferred
            stage.setScene(scene);
            stage.setTitle("Inventory");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Optionally show an error dialog to the user
            // showErrorDialog("Failed to load inventory screen.");
        }
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
        RetreiveSavedInventory.run(GameState.inventory.inventoryController);
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
