package com.example.PIIIProjectGUI.implementation.classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;

public class DiamondSlingshot extends Weapon {

    public DiamondSlingshot() {
        this.weaponName = "DiamondSlingshot";
        this.weaponItemQuality = 4;
        this.weaponDamage = 50;
        this.weight = 10;
        this.color = WeaponRarityColors.ORANGE;
    }

    @Override
    public String getItemDescription() {
        return "A slingshot made of diamond. Useful for ranged attacks.";
    }
}
