package com.example.PIIIProjectGUI.exceptions;

public class NoSavedInventoryStateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoSavedInventoryStateException(String message) {
        super(message);
    }
}
