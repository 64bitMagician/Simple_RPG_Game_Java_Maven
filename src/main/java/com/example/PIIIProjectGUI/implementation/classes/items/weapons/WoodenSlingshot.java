package com.example.PIIIProjectGUI.implementation.classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;

public class WoodenSlingshot extends Weapon {

    public WoodenSlingshot() {
        this.weaponName = "WoodenSlingshot";
        this.weaponItemQuality = 1;
        this.weaponDamage = 20;
        this.weight = 4;
        this.color = WeaponRarityColors.GREEN;
    }

    @Override
    public String getItemDescription() {
        return "A slingshot made of wood. Useful for ranged attacks.";
    }
}
