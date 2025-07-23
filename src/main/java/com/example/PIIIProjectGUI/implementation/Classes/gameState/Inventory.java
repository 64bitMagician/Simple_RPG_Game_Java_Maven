package com.example.PIIIProjectGUI.implementation.Classes.gameState;

import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.interfaces.Item;
import com.example.PIIIProjectGUI.inputoutput.InputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Inventory {
    private float totalWeightCapacity = 1000;
    private float currentWeightCapacity = 0;
    private int totalSlots = 20;
    private int usedSlots = 0;
//    private List<Item> itemInstanceRow = new ArrayList<>();
//    private List<Integer> itemCountRow = new ArrayList<>();
    private List<ItemAndCountPair> items = new ArrayList<>();

    private Character character;

    // Private constructor to prevent instantiation
    public Inventory(Character characterReceived) {
        this.character = characterReceived;
    }

    //The retreiveIndexOfItem(Item item) method returns
    // the index of the object within the list,
    // that maches the class type of
    // the object that you are passing as an argument.
    // If no match is found, -1 will be returned
    private int retreiveIndexOfItem(Item item) {
        int index = 0;
        for(ItemAndCountPair i : items) {
            if(i.key.getClass() == item.getClass()) { // the type of given object matches the type of an object within the inventory
                return index;
            }
            index++;
        }
        return -1;
    }

    public void run(){
        displayInventory();
    }

    public List<ItemAndCountPair> returnAllWeaponsFromInventory(){
        List<ItemAndCountPair> returnedWeapons = new ArrayList<>();
        Stream<ItemAndCountPair> weaponsStream = items.stream();
        weaponsStream.filter(item -> item.key instanceof Weapon).sorted().forEach(item -> returnedWeapons.add(item));
        return returnedWeapons;
    }

    public void eraseInventory(){
        items.clear();
        currentWeightCapacity = 0;
        usedSlots = 0;
    }

    public List<ItemAndCountPair> returnAllPotionsFromInventory(){
        List<ItemAndCountPair> returnedPotions = new ArrayList<>();
        Stream<ItemAndCountPair> potionsStream = items.stream();
        potionsStream.filter(item -> item.key instanceof Potion).sorted().forEach(item -> returnedPotions.add(item));
        return returnedPotions;
    }

    private boolean stackItemToInventory(Item item){
        //Check if he item already appears in your inventory, so you can stack it
        //trebuie referinta sau doar clasa
        int indexOfItem = retreiveIndexOfItem(item);
        if(indexOfItem>=0) // An instance of the item you want to add already exists in your inventory, so you can stack it
        {
            items.get(indexOfItem).value++;
            currentWeightCapacity += item.getItemWeight();
            return true;
        }
        else
            return false;
    }

    public void addItemToInventory(Item item) {
        if (currentWeightCapacity + item.getItemWeight() > totalWeightCapacity) {
            OutputDevice.printMessage("Cannot add this item to your inventory. It would exceed the total weight capacity of " + currentWeightCapacity + "/" +totalWeightCapacity + ".");
            return;
        }

        // Try stacking the item
        boolean itemHasBeenStacked = stackItemToInventory(item);

        if (usedSlots >= totalSlots && !itemHasBeenStacked) {
            OutputDevice.printMessage("Cannot add this item to your inventory. All " + totalSlots + "/" +totalSlots + " slots are occupied.");
            return;
        }

        if (itemHasBeenStacked) {
            OutputDevice.printMessage("Item has been stacked to your inventory.");
        } else {
            // The item is not stackable but can still be added to its own slot
            items.add(new ItemAndCountPair(item, 1));
            currentWeightCapacity += item.getItemWeight();
            usedSlots++;
            OutputDevice.printMessage("Item added to inventory in its own slot.");
        }
    }

    public boolean emptyInventoryQuery(){
        return items.isEmpty();
    }


    private void printItemWithCounting(ItemAndCountPair itemPair) {
        OutputDevice.printMessage("-----------------------------------------------------------------------------------------------------------");

        int instances = itemPair.value;
        if(instances>1) // if the items are stacked
        {
            OutputDevice.printMessageWithoutNewLine("x" + instances + " ");
        }
        OutputDevice.printMessage(itemPair.key.getItemInformation());

        OutputDevice.printMessage("-----------------------------------------------------------------------------------------------------------");
    }

    public void removeItemFromInventory(Item item) {
        int indexOfItem = retreiveIndexOfItem(item);
        if(indexOfItem!=-1)
        {
            if(items.get(indexOfItem).value>1) // Item to be removed is stacked
                items.get(indexOfItem).value--;
            else if(items.get(indexOfItem).value==1)
                items.remove(indexOfItem);

            OutputDevice.printMessage("Item has been removed from your inventory");
        }
        else
            OutputDevice.printMessage("No such item exists in your inventory");
    }

    private void equipWeapon(String weaponName){
        weaponName = weaponName.toLowerCase();
        boolean weaponHasBeenEquipped = false;
        // look for weapon within inventory based on name
        for(ItemAndCountPair itemPair : items)
        {
            //if weaponName equals items name then equip weapon
            //first get item's name
            Item itemFromInventory = itemPair.key;
            String itemFromInventoryName = itemFromInventory.getItemName();

            if (weaponName.equals(itemFromInventoryName.toLowerCase()))
            {
                // Verify that item is a weapon
                // fetch the item as a Weapon not as an Item
                if(itemFromInventory instanceof Weapon)
                {
                    character.equipWeapon((Weapon) itemFromInventory);
                    weaponHasBeenEquipped = true;
                    OutputDevice.printMessage(itemFromInventoryName + " has been eqquiped.");
                    break;
                }
                else // Item is not a weapon
                {
                    OutputDevice.printMessage("Your input matches an item from your inventory, but it is not a weapon...");
                }
            }
        }
        if (weaponHasBeenEquipped == false)
        {
            OutputDevice.printMessage("Your input does not match to any weapon in your inventory!");
        }
    }

    private void usePotion(String potionName){
        potionName = potionName.toLowerCase();
        boolean potionHasBeenConsumed = false;
        // look for potion within inventory based on name
        for(ItemAndCountPair itemPair : items)
        {
            //first get item's name
            Item itemFromInventory = itemPair.key;
            String itemFromInventoryName = itemFromInventory.getItemName();
            //if potionName equals item's name then consume potion
            if (potionName.equals(itemFromInventoryName.toLowerCase()))
            {
                // Verify that item is a potion
                // fetch the item as a Potion not as an Item
                if(itemFromInventory instanceof Potion)
                {
                    Potion potionToBeConsumed =  (Potion) itemFromInventory; // Casting Item to Potion so it can be passed as Potion, not Item, to other methods
                    character.drinkPotion(potionToBeConsumed);
                    // Delete potion instance from inventory. Case where it is and it is not stacked.
                    removeItemFromInventory(itemFromInventory);
                    potionHasBeenConsumed = true;
                    OutputDevice.printMessage(itemFromInventoryName + " has been consumed.");
                    break;
                }
                else // Item is not a potion
                {
                    OutputDevice.printMessage("Your input matches an item from your inventory, but it is not a potion...");
                    break;
                }
            }
        }
        if (potionHasBeenConsumed == false)
        {
            OutputDevice.printMessage("Your input does not match to any potion in your inventory!");
        }
    }

    public void displayInventory()
    {
        if(emptyInventoryQuery())
        {
            OutputDevice.printMessage("--------------------------");
            OutputDevice.printMessage("NO ITEMS IN YOUR INVENTORY");
            OutputDevice.printMessage("--------------------------");
            return;
        }

        while(true)
        {
            OutputDevice.printMessage("-------------------------");
            OutputDevice.printMessage("Press:");
            OutputDevice.printMessage("1. To Display Inventory");
            OutputDevice.printMessage("2. To Display Parts of your Inventory");
            OutputDevice.printMessage("3. To Equip a Weapon");
            OutputDevice.printMessage("4. Use a potion");
            OutputDevice.printMessage("9. To Exit Inventory");
            OutputDevice.printMessage("-------------------------");

            Object input = InputDevice.read("int");

            if(input.equals(1))
            {
                OutputDevice.printMessage("==============================================INVENTORY==============================================");

                for(ItemAndCountPair itemPair : items)
                    printItemWithCounting(itemPair);

                OutputDevice.printMessage("====================================================================================================");
                OutputDevice.printMessage("Slots Available: " + usedSlots + "/" + totalSlots);
                OutputDevice.printMessage("Weight Capacity: " + currentWeightCapacity + "/" + totalWeightCapacity);
                OutputDevice.printMessage("====================================================================================================");
            }
            else if(input.equals(2))
            {
                OutputDevice.printMessage("Press:");
                OutputDevice.printMessage("1 To display weapons");
                OutputDevice.printMessage("2. To display potions");

                Object input2  = InputDevice.read("int");

                if (input2.equals(1))
                {
                    OutputDevice.printMessage("==========INVENTORY==========");
                    OutputDevice.printMessage("===========WEAPONS===========");
                    Stream<ItemAndCountPair> itemsStream = items.stream();
                    itemsStream.filter(item -> item.key instanceof Weapon).sorted().forEach(item -> printItemWithCounting(item));
                    OutputDevice.printMessage("=============================");

                }
                else if (input2.equals(2))
                {
                    OutputDevice.printMessage("==========INVENTORY==========");
                    OutputDevice.printMessage("===========POTIONS===========");
                    Stream<ItemAndCountPair> itemsStream = items.stream();
                    itemsStream.filter(item -> item.key instanceof Potion).sorted().forEach(item -> printItemWithCounting(item));
                    OutputDevice.printMessage("=============================");
                }
                else
                {
                    OutputDevice.printMessage("Invalid input");
                }

            }
            else if(input.equals(3)) // Equip a weapon
            {
                OutputDevice.printMessage("==========INVENTORY==========");
                OutputDevice.printMessage("===========WEAPONS===========");
                Stream<ItemAndCountPair> itemsStream = items.stream();
                itemsStream.filter(item -> item.key instanceof Weapon).sorted().forEach(item -> printItemWithCounting(item));
                OutputDevice.printMessage("=============================");
                OutputDevice.printMessageWithoutNewLine("What weapon do you want to equip? Enter the name of corresponding weapon: ");
                InputDevice.consumeRemainingInput();
                String weaponTextInput = InputDevice.read("String").toString();
                equipWeapon(weaponTextInput);
            }
            else if(input.equals(4)) // Use a potion
            {
                OutputDevice.printMessage("==========INVENTORY==========");
                OutputDevice.printMessage("===========POTIONS===========");
                Stream<ItemAndCountPair> itemsStream = items.stream();
                itemsStream.filter(item -> item.key instanceof Potion).sorted().forEach(item -> printItemWithCounting(item));
                OutputDevice.printMessage("=============================");
                OutputDevice.printMessageWithoutNewLine("What potion do you want to use? Enter the name of corresponding potion: ");
                InputDevice.consumeRemainingInput();
                String potionTextInput = InputDevice.read("String").toString();
                usePotion(potionTextInput);
            }
            else if(input.equals(9))
            {
                // Consume The NewLine Character from the input
                InputDevice.consumeRemainingInput();
                break;
            }
            else OutputDevice.printMessage("Invalid input.");
        }
    }
}
