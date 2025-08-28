package com.example.PIIIProjectGUI.implementation.classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;

public class HolySword extends Weapon {

    public HolySword() {
        this.weaponName = "HolySword";
        this.weaponItemQuality = 4;
        this.weaponDamage = 64;
        this.weight = 21;
        this.color = WeaponRarityColors.ORANGE;
    }

    @Override
    public String getItemDescription() {
        return "The Holy Sword. It's Holy.";
    }
}
