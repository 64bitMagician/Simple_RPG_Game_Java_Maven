package com.example.PIIIProjectGUI.implementation.Classes.gameState;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
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
    public InventoryController inventoryController;
    public float totalWeightCapacity = 1000;
    public float currentWeightCapacity = 0;
    public int totalSlots = 20;
    public int usedSlots = 0;
    //    private List<Item> itemInstanceRow = new ArrayList<>();
    //    private List<Integer> itemCountRow = new ArrayList<>();
    public List<ItemAndCountPair> items = new ArrayList<>();

    public Character character;

    // Private constructor to prevent instantiation
    public Inventory(Character characterReceived) {
        this.character = characterReceived;
    }
}
