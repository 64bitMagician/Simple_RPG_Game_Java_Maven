package com.example.PIIIProjectGUI.exceptions;

public class CannotSaveEmptyInventoryException extends RuntimeException {
    public CannotSaveEmptyInventoryException(String message) {
        super(message);
    }
}
