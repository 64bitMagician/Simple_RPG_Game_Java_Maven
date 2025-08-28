package com.example.PIIIProjectGUI.implementation.classes.items.potions;

import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class GiantHealthPotion extends Potion {


    public GiantHealthPotion() {
        this.potionName = "GiantHealthPotion";
        this.potionItemQuality = 3;
        this.weight = 4.0f;
        this.color = PotionRarityColors.RED;
    }

    @Override
    public String getItemDescription() {
        return "This is a Giant Health Potion. Crafted by the Giants of the Mountains. It will grant you 200 HP. Be happy. Cause you had not died yet.";
    }

    @Override
    public void runEffectsOfPotion(Character character) {
        character.increaseHealth(200);
        OutputDevice.printMessage("You gained 200 HP");

    }
}
