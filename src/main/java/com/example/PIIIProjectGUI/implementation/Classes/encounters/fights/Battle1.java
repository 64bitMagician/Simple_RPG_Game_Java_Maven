package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights;

import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.Bat;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;

public class Battle1 extends ThreadedBattle {
    //CONSTRUCTORS
    public Battle1(Character character, InventoryController inventory) {
        this.character = character;
        this.inventory = inventory;
        this.enemyArmy.add(new Bat());
        this.enemyArmy.add(new Bat());
        this.enemyArmy.add(new Bat());
        this.ongoingBattle.set(true);
    }
}
