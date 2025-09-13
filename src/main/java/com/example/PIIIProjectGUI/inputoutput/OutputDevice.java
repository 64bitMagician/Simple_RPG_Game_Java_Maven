package com.example.PIIIProjectGUI.inputoutput;

public class OutputDevice {

    // ANSI escape codes for styling
    private static final String RESET = "\u001B[0m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String GREEN_BOLD = "\u001B[1;32m";
    private static final String BLUE_BOLD = "\u001B[1;34m";
    private static final String WHITE_BOLD = "\u001B[1;37m";

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

    public static void printError(String errorText) {
        String border = "==================== ERROR ====================";
        System.out.println(RED_BOLD + border + RESET);
        System.out.println(RED_BOLD + "❌ " + errorText + RESET);
        System.out.println(RED_BOLD + border + RESET);
    }

    public static void printSuccess(String successText) {
        String border = "=================== SUCCESS ===================";
        System.out.println(GREEN_BOLD + border + RESET);
        System.out.println(GREEN_BOLD + "✅ " + successText + RESET);
        System.out.println(GREEN_BOLD + border + RESET);
    }

    public static void printInfo(String infoText) {
        String border = "==================== INFO =====================";
        System.out.println(BLUE_BOLD + border + RESET);
        System.out.println(BLUE_BOLD + "ℹ️  " + infoText + RESET);
        System.out.println(BLUE_BOLD + border + RESET);
    }
}
