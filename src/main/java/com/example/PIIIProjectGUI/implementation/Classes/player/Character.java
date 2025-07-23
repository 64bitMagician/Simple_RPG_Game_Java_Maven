package com.example.PIIIProjectGUI.implementation.Classes.player;

// THIS IS CLASS 'A'


import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.interfaces.LivingEntity;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class Character implements LivingEntity {
    public String name;
    public float totalHealth;
    public float health;
    public float damageMultiplier;
    public boolean alive;
    public Weapon equippedWeapon;
    public float totalDamage;

    //Constructor
    public Character(String name,int health,int damageMultiplier, Weapon startingWeapon) {
        this.name = name;
        this.totalHealth = health;
        this.health = health;
        this.damageMultiplier = damageMultiplier;
        this.alive = true;
        this.equippedWeapon = startingWeapon;
        this.totalDamage = equippedWeapon.weaponDamage*damageMultiplier;
    }

    //Copy Constructor
    public Character(Character other) {
        this.name = other.name;
        this.totalHealth = other.totalHealth;
        this.health = other.health;
        this.damageMultiplier = other.damageMultiplier;
        this.alive = other.alive;
        this.equippedWeapon = other.equippedWeapon;
        this.totalDamage = other.totalDamage;
    }

    //IMPLEMENTED METHODS

    @Override
    public void displayStats() {
        OutputDevice.printMessage("-------CHARACTER-STATS--------");
        OutputDevice.printMessage("Name: " + name);
        OutputDevice.printMessage("Health: " + health + "/" + totalHealth);
        OutputDevice.printMessage("Damage Multiplier: " + damageMultiplier);
        OutputDevice.printMessage("Alive: " + alive);
        OutputDevice.printMessage("EquippedWeapon: " + equippedWeapon.getItemName());
        OutputDevice.printMessage("Total Damage: " + totalDamage);
        OutputDevice.printMessage("------------------------------");
    }

    @Override
    public String returnHealth() {
        return name + "'s Health: " + health + "/" + totalHealth;
    }

    public void equipWeapon(Weapon weaponToEquip){
        equippedWeapon = weaponToEquip;
        totalDamage = equippedWeapon.weaponDamage*damageMultiplier;
    }

    public void drinkPotion(Potion potion){
        potion.runEffectsOfPotion(this);
    }

    public void increaseHealth(float hp){
        float outcomeHealth = this.health + hp;
        if(outcomeHealth>this.totalHealth) // if total possible health of the character is exceeded
        {
            this.health = this.totalHealth;
        }
        else
        {
            this.health = outcomeHealth;
        }
    }

    public void characterDeath(){
        this.alive = false;
        OutputDevice.printMessage("You Died");
        System.exit(0);
    }
}
