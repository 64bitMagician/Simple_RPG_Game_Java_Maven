package com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses;

import com.example.PIIIProjectGUI.implementation.interfaces.Item;

public abstract class Weapon implements Item, Comparable<Weapon> {
    //DATA
    protected String weaponName;
    protected float weight;
    protected int weaponItemQuality;
    public float weaponDamage;

    @Override
    public int compareTo(Weapon other) {
        if(this.weaponItemQuality > other.weaponItemQuality)
            return 1;
        else if(this.weaponItemQuality < other.weaponItemQuality)
            return -1;
        else // Weapon Item Quallity is equal
        {
            if(this.weaponDamage > other.weaponDamage)
                return 1;
            else if(this.weaponDamage < other.weaponDamage)
                return -1;
            else // ItemQuality and Weapon Damage of compared objects is equal
            {
                if(this.weight > other.weight)
                    return 1;
                else if(this.weight < other.weight)
                    return -1;
            }
        }

        // Compared Objects are equal
        return 0;
    }

    @Override
    public float getItemWeight() {
        return weight;
    }

    @Override
    public String getItemInformation() {
        return weaponName + " | " + "IQ: " +weaponItemQuality + " | " + "D: " +weaponDamage + " | " + "W: " + weight + "\nITEM DESCRIPTION: " + getItemDescription();
    }

    //ABSTRACT METHODS
    @Override
    public abstract String getItemDescription();

    @Override
    public String toString() {
        String string;
        string = "--------------------------------------------------------------------------\n";
        string += weaponName + " | " + "IQ: " +weaponItemQuality + " | " + "D: " +weaponDamage + " | " + "W: " + weight + "\nITEM DESCRIPTION: " + getItemDescription() + "\n";
        string += "--------------------------------------------------------------------------";
        return string;
    }

    @Override
    public String getItemName() {
        return weaponName;
    }
}
