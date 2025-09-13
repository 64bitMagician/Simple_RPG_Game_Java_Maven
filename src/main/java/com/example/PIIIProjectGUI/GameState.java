package com.example.PIIIProjectGUI;

import com.example.PIIIProjectGUI.database.AccountsDAO;
import com.example.PIIIProjectGUI.database.CharacterDAO;
import com.example.PIIIProjectGUI.database.InventoryDAO;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Explore;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;

public class GameState {
    //PROGRAM ARGUMENTS
    public static String[] args;

    public static Character character;
    public static Inventory inventory;
    public static Explore explore;

    public static int currentAccountID;
    public static String currentAccountRole;

    private GameState() {} // it will be used as a single instance object (singleton), through static data and methods

    // Mark account as not "new_game".
    public static void markAccountAsNonNewGame(){
        // Check for new_game field update
        if (AccountsDAO.checkifnewgame(currentAccountID)) // If new game, update accounts table such that new_game field is false
        {
            AccountsDAO.updateNewGameFieldToArgumentValue(currentAccountID, false);
        }
        else {
            OutputDevice.printMessage("If you see this, then, when you called the markAccountAsNonNewGame method (GameState.java), the new_game field of accounts table, corresponding to this account should have been 'false'. So something is wrong.");
        }
    }

    public static void saveGame() {
        // Update Character Data
        CharacterDAO.saveCharacter(currentAccountID);

        // Save Inventory
        InventoryDAO.saveInventory(character.characterID);
    }

    // Erase saved data (game progress reset)
    public static void gameProgressReset(){
        // We have to delete contents from MySQL Tables related to saving progress

        // Erase Inventory of Character
        InventoryDAO.clearInventory(character.characterID);

        // Erase Character
        CharacterDAO.deleteCharacter(currentAccountID);

        // Mark account as in "new_game" state
        AccountsDAO.updateNewGameFieldToArgumentValue(currentAccountID,true);
    }

    public static void loadSavedData(){
        // Load Character Data
        CharacterDAO.loadSavedData(currentAccountID);

        // Load saved inventory associated to this character
        InventoryDAO.loadInventory(character.characterID);

        // Equip the previously equipped weapon
        CharacterDAO.equipThePreviouslyEquippedWeapon(currentAccountID);

        // Add Progress Data to newly instantiated Explore Singleton
        explore = new Explore(character, inventory.inventoryController);
        explore.battleCounter = CharacterDAO.getBattleCounterProgress(currentAccountID);;
        explore.runStageCounter = CharacterDAO.getRunStageCounterProgress(currentAccountID);

        printLoadedDataFromTable();
    }

    private static void printLoadedDataFromTable()
    {
        OutputDevice.printMessage("");
        OutputDevice.printMessage("==========THE FOLLOWING DATA HAS BEEN LOADED==========");

        OutputDevice.printMessage("-----CHARACTER LOADED WITH THE FOLLOWING DATA-----");
        OutputDevice.printMessage("ID: " + character.characterID);
        OutputDevice.printMessage("NAME: " + character.name);
        OutputDevice.printMessage("HEALTH: " + character.health + "/" + character.totalHealth);
        OutputDevice.printMessage("DAMAGE MULTIPLIER: " + character.damageMultiplier);
        OutputDevice.printMessage("TOTAL DAMAGE: " + character.totalDamage);
        OutputDevice.printMessage("EQUIPPED WEAPON: " + character.equippedWeapon.getItemName());
        OutputDevice.printMessage("--------------------------------------------------");

        OutputDevice.printMessage("==========CHARACTER PROGRESS==========");
        OutputDevice.printMessage("BATTLE COUNTER: " + explore.battleCounter + "/" + explore.totalBattles);
        OutputDevice.printMessage("STAGE COUNTER: " + explore.runStageCounter + "/" +  explore.runTotalStages);
        OutputDevice.printMessage("--------------------------------------");

        OutputDevice.printMessage("==========INVENTORY LOADED WITH THE FOLLOWING ITEMS==========");
        for (ItemAndCountPair item : inventory.items)
            OutputDevice.printMessage("x" + item.value + " " +  item.key.getItemName());
        OutputDevice.printMessage("--------------------------------------");
    }
}
