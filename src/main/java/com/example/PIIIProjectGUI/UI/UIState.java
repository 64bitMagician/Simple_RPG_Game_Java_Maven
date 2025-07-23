package com.example.PIIIProjectGUI.UI;

import com.example.PIIIProjectGUI.inputoutput.InputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class UIState {

    public static String chooseYourActionMainMenu() {
        OutputDevice.printMessage("Choose your action. Press:");
        OutputDevice.printMessage("1 To Open Your Inventory");
        OutputDevice.printMessage("2 To Explore");
        OutputDevice.printMessage("3 To Show Player Stats");
        OutputDevice.printMessage("4 To Retreive Saved Inventory");
        OutputDevice.printMessage("5 To Save Inventory");
        OutputDevice.printMessage("9 To Exit");

        OutputDevice.printMessageWithoutNewLine("Your Input: ");
        String input = InputDevice.read("String").toString();

        if(input.equals("1"))
        {
            return "Open Your Inventory";
        }
        else if(input.equals("2"))
        {
            return "Explore";
        }
        else if(input.equals("3"))
        {
            return "Show Player Stats";
        }
        else if(input.equals("4"))
        {
            return "Retreive Saved Inventory";
        }
        else if(input.equals("5"))
        {
            return "Save Inventory";
        }
        else if(input.equals("9"))
        {
            return "Exit";
        }

        return "Invalid Input";
    }



    public static String chooseYourActionPlayerBattle() {
        OutputDevice.printMessage("Choose your action. Press:");
        OutputDevice.printMessage("1 To Open Your Inventory");
        OutputDevice.printMessage("2 Atack!");
        OutputDevice.printMessage("9 To Exit");

        OutputDevice.printMessageWithoutNewLine("Your Input: ");
        String input = InputDevice.read("String").toString();

        if(input.equals("1"))
        {
            return "Open Your Inventory";
        }
        else if(input.equals("2"))
        {
            return "Atack";
        }
        else if(input.equals("9"))
        {
            return "Exit";
        }

        return "Invalid Input";
    }



}
