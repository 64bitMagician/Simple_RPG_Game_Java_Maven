package com.example.PIIIProjectGUI;

import com.example.PIIIProjectGUI.database.AccountsDAO;
import com.example.PIIIProjectGUI.database.CharacterDAO;
import com.example.PIIIProjectGUI.database.InventoryDAO;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;

public class GameState {
    //PROGRAM ARGUMENTS
    public static String[] args;

    public static Character character;
    public static Inventory inventory;
    public static Explore exploration;
    public static int currentAccountID;

    private GameState() {} // it will be used as a single instance object (singleton), through static data and methods

    public static void saveGame() {
        // Check for new_game field update
        if (AccountsDAO.checkIfNewGame(currentAccountID)) // If new game, it means this is the first save, so: update accounts table such that new_game field is false
        {
            AccountsDAO.updateNewGameFieldToArgumentValue(currentAccountID, false);
        }

        // Update Character Data
        CharacterDAO.saveCharacter(currentAccountID);

        // Save Inventory
        InventoryDAO.saveInventory(character.characterID);



    }

}
