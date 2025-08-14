package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PlayerStatsController {

    @FXML private Label nameLabel;
    @FXML private Label healthLabel;
    @FXML private Label damageMultiplierLabel;
    @FXML private Label aliveLabel;
    @FXML private Label equippedWeaponLabel;
    @FXML private Label totalDamageLabel;

    @FXML
    public void initialize() {
        var character = GameState.character;

        nameLabel.setText(character.name);
        healthLabel.setText(character.health + "/" + character.totalHealth);
        damageMultiplierLabel.setText(String.valueOf(character.damageMultiplier));
        aliveLabel.setText(String.valueOf(character.alive));

        if (character.equippedWeapon != null) {
            equippedWeaponLabel.setText(character.equippedWeapon.getItemName());
        } else {
            equippedWeaponLabel.setText("None");
        }

        totalDamageLabel.setText(String.valueOf(character.totalDamage));
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
    }
}