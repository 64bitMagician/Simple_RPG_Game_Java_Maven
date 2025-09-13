package com.example.PIIIProjectGUI.implementation.classes.database;

public class Account {
    private int id;
    private String email;
    private String role;

    public Account(int id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}