package com.example.PIIIProjectGUI.implementation.Classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;

public class Club extends Weapon {

    public Club() {
        this.weaponName = "Club";
        this.weaponItemQuality = 1;
        this.weaponDamage = 20;
        this.weight = 15;
        this.color = WeaponRarityColors.GREEN;
    }

    @Override
    public String getItemDescription() {
        return "You probably found this on the ground";
    }
}
