package com.example.PIIIProjectGUI.inputoutput;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputDevice {
    // Static Scanner shared by all methods in the class
    private static final Scanner scanner = new Scanner(System.in);

    // Private constructor to prevent instantiation
    private InputDevice() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Static method to read input based on the specified type
    public static Object read(String inputType) {
        try {
            switch (inputType.toLowerCase()) {
                case "string":
                    return scanner.nextLine();  // Reads a string
                case "int":
                    return scanner.nextInt();  // Reads an integer
                case "double":
                    return scanner.nextDouble();  // Reads a double
                case "boolean":
                    return scanner.nextBoolean();  // Reads a boolean
                case "float":
                    return scanner.nextFloat();  // Reads a float
                case "long":
                    return scanner.nextLong();  // Reads a long
                default:
                    System.out.println("Unsupported input type.");
                    return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input type! Please enter a valid " + inputType + ".");
            scanner.nextLine();  // Clear the invalid input from the scanner
            return null;
        }
    }

    // Static method to close the scanner when done
    public static void close() {
        scanner.close();
    }

    public static void consumeRemainingInput(){
        scanner.nextLine();
    }
}