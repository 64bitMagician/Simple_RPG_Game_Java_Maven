package com.example.PIIIProjectGUI.implementation.Classes.items.potions;

import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class AncientHealthPotion extends Potion {


    public AncientHealthPotion() {
        this.potionName = "Ancient Health Potion";
        this.potionItemQuality = 4;
        this.weight = 8.0f;
        this.color = PotionColors.BLACK;
    }

    @Override
    public String getItemDescription() {
        return "This is a Ancient Health Potion. Not to be mistaken with the Death Potion, cause both are Black. Crafted 1000 years ago by who knows. It will grant you 500 HP. Be happy. Cause you had not died yet.";
    }

    @Override
    public void runEffectsOfPotion(Character character) {
        character.increaseHealth(500);
        OutputDevice.printMessage("You gained 500 HP");
    }
}
