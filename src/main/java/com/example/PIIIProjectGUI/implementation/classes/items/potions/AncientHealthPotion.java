package com.example.PIIIProjectGUI.implementation.classes.items.potions;

import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class AncientHealthPotion extends Potion {


    public AncientHealthPotion() {
        this.potionName = "AncientHealthPotion";
        this.potionItemQuality = 4;
        this.weight = 8.0f;
        this.color = PotionRarityColors.ORANGE;
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
