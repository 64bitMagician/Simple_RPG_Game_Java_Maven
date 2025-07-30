package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses;

import com.example.PIIIProjectGUI.UI.UIState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.interfaces.BattleInterface;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class UnthreadedBattle implements BattleInterface {
    //DATA
    public Character character;
    public Inventory inventory;
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

    @Override
    public void characterAttacks(){
        if(character.alive==true)
            for(int i = 0;i<enemyArmy.size();i++){
                if(enemyArmy.get(i).alive==true){
                    OutputDevice.printMessage("-------------------------------------------------------");
                    OutputDevice.printMessage("The character attacks " + enemyArmy.get(i).name + " " + (i+1) + " from the army.");
                    enemyArmy.get(i).health-=character.totalDamage;
                    OutputDevice.printMessage("The " + enemyArmy.get(i).name +" takes damage "+character.totalDamage+" damage.");
                    OutputDevice.printMessage(enemyArmy.get(i).returnHealth());

                    if(enemyArmy.get(i).health<=0) // goblin dies
                    {
                        enemyArmy.get(i).alive=false; //! why does this set all object to be not alive. They share the same .alive... Why?
                        OutputDevice.printMessage("The " + enemyArmy.get(i).name + " has died");

                    }
                    if(enemyArmy.get(enemyArmy.size()-1).alive==false)
                    { // whole army is dead
                        OutputDevice.printMessage("/////////////////////////////");
                        OutputDevice.printMessage("!THE ARMY HAS BEEN DEFEATED!");
                        OutputDevice.printMessage("/////////////////////////////");
                        ongoingBattle.set(false);
                    }
                    OutputDevice.printMessage("-------------------------------------------------------");
                    break;
                }
            }
    }

    @Override
    public void armyAttacks() {
        for(int i = 0;i<enemyArmy.size();i++){
            if(enemyArmy.get(i).alive==true){
                OutputDevice.printMessage("-------------------------------------------------------");
                OutputDevice.printMessage("The " + (i+1) + " enemy, the " + enemyArmy.get(i).name + ", from the army attacks");
                character.health-=enemyArmy.get(i).damage;
                OutputDevice.printMessage(character.name + " takes "+ enemyArmy.get(i).damage + " damage.");
                OutputDevice.printMessage(character.returnHealth());
                if(character.health<=0) // character dies
                {
                    character.alive=false;
                    OutputDevice.printMessage("YOU DIED");
                    ongoingBattle.set(false);
                    System.exit(0);
                    break;
                }
                OutputDevice.printMessage("-------------------------------------------------------");
            }
        }
    }
}
