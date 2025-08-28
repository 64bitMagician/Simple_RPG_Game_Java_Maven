package com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile;



//THIS IS CLASS 'C'

import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses.Enemy;

public class Goblin extends Enemy {
    //DEFAULT CONSTRUCTORS
    public Goblin() {
        this.name = "Goblin";
        this.totalHealth = 50;
        this.health = 50;
        this.damage = 20;
        this.alive = true;
        this.level = 1;
    }

    //COPY CONSTRUCTOR
    public Goblin(Goblin other){
        this.health = other.health;
        this.damage = other.damage;
        this.alive = other.alive;
        this.level = other.level;
    }
}
