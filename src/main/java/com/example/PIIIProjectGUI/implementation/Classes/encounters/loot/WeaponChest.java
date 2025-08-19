package com.example.PIIIProjectGUI.implementation.Classes.encounters.loot;

import com.example.PIIIProjectGUI.controllers.InventoryController;
import com.example.PIIIProjectGUI.implementation.Classes.encounters.loot.abstractClasses.LootableArea;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.inputoutput.CSVInputDevice;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ClassInstantiationReference;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;
import com.example.PIIIProjectGUI.utilities.Pair;
import com.example.PIIIProjectGUI.utilities.Randomness;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WeaponChest extends LootableArea {
    // Temporary Data
    List<ItemAndCountPair> temporaryWeaponList = new ArrayList<ItemAndCountPair>();

    public WeaponChest(InventoryController inventoryReference) {
        initializeChestWithRandomWeapon();
        this.loot.addAll(temporaryWeaponList);
        this.inventory = inventoryReference;
    }

    private void initializeChestWithRandomWeapon(){
        // Determine Item Quality
        int itemQuality = Randomness.tenTwentyThirtyFourty();
        List<Pair<String,Integer>> weaponsList = CSVInputDevice.readStringIntegerCSV("src/main/java/com/example/PIIIProjectGUI/dataFiles/allWeaponItems.csv");
        Stream<Pair<String,Integer>> weaponsListSteam = weaponsList.stream();
        List<Pair<String,Integer>> specificItemQualityWeaponList = new ArrayList<>();
        weaponsListSteam.filter(item -> item.getSecond() == itemQuality).forEach(item -> specificItemQualityWeaponList.add(item));
        Pair<String,Integer> randomWeapon = Randomness.getRandomElementFromList(specificItemQualityWeaponList);
        String weaponClassName = randomWeapon.getFirst();
        Weapon randomWeaponInstance = ClassInstantiationReference.createWeaponInstance(weaponClassName);
        ItemAndCountPair itemAndCountPairWeapon = new ItemAndCountPair(randomWeaponInstance,1);
        temporaryWeaponList.add(itemAndCountPairWeapon);
    }

    @Override
    public void initializingMessage() {
        OutputDevice.printMessage("                              !=YOU=FOUND=A=CHEST=!");
    }
}
