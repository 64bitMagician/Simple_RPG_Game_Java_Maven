package com.example.PIIIProjectGUI.implementation.classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;

public class GoldSlingshot extends Weapon {

    public GoldSlingshot() {
        this.weaponName = "GoldSlingshot";
        this.weaponItemQuality = 3;
        this.weaponDamage = 40;
        this.weight = 8;
        this.color = WeaponRarityColors.RED;
    }

    @Override
    public String getItemDescription() {
        return "A slingshot made of gold. Useful for ranged attacks.";
    }
}
