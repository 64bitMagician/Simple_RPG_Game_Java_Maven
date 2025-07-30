package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.Goblin;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;

public class Battle2 extends ThreadedBattle {
    //CONSTRUCTORS
    public Battle2(Character character, InventoryController inventory) {
        this.character = character;
        this.inventory = inventory;
        this.enemyArmy.add(new Goblin());
        this.enemyArmy.add(new Goblin());
        this.ongoingBattle.set(true);
    }
}
