package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.UI.UIState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.interfaces.BattleInterface;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.threads.EnemyAtackThread;
import com.example.PIIIProjectGUI.threads.CharacterAtackThread;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

        while(ongoingBattle.get()) // While the battle is up and running
        {
            playerAction();
            armyAttacks();
        }

    }

    @Override
    public void playerAction() {

        while (true) // While game is running
        {

            String action = UIState.chooseYourActionPlayerBattle(); // Determines GameState

            if (action.equals("Open Your Inventory")) {
                //Open Your Inventory Implementation
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
            else if (action.equals("Atack")) {
                // Initiate Atack
                characterAttacks();
                break;
            }
            else if (action.equals("Exit")) {
                Boolean queryAnswer = yesNoQuery.query("Are you sure you want to exit? Your progress will be lost...");
                if(queryAnswer)
                {
                    System.exit(0);
                }
            }

        }

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
                ongoingBattle.set(false);
            }

            OutputDevice.printMessage("-------------------------------------------------------");
        }
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
