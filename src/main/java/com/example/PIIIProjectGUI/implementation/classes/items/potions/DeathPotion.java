package com.example.PIIIProjectGUI.implementation.classes.items.potions;

//THIS IS CLASS 'D'

import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;

public class DeathPotion extends Potion {

    public DeathPotion() {
        this.potionName = "DeathPotion";
        this.potionItemQuality = 1;
        this.weight = 10.0f;
        this.color = PotionRarityColors.GREEN;
    }

    @Override
    public String getItemDescription() {
        return "This is a DeathPotion. Kill Yourself While It's not Too Late...";
    }

    @Override
    public void runEffectsOfPotion(Character character) {
        character.characterDeathUI();
    }
}
