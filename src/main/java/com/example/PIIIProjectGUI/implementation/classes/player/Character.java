package com.example.PIIIProjectGUI.implementation.classes.player;

// THIS IS CLASS 'A'


import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.interfaces.LivingEntity;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.animation.PauseTransition;
import javafx.stage.Window;
import javafx.util.Duration;

import javax.swing.text.View;
import java.io.IOException;
import java.util.ArrayList;

public class Character implements LivingEntity {
    public int characterID;
    public String name;
    public float health;
    public float totalHealth;
    public boolean alive;
    public Weapon equippedWeapon;
    public float damageMultiplier;
    public float totalDamage;

    //Constructor
    public Character(String name,float health, float totalHealth, float damageMultiplier, Weapon equippedWeapon) {
        this.name = name;
        this.health = health;
        this.totalHealth = totalHealth;
        this.alive = true;
        this.equippedWeapon = equippedWeapon;
        this.damageMultiplier = damageMultiplier;
        if (equippedWeapon!=null)
            this.totalDamage = equippedWeapon.weaponDamage*damageMultiplier;
        else
            this.totalDamage = 0;
    }

    //Copy Constructor
    public Character(Character other) {
        this.characterID = characterID;
        this.name = name;
        this.health = health;
        this.totalHealth = totalHealth;
        this.alive = true;
        this.equippedWeapon = equippedWeapon;
        this.damageMultiplier = damageMultiplier;
        if (equippedWeapon!=null)
            this.totalDamage = equippedWeapon.weaponDamage*damageMultiplier;
        else
            this.totalDamage = 0;
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

        OutputDevice.printMessage(weaponToEquip.getItemName() + " equipped.");
    }

    public void drinkPotion(Potion potion){
        potion.runEffectsOfPotion(this);
        InventoryController.removeItemFromInventory(potion);
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

    public void characterDeathUI() {
        this.alive = false;

        // reset game over

        GameState.gameProgressReset();

        ViewNavigator.replaceAllStageWindowsWithASingleOne("youDied-view.fxml", "Victory");

        // Pause for 3 seconds before exiting
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> System.exit(0));
        delay.play();
    }

}
