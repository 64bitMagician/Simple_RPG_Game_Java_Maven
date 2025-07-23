package com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile;

import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.abstractClasses.Enemy;

public class Mutant extends Enemy {
    //DEFAULT CONSTRUCTORS
    public Mutant() {
        this.name = "Mutant";
        this.totalHealth = 100;
        this.health = 100;
        this.damage = 50;
        this.alive = true;
        this.level = 1;
    }

    //COPY CONSTRUCTOR
    public Mutant(Goblin other){
        this.health = other.health;
        this.damage = other.damage;
        this.alive = other.alive;
        this.level = other.level;
    }
}
