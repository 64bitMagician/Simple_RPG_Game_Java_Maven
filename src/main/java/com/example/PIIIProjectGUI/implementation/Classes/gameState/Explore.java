package com.example.PIIIProjectGUI.implementation.Classes.gameState;

import com.example.PIIIProjectGUI.GUI.GUIControllers.ExploreController;
import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.GUI.GUIControllers.LootScreenController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.*;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class Explore {

    //DATA
    // The main character
    public Character character;
    public InventoryController inventoryController;
    //      runPattern defines the exploration flow. The predetermined path that the character is going to encounter
    // during his exploration. It is a list of '0' and '1', where 0 denotes a lootable area and 1 denotes a battle.
    public int[] runPattern = {1,0,1,0,1,1,0,1};
    public int runTotalStages = runPattern.length;
    public int runStageCounter;
    // battles.length must match the '1's in 'runPattern'
    public ThreadedBattle[] battles = new ThreadedBattle[5];
    public int battleCounter;


    public Explore(Character character,InventoryController inventoryControllerReference){
        this.character = character;
        this.inventoryController = inventoryControllerReference;
        initializeBattles(character);
        runStageCounter=0;
        battleCounter=0;
    }

    public void showVictoryScreen() {
        Platform.runLater(() -> {
            // Close all current stages

            var windowsCopy = new ArrayList<>(javafx.stage.Window.getWindows());
            for (var window : windowsCopy) {
                if (window instanceof Stage) {
                    ((Stage) window).close();
                }
            }

            try {
                // Load "Victory" FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/victory.fxml"));
                Scene scene = new Scene(loader.load());
                Stage victoryStage = new Stage();
                victoryStage.setTitle("Victory!");
                victoryStage.setScene(scene);
                victoryStage.show();

                // Pause for 3 seconds before exiting
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(e -> System.exit(0));
                delay.play();


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void run(Character character){
        ExploreController.run(character);
    }

    public ExploreController loadBattleScreen() {
        Stage stage = new Stage();
        ExploreController.setStage(stage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/battle-view.fxml"));
            Parent root = loader.load();

            ExploreController controller = loader.getController();
            controller.drawBattle(); // draw initially

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Battle!");
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LootScreenController loadLootScreen()
    {
        Stage stage = new Stage();
        LootScreenController.setStage(stage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/loot-screen.fxml"));
            Parent root = loader.load();

            LootScreenController controller = loader.getController();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Loot!");
            stage.show();

            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    // Private Methods


    private void initializeBattles(Character character){
        battles[0] = new Battle1(character,inventoryController);
        battles[1] = new Battle2(character,inventoryController);
        battles[2] = new Battle3(character,inventoryController);
        battles[3] = new Battle4(character,inventoryController);
        battles[4] = new Battle5(character,inventoryController);
    }
}





