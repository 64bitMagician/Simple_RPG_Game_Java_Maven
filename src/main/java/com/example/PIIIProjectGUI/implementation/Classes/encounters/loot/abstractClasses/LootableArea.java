package com.example.PIIIProjectGUI.implementation.Classes.encounters.loot.abstractClasses;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.interfaces.Item;
import com.example.PIIIProjectGUI.implementation.interfaces.LootableAreaInterface;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;

import java.util.ArrayList;
import java.util.List;

public abstract class LootableArea implements LootableAreaInterface {
    //Data
    protected List<Item> loot = new ArrayList<>();
    protected InventoryController inventory;

    @Override
    public void lootItems() {
        for (Item item : loot) { // while iterating the items in the loot.
            OutputDevice.printMessage("                  =-=-=-=-ITEM FOUND-=-=-=-=");
            OutputDevice.printObject(item);

            Boolean queryAnswer = yesNoQuery.query("Take it?");
                if(queryAnswer)
                {
                    inventory.addItemToInventory(item);
                    break;
                }
                else if(!queryAnswer)
                {
                    OutputDevice.printMessage("You refused to take the item");
                    break;
                }
        }
        OutputDevice.printMessage("                  =-=-=-=-=NO=MORE=ITEMS=TO=LOOT=-=-=-=-=");
    }

    @Override
    public abstract void initializingMessage();
}
