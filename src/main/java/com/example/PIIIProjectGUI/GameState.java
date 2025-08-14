package com.example.PIIIProjectGUI;

import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;

public class GameState {
    //PROGRAM ARGUMENTS
    public static String[] args;

    public static Character character;
    public static Inventory inventory;
    public static Explore exploration;

    public GameState() {} // it will be used as a single instance object (singleton)

}
