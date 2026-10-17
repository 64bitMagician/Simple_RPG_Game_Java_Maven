package com.example.PIIIProjectGUI.threads;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.classes.livingEntities.hostile.abstractClasses.Enemy;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;

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
            synchronized (this) {
                character.health-=enemy.damage;
            }
            if(character.health<=0) // character dies
            {
                ongoingBattle.set(false);
                character.characterDeathUI();
                GameState.gameProgressReset();
            }
    }
}
