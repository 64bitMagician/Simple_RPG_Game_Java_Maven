package com.example.PIIIProjectGUI.app;

// I/O imports
import com.example.PIIIProjectGUI.UI.UIState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.SaveInventory;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Club;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.Fists;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.HolySword;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.inputoutput.InputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.RetreiveSavedInventory;

public class Game {
    //DATA
    Character character;
    Explore exploration;
    Inventory inventory;




    //CONSTRUCTORS
    public Game() {
    }





    //PRIVATE METHODS

    private void createCharacter(String[] args, Weapon startingWeapon){
        //      You can pass arguments to the program, such that you enter the game with a cheater run
        //  or with a normal run. [ "cheatMode" - cheat run, "default" - normal run   ]

        //  Character Creation
        if(args[0].equals("cheatMode"))
        {
            character = new Character("Cheater", 999, 999, new HolySword());
        }
        else if(args[0].equals("default"))
        {
            String name;
            while(true) // Loop Until Valid Name
            {
                OutputDevice.printMessage("Choose your Character Name: ");
                name = InputDevice.read("String").toString();
                if(name.equals(""))
                {
                    OutputDevice.printMessage("***Invalid Name***");
                }
                else break;
            }

            character = new Character(name, 500, 1, startingWeapon);
        }
    }

    private void initializationProcess(String[] args)
    {
        //Default Items
        Weapon fists = new Fists();
        Weapon startingWeapon = new Club();

        OutputDevice.printMessage("@@@@CHARACTER_CREATION@@@@");
        createCharacter(args, startingWeapon);

        inventory = new Inventory(character);
        exploration = new Explore(character, inventory);

        inventory.addItemToInventory(fists);
        inventory.addItemToInventory(startingWeapon);

        OutputDevice.printMessage("GAME STARTED");
    }

    public void createCharacterFromGUI(String[] args) {
        //Default Items
        Weapon fists = new Fists();
        Weapon startingWeapon = new Club();

        createCharacter(args, startingWeapon);

        inventory = new Inventory(character);
        exploration = new Explore(character, inventory);

        inventory.addItemToInventory(fists);
        inventory.addItemToInventory(startingWeapon);
    }











    //RUN GAME
    public void run(String[] args) {
        initializationProcess(args);

        while(true) // While game is running
        {
            String action = UIState.chooseYourActionMainMenu(); // Determines GameState

            if(action.equals("Open Your Inventory"))
            {
                //Open Your Inventory Implementation
                inventory.run();
            }
            else if(action.equals("Explore"))
            {
                exploration.run(character);
            }
            else if(action.equals("Show Player Stats"))
            {
                //Show Player Stats Implementation
                character.displayStats();
            }
            else if(action.equals("Retreive Saved Inventory"))
            {
                RetreiveSavedInventory.run(inventory);
            }
            else if(action.equals("Save Inventory"))
            {
                SaveInventory.run(inventory);
            }
            else if(action.equals("Exit"))
            {
                System.exit(0);
            }
            else if(action.equals("Invalid Input"))
            {
                OutputDevice.printMessage("Invalid Input. Try Again");
            }
        }
    }
}
