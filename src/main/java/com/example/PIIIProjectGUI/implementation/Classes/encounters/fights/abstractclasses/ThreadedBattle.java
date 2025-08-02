package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses;

import com.example.PIIIProjectGUI.GUI.GUIControllers.ExploreController;
import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.GUI.utilities.GameState;
import com.example.PIIIProjectGUI.UI.UIState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.interfaces.BattleInterface;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.threads.EnemyAtackThread;
import com.example.PIIIProjectGUI.threads.CharacterAtackThread;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ThreadedBattle implements BattleInterface {
    // In this verion of the battle, the character atack not just one enemy at a time, but all of them.
    // The enemies do one atack on the player, each one of them.
    //DATA
    public Character character;
    public InventoryController inventory;
    public List<Enemy> enemyArmy = new ArrayList<>();
    public AtomicBoolean ongoingBattle = new AtomicBoolean();

    @Override
    public void run() {

        if(ongoingBattle.get()) // IF the battle is up and running
        {
            characterAttacks();
            armyAttacks();
            ExploreController.enemies = enemyArmy;
        }
    }

    @Override
    public void playerAction() {

    }

    private boolean checkIfWholeArmyIsDead(){
        boolean wholeArmyIsDead = true;
        for(Enemy enemy : enemyArmy)
        {
            if (enemy.alive)
            {
                wholeArmyIsDead = false;
                break;
            }
        }
        return wholeArmyIsDead;
    }

    @Override
    public void characterAttacks() {
        List<CharacterAtackThread> characterAtackThreads = new ArrayList<>();
        if (character.alive == true)
        {
            for (int i = 0; i < enemyArmy.size(); i++) {
                // Make threads for each alive enemy in the army, and run the threads
                if (enemyArmy.get(i).alive) { // if enemy alive
                    CharacterAtackThread t = new CharacterAtackThread(enemyArmy.get(i), i, character, ongoingBattle);
                    characterAtackThreads.add(t);
                    t.run();
                }
            }

            for (CharacterAtackThread t : characterAtackThreads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(checkIfWholeArmyIsDead()) // if whole army is dead
            {
                OutputDevice.printMessage("/////////////////////////////");
                OutputDevice.printMessage("!THE ARMY HAS BEEN DEFEATED!");
                OutputDevice.printMessage("/////////////////////////////");
                victoryUI();
                ongoingBattle.set(false);
            }

            OutputDevice.printMessage("-------------------------------------------------------");
        }
    }

    private void victoryUI(){
        // Battle is over, show popup and close battle screen
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Victory");
            alert.setHeaderText("You won the battle!");
            alert.setContentText("Click Continue to proceed.");

            ButtonType continueButton = new ButtonType("Continue", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(continueButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == continueButton) {

                    // Now close the actual battle stage
                    Stage battleStage = ExploreController.getStage(); // expose this getter
                    if (battleStage != null) {
                        battleStage.close();
                    }
                }
            });
        });
    }


    @Override
    public void armyAttacks() {
        List<EnemyAtackThread> enemyAtackThreads = new ArrayList<>();
        for(int i = 0;i<enemyArmy.size();i++){
            // Make threads for each alive enemy in the army, and run the threads
            if (enemyArmy.get(i).alive)
            {
                EnemyAtackThread t = new EnemyAtackThread(enemyArmy.get(i),i,character,ongoingBattle);
                enemyAtackThreads.add(t);
                t.run();
            }
        }

        for (EnemyAtackThread t : enemyAtackThreads){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
