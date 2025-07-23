package com.example.PIIIProjectGUI.utilities;

public class Pair<T, U> {
    private T first;  // This will store the first value (e.g., Integer)
    private U second; // This will store the second value (e.g., String)

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    // Getters and setters
    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
