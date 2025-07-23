package com.example.PIIIProjectGUI.testing;

import com.example.PIIIProjectGUI.utilities.*;
import com.example.PIIIProjectGUI.inputoutput.CSVOutputDevice;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
        public static void main(String[] args) {
            // Example list of pairs to write to CSV
            List<Pair<Integer, String>> list = new ArrayList<>();

            // Write the list to a CSV file
            CSVOutputDevice.writeTwoColumnCSV("src/dataFiles.gameStateFiles/savedInventoryWeapons.csv", list);
    }
}
