package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.classes.encounters.loot.PotionChest;
import com.example.PIIIProjectGUI.implementation.classes.encounters.loot.WeaponChest;
import com.example.PIIIProjectGUI.implementation.classes.encounters.loot.abstractClasses.LootableArea;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.implementation.classes.encounters.fights.abstractclasses.ThreadedBattle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExploreController {

    @FXML private VBox battleArea;
    @FXML private TextArea battleLog;

    //Make new stage

    static private Stage battleStage = new Stage();

    static public List<Enemy> enemies = new ArrayList<>();
    static private List<EnemyUI> enemiesUI = new ArrayList<>();
    static private EnemyUI selectedEnemy;

    static private Explore explore = GameState.explore;
    static private ThreadedBattle battle;

    static private CharacterUI characterUI;

    static public Stage getStage(){
        return battleStage;
    }

    static public void setStage(Stage givenStage){
        battleStage = givenStage;
    }


    static private void updateEnemyUIData(){

            for(int i = 0; i < enemies.toArray().length; i++)
            {
                enemiesUI.get(i).correspondingEnemy.health = enemies.get(i).health;
                enemiesUI.get(i).updateHealthBar();
            }
    }

    static void updateCharacterUI() {
        characterUI.updateHealthBar();
    }

    static public void updateAllUI(){
        updateCharacterUI();
        updateEnemyUIData();
    }



    static public void run(Character character){
        if (explore.runStageCounter == explore.runTotalStages)
        {
            explore.showVictoryScreen();
            // Victory, thus reset all game progress for this account
            GameState.gameProgressReset();
            return;
        }
        if(explore.runPattern[explore.runStageCounter]==0) // Encounter a lootable area
        {
            OutputDevice.printMessage("!YOU FOUND A CHEST!");
            LootableArea randomLootableArea;
            if(Math.random() < 0.5)
            {
                randomLootableArea = new WeaponChest(explore.inventoryController);
            }
            else
            {
                randomLootableArea = new PotionChest(explore.inventoryController);
            }

            LootScreenController.setLootableArea(randomLootableArea);
            explore.loadLootScreen();
        }
        else if(explore.runPattern[explore.runStageCounter]==1) // Encounter a battle
        {
            battle = explore.battles[explore.battleCounter];

            //battle.run(); old terminal version
            // Run GUI Battle
            enemies = battle.enemyArmy;
            enemiesUI.clear(); // reset the enemiesUI
            explore.loadBattleScreen();
        }
    }

    public void drawBattle() {
        battleArea.getChildren().clear();

        if (characterUI==null)
            characterUI = new CharacterUI();
        battleArea.getChildren().add(characterUI.container);

        // Create and draw enemies
        for (int i = 0; i < enemies.toArray().length; i++) {
            EnemyUI enemyUI = new EnemyUI(enemies.get(i).name, enemies.get(i).health, enemies.get(i));
            enemiesUI.add(enemyUI);
            battleArea.getChildren().add(enemyUI.container);
        }
    }

    @FXML
    private void handleOpenInventory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/views/inventory-view.fxml"));
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
        log("Opening inventory...");
        // You can use a scene switcher here
    }

    @FXML
    private void handleAttack() {
        battle.run();
        updateCharacterUI();
        updateEnemyUIData();
    }

    @FXML
    private void handleExitGame() {
        log("Exiting game...");
        System.exit(0);
    }

    private void log(String message) {
        battleLog.appendText(message + "\n");
    }

    // ---------------------------
    // Inner class: EnemyUI
    // ---------------------------
    private class EnemyUI {
        Enemy correspondingEnemy;
        VBox container;
        ProgressBar hpBar;
        StackPane hpStack;

        EnemyUI(String name, float hp, Enemy enemy) {
            this.correspondingEnemy = enemy;
            this.hpBar = new ProgressBar(hp / correspondingEnemy.totalHealth);
            hpBar.setPrefWidth(120);
            Label nameLabel = new Label(name);

            Label hpLabel = new Label((float)correspondingEnemy.health + " / " + (float)correspondingEnemy.totalHealth);
            hpLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: black;");

            hpStack = new StackPane(hpBar, hpLabel);

            container = new VBox(5, nameLabel, hpStack);
            container.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #fdd;");
            container.setOnMouseClicked(e -> select());

            highlight(false);
        }

        void updateHealthBar()
        {
            Label hpLabel = (Label) hpStack.getChildren().get(1);

            if(correspondingEnemy.health <= 0)
            {
                hpBar.setProgress(0/correspondingEnemy.totalHealth);
                hpLabel.setText("0/" + correspondingEnemy.totalHealth);
            }
            else
            {
                hpBar.setProgress(correspondingEnemy.health/correspondingEnemy.totalHealth);
                hpLabel.setText(correspondingEnemy.health + "/" + correspondingEnemy.totalHealth);
            }
        }

        void select() {
            if (selectedEnemy != null) {
                selectedEnemy.highlight(false);
            }
            selectedEnemy = this;
            highlight(true);
        }

        void highlight(boolean selected) {
            container.setStyle(selected
                    ? "-fx-border-color: red; -fx-background-color: #faa; -fx-padding: 10;"
                    : "-fx-border-color: black; -fx-background-color: #fdd; -fx-padding: 10;");
        }
    }


    // ---------------------------
    // Inner class: CharacterUI
    // ---------------------------
    private class CharacterUI {
        VBox container;
        ProgressBar hpBar;
        StackPane hpStack;
        Label hpLabel;

        CharacterUI() {
            this.hpBar = new ProgressBar(GameState.character.health / GameState.character.totalHealth);
            hpBar.setPrefWidth(120);
            Label nameLabel = new Label(GameState.character.name);
            hpLabel = new Label((float)GameState.character.health + " / " + (float)GameState.character.totalHealth);
            hpLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: black;");

            hpStack = new StackPane(hpBar, hpLabel);

            container = new VBox(5, nameLabel, hpStack);
            container.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #98FB98;");
        }

        void updateHealthBar()
        {
            hpLabel = (Label) hpStack.getChildren().get(1);

            if(GameState.character.health <= 0)
            {
                hpBar.setProgress(0/GameState.character.totalHealth);
                hpLabel.setText("0/" + GameState.character.totalHealth);
            }
            else
            {
                hpBar.setProgress(GameState.character.health/GameState.character.totalHealth);
                hpLabel.setText(GameState.character.health + "/" + GameState.character.totalHealth);
            }
        }
    }
}

