package com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses;

import com.example.PIIIProjectGUI.implementation.interfaces.LivingEntity;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public abstract class Enemy implements LivingEntity, Comparable<Enemy> {
    public String name;
    public float totalHealth;
    public float health;
    public float damage;
    public boolean alive;
    public int level;

    // implementing interface LivingEntity to display health
    @Override
    public String returnHealth() {
        return name + "'s Health: " + health + "/" + totalHealth;
    }

    @Override
    public void displayStats() {
        OutputDevice.printMessage("-------ENEMY-STATS--------");
        OutputDevice.printMessage("Health: " + health + "/" + totalHealth);
        OutputDevice.printMessage("Damage: " + damage);
        OutputDevice.printMessage("Alive: " + alive);
    }

    @Override
    public int compareTo(Enemy other) {
        if(this.level > other.level)
            return 1;
        else if(this.level < other.level)
            return -1;

        return 0;
    }
}
