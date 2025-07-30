package com.example.PIIIProjectGUI.GUI.utilities;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Club;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;

public class GameState {
    //PROGRAM ARGUMENTS
    public static String[] args;

    public static Character character;
    public static Inventory inventory;
    public static Explore exploration;

    public GameState() {} // it will be used as a single instance object (singleton)

}
