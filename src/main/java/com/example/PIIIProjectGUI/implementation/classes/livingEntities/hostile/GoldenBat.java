package com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile;

import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses.Enemy;

public class GoldenBat extends Enemy {
    //DEFAULT CONSTRUCTORS
    public GoldenBat() {
        this.name = "Golden Bat";
        this.totalHealth = 35;
        this.health = 35;
        this.damage = 20;
        this.alive = true;
        this.level = 2;
    }

    //COPY CONSTRUCTOR
    public GoldenBat(Goblin other){
        this.health = other.health;
        this.damage = other.damage;
        this.alive = other.alive;
        this.level = other.level;
    }
}
