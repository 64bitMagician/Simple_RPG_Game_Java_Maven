package com.example.PIIIProjectGUI.implementation.Classes.gameState;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.*;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.loot.PotionChest;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.loot.WeaponChest;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.loot.abstractClasses.LootableArea;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;

public class Explore {

    //DATA
    // The main character
    Character character;
    InventoryController inventory;
    //      runPattern defines the exploration flow. The predetermined path that the character is going to encounter
    // during his exploration. It is a list of '0' and '1', where 0 denotes a lootable area and 1 denotes a battle.
    private int[] runPattern = {1,0,1,0,1,1,0,1};
    private int runTotalStages = runPattern.length;
    private int runStageCounter;
    // battles.length must match the '1's in 'runPattern'
    private ThreadedBattle[] battles = new ThreadedBattle[5];
    private int battleCounter;


    public Explore(Character character,InventoryController inventoryReference){
        this.character = character;
        this.inventory = inventoryReference;
        initializeBattles(character);
        runStageCounter=0;
        battleCounter=0;
    }

    public void run(Character character){
        if (runStageCounter == runTotalStages)
        {
            OutputDevice.printMessage("You beat all the monsters");
            OutputDevice.printMessage("     YOU WON!");
            OutputDevice.printMessage("    The End...");
            System.exit(0);
        }
            if(runPattern[runStageCounter]==0) // Encounter a lootable area
            {
                OutputDevice.printMessage("!YOU FOUND A CHEST!");
                LootableArea randomLootableArea;
                if(Math.random() < 0.7)
                {
                    randomLootableArea = new WeaponChest(inventory);
                }
                else
                {
                    randomLootableArea = new PotionChest(inventory);
                }
                lootLootableArea(randomLootableArea);
                //Free up memory by removing references to the instanced object, using null.
                //This makes randomLootableArea be garbage-collected.
                randomLootableArea = null;
            }
            else if(runPattern[runStageCounter]==1) // Encounter a battle
            {
                OutputDevice.printMessage("               YOU ENCOUNTERED A BATTLE");
                OutputDevice.printMessage("======================================================");

                ThreadedBattle battle = battles[battleCounter];

                battle.run();

                battleCounter++;
            }
            runStageCounter++;
        }

    // Private Methods

    private void initializeBattles(Character character){
        battles[0] = new Battle1(character,inventory);
        battles[1] = new Battle2(character,inventory);
        battles[2] = new Battle3(character,inventory);
        battles[3] = new Battle4(character,inventory);
        battles[4] = new Battle5(character,inventory);
    }

    private void lootLootableArea(LootableArea lootableArea){
        OutputDevice.printMessage("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        lootableArea.initializingMessage();
        lootableArea.lootItems();
        OutputDevice.printMessage("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
}





