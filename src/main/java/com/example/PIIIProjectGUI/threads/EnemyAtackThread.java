package com.example.PIIIProjectGUI.threads;

import com.example.PIIIProjectGUI.implementation.Classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnemyAtackThread extends Thread{
    //DATA
    public Enemy enemy;
    public int enemyIndex;
    public Character character;
    public AtomicBoolean ongoingBattle;

    public EnemyAtackThread(Enemy enemyReceived, int enemyIndexReceived, Character characterReceived, AtomicBoolean ongoingBattleReceived){
        this.enemy = enemyReceived;
        this.enemyIndex = enemyIndexReceived;
        this.character = characterReceived;
        this.ongoingBattle = ongoingBattleReceived;
    }

    @Override
    public void run() {
        enemyAttacks();
    }



    public void enemyAttacks() {
            OutputDevice.printMessage("-------------------------------------------------------");
            synchronized (this) {
                OutputDevice.printMessage("The " + (enemyIndex+1) + " enemy, the " + enemy.name + ", from the army attacks");
                character.health-=enemy.damage;
                OutputDevice.printMessage(character.name + " takes "+ enemy.damage + " damage.");
                OutputDevice.printMessage(character.returnHealth());
            }
            if(character.health<=0) // character dies
            {
                character.alive=false;
                OutputDevice.printMessage("YOU DIED");
                ongoingBattle.set(false);
                System.exit(0);
            }
            OutputDevice.printMessage("-------------------------------------------------------");
    }
}
