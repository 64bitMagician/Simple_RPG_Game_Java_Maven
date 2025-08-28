package com.example.PIIIProjectGUI.implementation.classes.encounters.fights;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.Goblin;
import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.Mutant;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;

public class Battle5 extends ThreadedBattle {
    //CONSTRUCTORS
    public Battle5(Character character, InventoryController inventory) {
        this.character = character;
        this.inventory = inventory;
        this.enemyArmy.add(new Mutant());
        this.enemyArmy.add(new Mutant());
        this.enemyArmy.add(new Goblin());
        this.enemyArmy.add(new Goblin());
        this.enemyArmy.add(new Goblin());
        this.ongoingBattle.set(true);
    }
}
