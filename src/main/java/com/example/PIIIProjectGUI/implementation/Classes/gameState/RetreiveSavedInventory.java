package com.example.PIIIProjectGUI.implementation.Classes.gameState;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.exceptions.NoSavedInventoryStateException;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.DeathPotion;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.GreatHealthPotion;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.HealthPotion;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.HolySword;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.inputoutput.CSVInputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ClassInstantiationReference;
import com.example.PIIIProjectGUI.utilities.Pair;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;

import java.util.List;

public class RetreiveSavedInventory {

    public static void run(InventoryController inventory){
        try{
                Boolean queryAnswer = yesNoQuery.query("Retreiving saved items will remove all items from current inventory.\nAre you sure you want to do that?");
                if(queryAnswer)
                {
                    retreiveSavedInventory(inventory);
                }
        }
        catch (NoSavedInventoryStateException e)
        {
            OutputDevice.printMessage("Error Message: " + e.getMessage());

            Boolean queryAnswer = yesNoQuery.query("Do you want a starting kit?");
            if(queryAnswer)
            {
                inventory.eraseInventory();
                addSampleItemsToInventory(inventory);

            }
        }
    }




    private static void retreiveSavedInventory(InventoryController inventory){
        if(CSVInputDevice.isCSVEmptyAfterHeader("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryWeapons.csv") && CSVInputDevice.isCSVEmptyAfterHeader("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryPotions.csv"))
        {
            throw new NoSavedInventoryStateException("!Inventory Save Missing. There are no items to be retreived!");
        }

        //Erase all items from current inventory
        inventory.eraseInventory();

        List<Pair<Integer, String>> list;

        //Adding Weapons To Inventory
        list = CSVInputDevice.readIntegerStringCSV("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryWeapons.csv");
        for (Pair<Integer, String> pair : list) {
            int quantity = pair.getFirst();
            String className = pair.getSecond();
            for (int i = 0; i < quantity; i++) {
                // Pass the correct package path
                Weapon instance = ClassInstantiationReference.createInstance("com.example.PIIIProjectGUI.implementation.Classes.items.weapons", className);
                inventory.addItemToInventory(instance);
            }
        }

        //Adding Potions To Inventory
        list = CSVInputDevice.readIntegerStringCSV("src/main/java/com/example/PIIIProjectGUI/dataFiles/gameStateFiles/savedInventoryPotions.csv");
        for (Pair<Integer, String> pair : list) {
            int quantity = pair.getFirst();
            String className = pair.getSecond();
            for (int i = 0; i < quantity; i++) {
                // Pass the correct package path
                Potion instance = ClassInstantiationReference.createInstance("com.example.PIIIProjectGUI.implementation.Classes.items.potions", className);
                inventory.addItemToInventory(instance);
            }
        }
        OutputDevice.printMessage("Saved Inventory Has Been Restored");
    }

    private static void addSampleItemsToInventory(InventoryController inventory){
        inventory.addItemToInventory(new DeathPotion());
        inventory.addItemToInventory(new GreatHealthPotion());
        inventory.addItemToInventory(new DeathPotion());
        inventory.addItemToInventory(new HolySword());
        inventory.addItemToInventory(new HealthPotion());
        inventory.addItemToInventory(new HolySword());
        inventory.addItemToInventory(new GreatHealthPotion());
    }


}
