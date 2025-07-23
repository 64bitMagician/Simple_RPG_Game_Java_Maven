package com.example.PIIIProjectGUI.inputoutput;

import com.example.PIIIProjectGUI.utilities.Pair;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class CSVOutputDevice {

    // Method to write a list of Pair<Integer, String> to a CSV file, preserving the first row (header)
    public static void writeTwoColumnCSV(String relativePath, List<Pair<Integer, String>> pairs) {
        // Convert the relative path to an absolute path
        Path filePath = Paths.get(relativePath).toAbsolutePath();
        System.out.println("Attempting to write file at: " + filePath); // Debugging log

        try {
            // Read the current contents of the file (if exists) and preserve the header
            List<String> lines = Files.readAllLines(filePath);
            String header = lines.get(0); // Assuming the first line is the header

            // Open the file in write mode (this will overwrite the file)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                // Write the header back to the file
                writer.write(header);
                writer.newLine(); // Move to the next line

                // Iterate over the list of pairs and write each one to the CSV
                for (Pair<Integer, String> pair : pairs) {
                    // Format the line with the pair's first (integer) and second (string) values
                    writer.write(pair.getFirst() + "," + pair.getSecond());
                    writer.newLine(); // Move to the next line after each pair
                }

            } catch (IOException e) {
                System.err.println("Error writing to CSV file: " + e.getMessage());
            }

        } catch (IOException e) {
            // Handle errors in reading the file
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    public static void eraseFile(String relativePath) {
        // Convert the relative path to an absolute path
        Path filePath = Paths.get(relativePath).toAbsolutePath();
        System.out.println("Attempting to erase contents of file at: " + filePath); // Debugging log

        try {
            // Read the current contents of the file (if exists) to get the header
            List<String> lines = Files.readAllLines(filePath);
            if (lines.isEmpty()) {
                System.out.println("The file is empty. Nothing to erase.");
                return; // No content to erase
            }

            String header = lines.get(0); // Assuming the first line is the header

            // Overwrite the file, keeping only the header
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write(header); // Write the header
                writer.newLine();     // Add a new line after the header
            }

            System.out.println("File contents erased, but the header is preserved.");

        } catch (IOException e) {
            // Handle errors in reading or writing the file
            System.err.println("Error processing the CSV file: " + e.getMessage());
        }
    }
}
