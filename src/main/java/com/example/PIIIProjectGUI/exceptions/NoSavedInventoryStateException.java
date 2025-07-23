package com.example.PIIIProjectGUI.exceptions;

public class NoSavedInventoryStateException extends RuntimeException {
    public NoSavedInventoryStateException(String message) {
        super(message);
    }
}
