package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.exceptions.NullException;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.interfaces.Item;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;
import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import com.example.PIIIProjectGUI.utilities.yesNoQuery;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InventoryController {
    static Inventory inventory = GameState.inventory;

    @FXML
    private VBox itemsContainer;

    private Weapon getEquippedWeapon() {
        return GameState.character.equippedWeapon;   // adapt to your API
    }

    // Logic & Data Methods

    //The retreiveIndexOfItem(Item item) method returns
    // the index of the object within the list,
    // that maches the class type of
    // the object that you are passing as an argument.
    // If no match is found, -1 will be returned
    static private int retreiveIndexOfItem(Item item) {
        int index = 0;
        for(ItemAndCountPair i : inventory.items) {
            if(i.key.getClass() == item.getClass()) { // the type of given object matches the type of an object within the inventory
                return index;
            }
            index++;
        }
        return -1;
    }

    static public List<ItemAndCountPair> returnAllWeaponsFromInventory(){
        List<ItemAndCountPair> returnedWeapons = new ArrayList<>();
        Stream<ItemAndCountPair> weaponsStream = inventory.items.stream();
        weaponsStream.filter(item -> item.key instanceof Weapon).sorted().forEach(item -> returnedWeapons.add(item));
        return returnedWeapons;
    }

    static public void eraseInventory(){
        inventory.items.clear();
        inventory.currentWeightCapacity = 0;
        inventory.usedSlots = 0;
    }

    static public List<ItemAndCountPair> returnAllPotionsFromInventory(){
        List<ItemAndCountPair> returnedPotions = new ArrayList<>();
        Stream<ItemAndCountPair> potionsStream = inventory.items.stream();
        potionsStream.filter(item -> item.key instanceof Potion).sorted().forEach(item -> returnedPotions.add(item));
        return returnedPotions;
    }



    static private int getHowManyItemInstancesYouCanAddToInventoryBasedOnWeight(Item itemInstance, int numberOfItemInstances)
    {
        float itemInstanceWeight = itemInstance.getItemWeight();
        float totalWeightOfItemInstances = itemInstanceWeight*numberOfItemInstances;

        float maxPossibleInventoryCapacity = inventory.currentWeightCapacity + totalWeightOfItemInstances;
        int maxNumberOfInstancesYouCanAdd = numberOfItemInstances;

        while(maxPossibleInventoryCapacity > inventory.totalWeightCapacity)
        {
            maxPossibleInventoryCapacity -= itemInstanceWeight;
            maxNumberOfInstancesYouCanAdd--;
        }

        return maxNumberOfInstancesYouCanAdd;
    }

    static private int getHowManyItemInstancesYouCanAddToInventoryBasedOnWeight(ItemAndCountPair itemAndCountPair)
    {
        Item itemInstance = itemAndCountPair.key;
        int numberOfItemInstances = itemAndCountPair.value;

        return getHowManyItemInstancesYouCanAddToInventoryBasedOnWeight(itemInstance, numberOfItemInstances);
    }

    static private boolean stackItemToInventory(Item item, int itemInstancesToStack){
        float inentoryCurrentWeightCapacity = inventory.currentWeightCapacity;
        float inventoryTotalWeightCapacity = inventory.totalWeightCapacity;
        float totalItemAndCountPairWeightCapacity = item.getItemWeight()*itemInstancesToStack;

        //Check if he item already appears in your inventory, so you can stack it
        //trebuie referinta sau doar clasa
        int indexOfItem = retreiveIndexOfItem(item);

        if(indexOfItem>=0) // An instance of the item you want to add already exists in your inventory, so you can stack it
        {
            if (inentoryCurrentWeightCapacity + totalItemAndCountPairWeightCapacity > inventoryTotalWeightCapacity)
            {
                // But what is the max number of items you can add from the number of instances
                int maxNumberOfItemInstancesYouCanStack = getHowManyItemInstancesYouCanAddToInventoryBasedOnWeight(item, itemInstancesToStack);

                if(maxNumberOfItemInstancesYouCanStack == 0) // Even one item is too heavy to be added to inventory
                {
                    OutputDevice.printMessage("You cannot stack any of items because it will exceed your weight capacity...");
                    return false;
                }

                boolean addAsManyAsPossible = yesNoQuery.query("Too much weight! You cannot stack all that items to your inventory. Want to add as many as possible?");

                if (addAsManyAsPossible)
                {
                    inventory.items.get(indexOfItem).value+=maxNumberOfItemInstancesYouCanStack;
                    inventory.currentWeightCapacity += item.getItemWeight()*maxNumberOfItemInstancesYouCanStack;
                    return true;
                }
                else
                {
                    OutputDevice.printMessage("You hadn't stock any of the items to inventory...");
                    return false;
                }
            }
            else // There is no concern of weight
            {
                inventory.items.get(indexOfItem).value+=itemInstancesToStack;
                inventory.currentWeightCapacity += totalItemAndCountPairWeightCapacity;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    static public void addItemToInventory(Item item) {
        if (item==null)
            new NullException("The 'item' parameter in method addItemToInventory, from InventoryController.java is null");

        if (inventory.currentWeightCapacity + item.getItemWeight() > inventory.totalWeightCapacity) {
            OutputDevice.printMessage("Cannot add this item to your inventory. It would exceed the total weight capacity. Current Weight Cappacity: " + inventory.currentWeightCapacity + "/" + inventory.totalWeightCapacity + ".");
            return;
        }

        // Try stacking the item
        boolean itemHasBeenStacked = stackItemToInventory(item, 1);

        if (inventory.usedSlots >= inventory.totalSlots && !itemHasBeenStacked) {
            OutputDevice.printMessage("Cannot add this item to your inventory. All " + inventory.totalSlots + "/" + inventory.totalSlots + " slots are occupied.");
            return;
        }

        if (itemHasBeenStacked) {
            OutputDevice.printMessage(item.getItemName() + " has been stacked to your inventory.");
        } else {
            // The item is not stackable but can still be added to its own slot
            inventory.items.add(new ItemAndCountPair(item, 1));
            inventory.currentWeightCapacity += item.getItemWeight();
            inventory.usedSlots++;
            OutputDevice.printMessage(item.getItemName() + " added to inventory in its own slot.");
        }
    }

    static public void addItemToInventory(ItemAndCountPair itemAndCountPair){
        // get Items Weight. How many instances * weight of an instance
        float totalItemAndCountPairWeight = itemAndCountPair.value * itemAndCountPair.key.getItemWeight();
        if (inventory.currentWeightCapacity + totalItemAndCountPairWeight > inventory.totalWeightCapacity) {
            OutputDevice.printMessage("Cannot add this many items to your inventory. It would exceed the total weight capacity. Current weight capacity: " + inventory.currentWeightCapacity + "/" + inventory.totalWeightCapacity + ".");
            return;
        }

        // Try stacking the item
        boolean itemHasBeenStacked = stackItemToInventory(itemAndCountPair.key, itemAndCountPair.value);

        if (inventory.usedSlots >= inventory.totalSlots && !itemHasBeenStacked) {
            OutputDevice.printMessage("Cannot add this item to your inventory. All " + inventory.totalSlots + "/" + inventory.totalSlots + " slots are occupied.");
            return;
        }

        if (itemHasBeenStacked) {
            OutputDevice.printMessage("Item has been stacked to your inventory.");
        } else {
            // The item is not stackable but can still be added to its own slot
            inventory.items.add(itemAndCountPair);
            inventory.currentWeightCapacity += totalItemAndCountPairWeight;
            inventory.usedSlots++;
            OutputDevice.printMessage("Item added to inventory in its own slot.");
        }
    }

    static public boolean emptyInventoryQuery(){
        return inventory.items.isEmpty();
    }


    static private void printItemWithCounting(ItemAndCountPair itemPair) {
        OutputDevice.printMessage("-----------------------------------------------------------------------------------------------------------");

        int instances = itemPair.value;
        if(instances>1) // if the items are stacked
        {
            OutputDevice.printMessageWithoutNewLine("x" + instances + " ");
        }
        OutputDevice.printMessage(itemPair.key.getItemInformation());

        OutputDevice.printMessage("-----------------------------------------------------------------------------------------------------------");
    }

    static public void removeItemFromInventory(Item item) {
        int indexOfItem = retreiveIndexOfItem(item);
        if(indexOfItem!=-1)
        {
            if(inventory.items.get(indexOfItem).value>1) // Item to be removed is stacked
                inventory.items.get(indexOfItem).value--;
            else if(inventory.items.get(indexOfItem).value==1)
                inventory.items.remove(indexOfItem);

            OutputDevice.printMessage("Item has been removed from your inventory");
        }
        else
            OutputDevice.printMessage("No such item exists in your inventory");
    }

    public void updateDisplay(List<ItemAndCountPair> items) {
        if (itemsContainer == null) {
            OutputDevice.printMessage("itemsContainer is not injected. Check FXML fx:id.");
            return;
        }

        itemsContainer.getChildren().clear();
        for (ItemAndCountPair item : items) {
            HBox itemBox = createItemBox(item);
            itemsContainer.getChildren().add(itemBox);
        }
    }

    @FXML
    public void initialize() {
        updateDisplay(inventory.items);  // Auto-display items at startup
    }


    private HBox createItemBox(ItemAndCountPair pair) {

        Item   item  = pair.key;
        int    count = pair.value;

        // ░░  rarity colour square ░░
        Region rarity = new Region();
        rarity.setPrefSize(20, 20);
        rarity.setStyle("-fx-background-color:" + item.getItemRarityColor() +
                "; -fx-border-color:black;");

        // ░░  name + stack count ░░
        Label name = new Label(item.getItemName() + " x" + count);
        name.setStyle("-fx-font-size:14; -fx-font-weight:bold;");

        // ░░  equipped icon (added later if needed) ░░
        Label equippedIcon = new Label("⚔");   // U+2694 CROSSED SWORDS
        equippedIcon.setStyle("-fx-font-size:16;");
        equippedIcon.setVisible(false);        // default: hidden

        HBox box = new HBox(10, rarity, name, equippedIcon);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-background-color:#f0f0f0; -fx-border-color:gray; -fx-border-radius:5;");

        /* ----------  highlight if this weapon is the equipped one  ---------- */
        if (item instanceof Weapon weapon && weapon == getEquippedWeapon()) {
            equippedIcon.setVisible(true);
            box.setStyle("-fx-background-color:#d9ffd9; -fx-border-color:gray; -fx-border-radius:5;");
        }

        /* ----------  click behaviour  ---------- */
        box.setOnMouseClicked(e -> {

            if (item instanceof Weapon weapon) {                           // equip
                inventory.character.equipWeapon(weapon);
                OutputDevice.printMessage(weapon.getItemName() + " equipped.");
                updateDisplay(inventory.items);
                return;
            }

            if (item instanceof Potion potion) {                           // use
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Use " + potion.getItemName() + "?",
                        ButtonType.OK, ButtonType.CANCEL);
                alert.setHeaderText(null);
                alert.setTitle("Use Potion");
                if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                    inventory.character.drinkPotion(potion);
                    OutputDevice.printMessage(potion.getItemName() + " used.");
                    updateDisplay(inventory.items);
                    ExploreController.updateAllUI();
                }
            }
        });

        return box;
    }


    @FXML
    private void handleDisplayWeapons() {
        List<ItemAndCountPair> weapons = inventory.items.stream()
                .filter(i -> i.key instanceof Weapon)
                .toList();
        updateDisplay(weapons);
    }

    @FXML
    private void handleDisplayPotions() {
        List<ItemAndCountPair> potions = inventory.items.stream()
                .filter(i -> i.key instanceof Potion)
                .toList();
        updateDisplay(potions);
    }

    @FXML
    private void handleExit() {
        int numberOfStagesInStack = ViewNavigator.getNumberOfStagesInStack();

        if (numberOfStagesInStack == 1)
        {
            ViewNavigator.switchScene("main-menu.fxml");
        }
        else if (numberOfStagesInStack == 2)
        {
            ViewNavigator.closeTopStage();
        }
    }
}
