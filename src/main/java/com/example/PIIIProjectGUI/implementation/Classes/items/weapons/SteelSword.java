package com.example.PIIIProjectGUI.implementation.Classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;

public class SteelSword extends Weapon {

    public SteelSword() {
        this.weaponName = "Steel Sword";
        this.weaponItemQuality = 2;
        this.weaponDamage = 35;
        this.weight = 21;
        this.color = WeaponRarityColors.BLUE;
    }

    @Override
    public String getItemDescription() {
        return "This is the Steel Sword. The most basic sword you could possibly get... But it's very reliable";
    }
}
