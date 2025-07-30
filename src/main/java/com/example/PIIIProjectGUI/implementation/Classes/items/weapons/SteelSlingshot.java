package com.example.PIIIProjectGUI.implementation.Classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;

public class SteelSlingshot extends Weapon {

    public SteelSlingshot() {
        this.weaponName = "Steel Slingshot";
        this.weaponItemQuality = 2;
        this.weaponDamage = 30;
        this.weight = 6;
        this.color = WeaponRarityColors.BLUE;
    }

    @Override
    public String getItemDescription() {
        return "A slingshot made of steel. Useful for ranged attacks.";
    }
}
