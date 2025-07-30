package com.example.PIIIProjectGUI.implementation.Classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;

public class WoodenSlingshot extends Weapon {

    public WoodenSlingshot() {
        this.weaponName = "Wooden Slingshot";
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
