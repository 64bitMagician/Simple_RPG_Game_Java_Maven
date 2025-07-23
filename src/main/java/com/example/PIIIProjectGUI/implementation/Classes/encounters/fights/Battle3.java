package com.example.PIIIProjectGUI.implementation.Classes.encounters.fights;

import com.example.PIIIProjectGUI.implementation.Classes.encounters.fights.abstractclasses.ThreadedBattle;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.Goblin;
import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.GoldenBat;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;

public class Battle3 extends ThreadedBattle {
    //CONSTRUCTORS
    public Battle3(Character character, Inventory inventory) {
        this.character = character;
        this.inventory = inventory;
        this.enemyArmy.add(new Goblin());
        this.enemyArmy.add(new Goblin());
        this.enemyArmy.add(new GoldenBat());
        this.ongoingBattle.set(true);
    }
}
