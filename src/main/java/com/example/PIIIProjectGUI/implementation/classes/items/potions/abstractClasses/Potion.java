package com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses;

import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.implementation.interfaces.Item;

public abstract class Potion implements Item, Comparable<Potion> {
    //DATA
    public enum PotionRarityColors {GREEN,BLUE,RED,ORANGE}

    protected String potionName;
    protected int potionItemQuality;
    protected float weight;
    protected PotionRarityColors color;

    // ABSTRACT METHODS

    public abstract void runEffectsOfPotion(Character character);

    // FULL DEFINED METHODS

    public int getPotionItemQualityQuality(){
        return potionItemQuality;
    }

    @Override
    public int compareTo(Potion other) {
        if(this.potionItemQuality > other.potionItemQuality)
            return 1;
        else if(this.potionItemQuality < other.potionItemQuality)
            return -1;
        else // potionItemQuality of compared objects is equal
        {
            if(this.weight > other.weight)
                return 1;
            else if(this.weight < other.weight)
                return -1;
        }

        return 0;
    }

    @Override
    public String getItemRarityColor() {
        switch (color) {
            case GREEN -> { return "GREEN"; }
            case BLUE -> { return "BLUE"; }
            case RED -> { return "RED"; }
            case ORANGE -> { return "ORANGE"; }
        }
        return "GREY";
    }

    @Override
    public float getItemWeight() {
        return weight;
    }

    @Override
    public abstract String getItemDescription();

    @Override
    public String getItemInformation() {
        return potionName + " | C:  " + color.toString() + " | " + "IQ: " + potionItemQuality + " | " + "W: " + weight  + "\nITEM DESCRIPTION: " +  getItemDescription();
    }

    @Override
    public String toString() {
        String string;
        string = "--------------------------------------------------------------------------\n";
        string += potionName + " | C:  " + color.toString() + " | " + "IQ: " + potionItemQuality + " | " + "W: " + weight  + "\nITEM DESCRIPTION: " +  getItemDescription() + "\n";
        string += "--------------------------------------------------------------------------";
        return string;
    }

    @Override
    public String getItemName() {
        return potionName;
    }
}
