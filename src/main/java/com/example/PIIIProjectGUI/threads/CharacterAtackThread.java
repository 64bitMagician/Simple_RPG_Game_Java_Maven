package com.example.PIIIProjectGUI.threads;

import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

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
        OutputDevice.printMessage("-------------------------------------------------------");
        OutputDevice.printMessage("The character attacks " + enemy.name + " " + (enemyIndex+1) + " from the army.");
        enemy.health-=character.totalDamage;
        OutputDevice.printMessage("The " + enemy.name +" takes damage "+character.totalDamage+" damage.");
        OutputDevice.printMessage(enemy.returnHealth());

        if(enemy.health<=0) // goblin dies
        {
            enemy.alive=false; //! why does this set all object to be not alive. They share the same .alive... Why?
            OutputDevice.printMessage("The " + enemy.name + " has died");
        }
    }
}
