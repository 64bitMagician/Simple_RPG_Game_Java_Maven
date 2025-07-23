package com.example.PIIIProjectGUI.GUI.utilities;

import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Club;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;

public class GameState {

    public static Character character;
    public static Inventory inventory;
    public static Explore exploration;

    public GameState() {} // private constructor for singleton

    public static void createCharacter(String name) {
        //Default Items
        Weapon fists = new Fists();
        Weapon startingWeapon = new Club();

        character = new Character(name, 500, 1, startingWeapon);

        inventory = new Inventory(character);
        exploration = new Explore(character, inventory);

        inventory.addItemToInventory(fists);
        inventory.addItemToInventory(startingWeapon);
    }
}
