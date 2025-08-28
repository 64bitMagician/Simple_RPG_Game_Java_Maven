package com.example.PIIIProjectGUI.implementation.classes.encounters.fights;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.Bat;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;

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
