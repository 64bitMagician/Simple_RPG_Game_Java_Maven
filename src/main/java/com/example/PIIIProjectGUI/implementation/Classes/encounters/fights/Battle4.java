package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.GoldenBat;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.Mutant;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;

public class Battle4 extends ThreadedBattle {
    //CONSTRUCTORS
    public Battle4(Character character, InventoryController inventory) {
        this.character = character;
        this.inventory = inventory;
        this.enemyArmy.add(new Mutant());
        this.enemyArmy.add(new GoldenBat());
        this.ongoingBattle.set(true);
    }
}
