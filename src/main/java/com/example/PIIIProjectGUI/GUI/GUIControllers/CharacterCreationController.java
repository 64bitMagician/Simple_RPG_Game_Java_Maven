package com.example.PIIIProjectGUI.GUI.GUIControllers;

import com.example.PIIIProjectGUI.GUI.utilities.GameState;
import com.example.PIIIProjectGUI.app.Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CharacterCreationController {

    @FXML
    private TextField nameField;

    private Game game;

    @FXML
    public void onCreateCharacterButton(ActionEvent event) {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            nameField.setPromptText("Name cannot be empty");
            return;
        }

        try {
            // Create Character
            GameState.createCharacter(name);
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
