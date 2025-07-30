package com.example.PIIIProjectGUI.app;

// I/O imports
import com.example.PIIIProjectGUI.GUI.GUIControllers.InventoryController;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game {
    //DATA
    Character character;
    Explore exploration;
    Inventory inventory;
    InventoryController inventoryController;




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
        exploration = new Explore(character, inventoryController);

        inventoryController.addItemToInventory(fists);
        inventoryController.addItemToInventory(startingWeapon);

        OutputDevice.printMessage("GAME STARTED");
    }

    public void createCharacterFromGUI(String[] args) {
        //Default Items
        Weapon fists = new Fists();
        Weapon startingWeapon = new Club();

        createCharacter(args, startingWeapon);

        inventory = new Inventory(character);
        exploration = new Explore(character, inventoryController);

        inventoryController.addItemToInventory(fists);
        inventoryController.addItemToInventory(startingWeapon);
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
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/inventory-view.fxml"));
                    Scene scene = new Scene(loader.load());
                    Stage stage = new Stage();  // Or reuse the current one if preferred
                    stage.setScene(scene);
                    stage.setTitle("Inventory");
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Optionally show an error dialog to the user
                    // showErrorDialog("Failed to load inventory screen.");
                }
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
                RetreiveSavedInventory.run(inventoryController);
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
