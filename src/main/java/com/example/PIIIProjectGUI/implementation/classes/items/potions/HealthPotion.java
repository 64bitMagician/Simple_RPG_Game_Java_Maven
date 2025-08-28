package com.example.PIIIProjectGUI.implementation.classes.items.potions;

//THIS IS CLASS 'E'


import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class HealthPotion extends Potion {

    public HealthPotion() {
        this.potionName = "HealthPotion";
        this.potionItemQuality = 1;
        this.weight = 2.0f;
        this.color = PotionRarityColors.GREEN;
    }

    @Override
    public String getItemDescription() {
        return "This is a HealthPotion. It will grant you 50 HP. Be happy. Cause you had not died yet.";
    }

    @Override
    public void runEffectsOfPotion(Character character) {
        character.increaseHealth(50);
        OutputDevice.printMessage("You gained 50 HP");
    }
}
