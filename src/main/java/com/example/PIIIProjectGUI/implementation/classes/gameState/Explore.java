package com.example.PIIIProjectGUI.implementation.classes.gameState;

import com.example.PIIIProjectGUI.controllers.ExploreController;
import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.classes.encounters.fights.*;
import com.example.PIIIProjectGUI.implementation.classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

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
    public int totalBattles = 5;


    public Explore(Character character,InventoryController inventoryControllerReference){
        this.character = character;
        this.inventoryController = inventoryControllerReference;
        initializeBattles(character);
        runStageCounter=0;
        battleCounter=0;
    }

    public void showVictoryScreen() {
        ViewNavigator.replaceAllStageWindowsWithASingleOne("victory.fxml","Victory");

        // Pause for 3 seconds before exiting
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> System.exit(0));
        delay.play();
    }

    public void run(Character character){
        ExploreController.run(character);
    }

    public void loadBattleScreen() {
        // First get the ExploreController instance to configure the data of living entities, so they can be shown on screen
        // We do that through the drawBattle() method created within ExploreController
        // We get the ExploreController instance through the battle-view.fxml file, since that file is connected to our controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/views/battle-view.fxml"));
        ExploreController controller = null;
        try {
            Parent root = loader.load();
            controller = loader.getController();
            controller.drawBattle(); // draw initially (initialize the data of living entities before drawing them to screen)
            Stage stage = ViewNavigator.getTopStage();
            stage.setScene(new Scene(root));
            stage.setTitle(ViewNavigator.getTitle("battle-view"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // or your OutputDevice.printError(e.getMessage());
        }
    }

    public void loadLootScreen()
    {
        ViewNavigator.switchScene("loot-screen.fxml");
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





