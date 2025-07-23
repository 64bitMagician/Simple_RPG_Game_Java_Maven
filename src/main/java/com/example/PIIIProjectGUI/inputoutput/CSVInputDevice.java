package com.example.PIIIProjectGUI.inputoutput;

import com.example.PIIIProjectGUI.utilities.Pair;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CSVInputDevice {

    // Method to read from a CSV file and return a list of Pair<Integer, String>
    public static List<Pair<Integer, String>> readIntegerStringCSV(String relativePath) {
        List<Pair<Integer, String>> pairs = new ArrayList<>();

        try {
            // Convert the relative path to an absolute path
            Path filePath = Paths.get(relativePath).toAbsolutePath();
            System.out.println("Attempting to read file at: " + filePath); // Debugging log

            // Use the absolute path to read the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String line;

                // Skip the first line (header)
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header row
                    }

                    // Split the line by commas (assuming a CSV format)
                    String[] columns = line.split(",");

                    // Check if the line has at least two columns
                    if (columns.length >= 2) {
                        try {
                            // Parse the first column as an integer and the second as a string
                            Integer first = Integer.parseInt(columns[0].trim());  // First column as Integer
                            String second = columns[1].trim();  // Second column as String

                            // Add the parsed values as a Pair to the list
                            pairs.add(new Pair<>(first, second));
                        } catch (NumberFormatException e) {
                            // Handle the case where the first column is not an integer
                            System.err.println("Error parsing integer in line: " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Handle errors reading the file
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        // Return the list of pairs
        return pairs;
    }



    // Method to read from a CSV file and return a list of Pair<String, Integer>
    public static List<Pair<String, Integer>> readStringIntegerCSV(String relativePath) {
        List<Pair<String, Integer>> pairs = new ArrayList<>();

        try {
            // Convert the relative path to an absolute path
            Path filePath = Paths.get(relativePath).toAbsolutePath();
            System.out.println("Attempting to read file at: " + filePath); // Debugging log

            // Use the absolute path to read the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String line;

                // Skip the first line (header)
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header row
                    }

                    // Split the line by commas (assuming a CSV format)
                    String[] columns = line.split(",");

                    // Check if the line has at least two columns
                    if (columns.length >= 2) {
                        try {
                            // Parse the first column as an String and the second as a Integer
                            String first = columns[0].trim();  // Second column as String
                            Integer second = Integer.parseInt(columns[1].trim());  // First column as Integer

                            // Add the parsed values as a Pair to the list
                            pairs.add(new Pair<>(first, second));
                        } catch (NumberFormatException e) {
                            // Handle the case where the first column is not an integer
                            System.err.println("Error parsing integer in line: " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Handle errors reading the file
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        // Return the list of pairs
        return pairs;
    }


    // Method to check if a CSV file is empty after the header row
    public static boolean isCSVEmptyAfterHeader(String relativePath) {
        try {
            // Convert the relative path to an absolute path
            Path filePath = Paths.get(relativePath).toAbsolutePath();
            System.out.println("Checking file at: " + filePath); // Debugging log

            // Use the absolute path to read the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String line;

                // Skip the first line (header)
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip the header row
                    }

                    // If we encounter any non-empty line after the header, the file is not empty
                    if (!line.trim().isEmpty()) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            // Handle errors reading the file
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        // If no non-empty rows were found, return true (file is empty after header)
        return true;
    }
}
