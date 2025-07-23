package com.example.PIIIProjectGUI.randomization;

import com.example.PIIIProjectGUI.utilities.IntegerRange;

import java.util.Random;

public class RandomIntegerByRange {

    //DATA
    private static Random random = new Random();

    //PUBLIC METHODS
    public static int generateRandomIntByRange(IntegerRange range){
        return random.nextInt(range.position2-range.position1) + range.position1;
    }


}
