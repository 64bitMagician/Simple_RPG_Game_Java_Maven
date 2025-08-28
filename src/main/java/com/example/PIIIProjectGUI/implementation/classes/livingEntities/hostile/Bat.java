package com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile;

import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses.Enemy;

public class Bat extends Enemy {
    //DEFAULT CONSTRUCTORS
    public Bat() {
        this.name = "Bat";
        this.totalHealth = 25;
        this.health = 25;
        this.damage = 10;
        this.alive = true;
        this.level = 1;
    }

    //COPY CONSTRUCTOR
    public Bat(Goblin other){
        this.health = other.health;
        this.damage = other.damage;
        this.alive = other.alive;
        this.level = other.level;
    }
}
