package com.example.PIIIProjectGUI.implementation.Classes.items.potions;

import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class GreatHealthPotion extends Potion {


    public GreatHealthPotion() {
        this.potionName = "Great Health Potion";
        this.potionItemQuality = 2;
        this.weight = 2.0f;
        this.color = PotionRarityColors.BLUE;
    }

    @Override
    public String getItemDescription() {
        return "This is a Great Health Potion. It will grant you 100 HP. Be happy. Cause you had not died yet.";
    }

    @Override
    public void runEffectsOfPotion(Character character) {
        character.increaseHealth(100);
        OutputDevice.printMessage("You gained 100 HP");

    }
}
