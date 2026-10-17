package com.example.PIIIProjectGUI.exceptions;

public class CannotSaveEmptyInventoryException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotSaveEmptyInventoryException(String message) {
        super(message);
    }
}
