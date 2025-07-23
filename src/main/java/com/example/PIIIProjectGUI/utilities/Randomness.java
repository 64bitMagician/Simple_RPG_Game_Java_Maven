package com.example.PIIIProjectGUI.utilities;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomness {
    public static int tenTwentyThirtyFourty() {
        // This method will return the item quality
        Random rand = new Random();
        int chance = rand.nextInt(101); // 1 to 100

        if (chance <= 5) {
            return 4; // 5%
        } else if (chance <= 20) {
            return 3; // 15%
        } else if (chance <= 50) {
            return 2; // 30%
        } else {
            return 1; // 50%
        }
    }

    public static <T> T getRandomElementFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }
}
