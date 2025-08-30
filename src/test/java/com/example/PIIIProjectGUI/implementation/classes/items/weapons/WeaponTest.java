package com.example.PIIIProjectGUI.implementation.classes.items.weapons;

import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Weapon class
public class WeaponTest {

    // Simple concrete subclass for testing
    static class TestWeapon extends Weapon {
        public TestWeapon(String name, float weight, int quality, float damage, WeaponRarityColors color) {
            this.weaponName = name;
            this.weight = weight;
            this.weaponItemQuality = quality;
            this.weaponDamage = damage;
            this.color = color;
        }

        @Override
        public String getItemDescription() {
            return "Test weapon: " + weaponName;
        }
    }

    // --- Constructor Tests ---

    @Test
    public void testWeaponConstructorSetsValues() {
        Weapon w = new TestWeapon("Sword", 5.0f, 3, 25.0f, Weapon.WeaponRarityColors.RED);

        assertEquals("Sword", w.getItemName());
        assertEquals(5.0f, w.getItemWeight());
        assertEquals(3, w.getWeaponItemQuality());
        assertEquals(25.0f, w.weaponDamage);
        assertEquals("RED", w.getItemRarityColor());
    }

    // --- Functionality Tests ---

    @Test
    public void testCompareToByQuality() {
        Weapon w1 = new TestWeapon("Sword", 5.0f, 3, 25.0f, Weapon.WeaponRarityColors.RED);
        Weapon w2 = new TestWeapon("Axe", 5.0f, 4, 25.0f, Weapon.WeaponRarityColors.ORANGE);

        assertTrue(w2.compareTo(w1) > 0); // higher quality should be "greater"
    }

    @Test
    public void testCompareToByDamage() {
        Weapon w1 = new TestWeapon("Sword", 5.0f, 2, 20.0f, Weapon.WeaponRarityColors.BLUE);
        Weapon w2 = new TestWeapon("Axe", 5.0f, 2, 30.0f, Weapon.WeaponRarityColors.BLUE);

        assertTrue(w2.compareTo(w1) > 0); // equal quality, higher damage wins
    }

    @Test
    public void testCompareToByWeight() {
        Weapon w1 = new TestWeapon("Sword", 4.0f, 2, 20.0f, Weapon.WeaponRarityColors.BLUE);
        Weapon w2 = new TestWeapon("Axe", 6.0f, 2, 20.0f, Weapon.WeaponRarityColors.BLUE);

        assertTrue(w2.compareTo(w1) > 0); // equal quality & damage, heavier weight wins
    }

    @Test
    public void testGetItemRarityColor() {
        Weapon w = new TestWeapon("Bow", 3.0f, 1, 15.0f, Weapon.WeaponRarityColors.GREEN);
        assertEquals("GREEN", w.getItemRarityColor());
    }

    @Test
    public void testGetItemInformationIncludesFields() {
        Weapon w = new TestWeapon("Dagger", 2.0f, 4, 12.0f, Weapon.WeaponRarityColors.ORANGE);
        String info = w.getItemInformation();

        assertTrue(info.contains("Dagger"));
        assertTrue(info.contains("IQ: 4"));
        assertTrue(info.contains("D: 12.0"));
        assertTrue(info.contains("W: 2.0"));
        assertTrue(info.contains("Test weapon: Dagger"));
    }
}
