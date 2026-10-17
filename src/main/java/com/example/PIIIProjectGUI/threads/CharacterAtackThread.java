package com.example.PIIIProjectGUI.threads;

import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;

import java.util.concurrent.atomic.AtomicBoolean;

public class CharacterAtackThread extends Thread{
    //DATA
    public Enemy enemy;
    public int enemyIndex;
    public Character character;
    public AtomicBoolean ongoingBattle;

    public CharacterAtackThread(Enemy enemyReceived, int enemyIndexReceived, Character characterReceived, AtomicBoolean ongoingBattleReceived){
        this.enemy = enemyReceived;
        this.enemyIndex = enemyIndexReceived;
        this.character = characterReceived;
        this.ongoingBattle = ongoingBattleReceived;
    }

    @Override
    public void run() {
        characterAttacks();
    }



    public void characterAttacks() {
        enemy.health-=character.totalDamage;

        if(enemy.health<=0) // goblin dies
        {
            enemy.alive=false; //! why does this set all object to be not alive. They share the same .alive... Why?
        }
    }
}
