package com.example.PIIIProjectGUI.implementation.classes.gameState;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;

import java.util.ArrayList;
import java.util.List;

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
