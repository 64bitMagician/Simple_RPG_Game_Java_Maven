package com.example.PIIIProjectGUI.implementation.Classes.encounters.loot;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.loot.abstractClasses.LootableArea;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.inputoutput.CSVInputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ClassInstantiationReference;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;
import com.example.PIIIProjectGUI.utilities.Pair;
import com.example.PIIIProjectGUI.utilities.Randomness;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;




public class PotionChest extends LootableArea {
    // Temporary Data
    List<ItemAndCountPair> temporaryPotionList = new ArrayList<ItemAndCountPair>();

    public PotionChest(InventoryController inventoryReference) {
        initializeChestWithRandomPotion();
        this.loot.addAll(temporaryPotionList);
        this.inventory = inventoryReference;
    }

    private void initializeChestWithRandomPotion(){
        // Determine Item Quality
        int itemQuality = Randomness.tenTwentyThirtyFourty();
        List<Pair<String,Integer>> potionsList = CSVInputDevice.readStringIntegerCSV("src/main/java/com/example/PIIIProjectGUI/dataFiles/allPotionItems.csv");
        Stream<Pair<String,Integer>> potionsListSteam = potionsList.stream();
        List<Pair<String,Integer>> specificItemQualityPotionList = new ArrayList<>();
        potionsListSteam.filter(item -> item.getSecond() == itemQuality).forEach(item -> specificItemQualityPotionList.add(item));
        Pair<String,Integer> randomPotion = Randomness.getRandomElementFromList(specificItemQualityPotionList);
        String potionClassName = randomPotion.getFirst();
        Potion randomPotionInstance = ClassInstantiationReference.createPotionInstance(potionClassName);
        // Introduce it within an ItemCountPair, in case for multiple instance loot drops
        ItemAndCountPair itemAndCountPairPotion = new ItemAndCountPair(randomPotionInstance,1);
        temporaryPotionList.add(itemAndCountPairPotion);
    }

    @Override
    public void initializingMessage() {
        OutputDevice.printMessage("                              !=YOU=FOUND=A=CHEST=!");
    }
}
