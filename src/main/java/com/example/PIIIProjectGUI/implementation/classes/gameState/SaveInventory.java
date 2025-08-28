package com.example.PIIIProjectGUI.implementation.classes.gameState;

import com.example.PIIIProjectGUI.exceptions.CannotSaveEmptyInventoryException;
import com.example.PIIIProjectGUI.inputoutput.CSVOutputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;
import com.example.PIIIProjectGUI.utilities.Pair;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;

import java.util.ArrayList;
import java.util.List;

public class SaveInventory {

    public static void run(Inventory inventory){
        try{
            saveInventory(inventory);
        }
        catch (CannotSaveEmptyInventoryException e){
            OutputDevice.printMessage("Error Message: " + e.getMessage());

            Boolean queryAnswer = yesNoQuery.query();
            if(queryAnswer)
            {
                // Erase all saved Inventory data.
                CSVOutputDevice.eraseFile("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryWeapons.csv");
                CSVOutputDevice.eraseFile("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryPotions.csv");
                OutputDevice.printMessage("All saved data has been deleted");
            }
        }
    }




    private static void saveInventory(Inventory inventory){
        //This method will override the previously saved inventory with the currently running inventory

        //Retreive the items from the currently running inventory, within a list of Pair<Integer,String>
        List<ItemAndCountPair> weaponList,potionList;
        List<Pair<Integer,String>> savingWeaponsList = new ArrayList<>();
        List<Pair<Integer,String>> savingPotionsList = new ArrayList<>();

        //Retreive all weapon and potion items
        weaponList = inventory.inventoryController.returnAllWeaponsFromInventory();
        potionList = inventory.inventoryController.returnAllPotionsFromInventory();

        //Exception for when the inventory is empty. Thus there is nothing to be saved.
        if(weaponList.isEmpty() && potionList.isEmpty()){
            throw new CannotSaveEmptyInventoryException("!Your Current Inventory is Empty!\n!Saving an empty inventory will erase all previously saved data!");
        }

        //Convert ItemAndCountPair to Pair<Integer,String>
        for(ItemAndCountPair pair : weaponList){
            int count = pair.value;
            String className = pair.key.getClass().getSimpleName();
            savingWeaponsList.add(new Pair<Integer,String>(count,className));
        }
        //Save the savingWeaponsList to the savedInventoryWeapons.csv
        CSVOutputDevice.writeTwoColumnCSV("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryWeapons.csv",savingWeaponsList);

        //Convert ItemAndCountPair to Pair<Integer,String>
        for(ItemAndCountPair pair : potionList){
            int count = pair.value;
            String className = pair.key.getClass().getSimpleName();
            savingPotionsList.add(new Pair<Integer,String>(count,className));
        }
        //Save the savingList to the savedInventoryPotions.csv
        CSVOutputDevice.writeTwoColumnCSV("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryPotions.csv",savingPotionsList);

        OutputDevice.printMessage("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        OutputDevice.printMessage("  Inventory Has Been Saved");
        OutputDevice.printMessage("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    }

}
