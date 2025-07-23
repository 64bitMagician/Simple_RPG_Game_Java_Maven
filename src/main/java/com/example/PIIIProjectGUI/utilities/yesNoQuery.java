package com.example.PIIIProjectGUI.utilities;

import com.example.PIIIProjectGUI.inputoutput.InputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;

public class yesNoQuery {

    //       This query function returns true if the input is 'Y' or 'y', which implies 'yes', or returns false if input
    //  is 'N' or 'n', which implies 'no'. The argument represents the query before asking for "Y/N: " input.
    public static boolean query(String message){

        while(true)
        {
            OutputDevice.printMessage(message);
            OutputDevice.printMessageWithoutNewLine("Y/N: ");
            String inputMessage = InputDevice.read("String").toString();

            if(inputMessage.equals("Y")||inputMessage.equals("y"))
            {
                return true;
            }
            else if(inputMessage.equals("N")||inputMessage.equals("n"))
            {
                return false;
            }
            else
            {
                OutputDevice.printMessage("Invalid Input. Try again");
                InputDevice.consumeRemainingInput();
            }
        }

    }

    //      This method does exactly what the method above does, that's why it overrides it. But since it receives no argument,
    //  this method is used as a default query method. The query will always be the message: "Are you sure you want to do that?\nY/N:".
    public static boolean query(){

        String message = "Are you sure you want to do that?\nY/N:";

        while(true)
        {
            OutputDevice.printMessageWithoutNewLine(message);
            String inputMessage = InputDevice.read("String").toString();

            if(inputMessage.equals("Y")||inputMessage.equals("y"))
            {
                return true;
            }
            else if(inputMessage.equals("N")||inputMessage.equals("n"))
            {
                return false;
            }
            else
            {
                OutputDevice.printMessage("Invalid Input. Try again");
                InputDevice.consumeRemainingInput();
            }
        }

    }


}
