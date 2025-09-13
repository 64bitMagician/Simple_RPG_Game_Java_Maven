package com.example.PIIIProjectGUI.utilities;

import java.util.ArrayList;
import java.util.List;

public class StringUtilities {

    //METHOD FOR RETURNING THE STRING ARRAY REPRESENTATION OF A STRING IN A BOOK LIKE MANNER
    public static String[] convertOneStringSentencesIntoStringArrays(String input) {
        int maxLength = 100;  // Maximum characters per line
        List<String> lines = new ArrayList<>();  // To store each "line" as a separate string
        StringBuilder currentLine = new StringBuilder();  // Holds the current line being built

        String[] words = input.split(" ");  // Split input into words by spaces

        for (String word : words) {
            // Check if the current line plus the next word exceeds the max length
            if (currentLine.length() + word.length() > maxLength) {

                // If the word itself exceeds the max length, split by hyphens
                if (word.contains("-")) {
                    String[] syllables = word.split("-");
                    for (String syllable : syllables) {
                        if (currentLine.length() + syllable.length() + 1 > maxLength) {
                            // If adding the syllable would exceed max, start a new line
                            lines.add(currentLine.toString().trim());
                            currentLine = new StringBuilder();
                        }
                        currentLine.append(syllable).append("-"); // Add syllable with hyphen
                    }
                    // Remove the trailing hyphen for the last syllable and continue
                    currentLine.deleteCharAt(currentLine.length() - 1);
                } else {
                    // Add the current line to lines and reset for the next line
                    lines.add(currentLine.toString().trim());
                    currentLine = new StringBuilder();
                }
            }

            // Add the word to the current line if it fits
            currentLine.append(word).append(" ");
        }

        // Add any remaining content in currentLine to lines
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        // Convert the list of lines to an array and return it
        return lines.toArray(new String[0]);
    }

    /**
     * Returns the name of the FXML file without the ".fxml" extension.
     * If the string does not end with ".fxml", returns the original string.
     *
     * @param fileName The input string
     * @return The string without ".fxml" if it ends with it, else the original string
     */
    public static String stripFXMLExtension(String fileName) {
        if (fileName != null && fileName.endsWith(".fxml")) {
            return fileName.substring(0, fileName.length() - 5); // remove ".fxml"
        }
        return fileName;
    }
}
