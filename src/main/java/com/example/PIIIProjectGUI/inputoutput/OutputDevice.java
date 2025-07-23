package com.example.PIIIProjectGUI.inputoutput;

public class OutputDevice {

    // Private constructor to prevent instantiation
    private OutputDevice() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Static method to print a simple message
    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printMessageWithoutNewLine(String message) {
        System.out.print(message);
    }

    public static void printObject(Object object) {
        System.out.println(object);
    }
}

