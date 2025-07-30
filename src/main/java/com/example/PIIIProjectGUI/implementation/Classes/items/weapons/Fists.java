package com.example.PIIIProjectGUI.implementation.Classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;

public class Fists extends Weapon {

    public Fists() {
        this.weaponName = "Fists";
        this.weaponItemQuality = 1;
        this.weaponDamage = 5;
        this.weight = 0;
        this.color = WeaponRarityColors.GREEN;
    }

    @Override
    public String getItemDescription() {
        return "You have nothing upon you but your fists.";
    }
}
