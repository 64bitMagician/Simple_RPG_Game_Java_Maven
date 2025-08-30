package com.example.PIIIProjectGUI.implementation.classes.items.potions;

import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Potion class
public class PotionTest {

    // Simple concrete subclass for testing
    static class TestPotion extends Potion {
        private final float healAmount;

        public TestPotion(String name, int quality, float weight, Potion.PotionRarityColors color, float healAmount) {
            this.potionName = name;
            this.potionItemQuality = quality;
            this.weight = weight;
            this.color = color;
            this.healAmount = healAmount;
        }

        @Override
        public void runEffectsOfPotion(Character character) {
            character.increaseHealth(healAmount);
        }

        @Override
        public String getItemDescription() {
            return "Test potion: heals " + healAmount + " HP.";
        }
    }

    // Dummy Weapon class to satisfy Character constructor
    static class TestWeapon extends Weapon {
        public TestWeapon(String name, float damage) {
            this.weaponName = name;
            this.weaponDamage = damage;
        }

        @Override
        public String getItemDescription() {
            return "Test weapon: " + weaponName;
        }
    }

    // --- Constructor Tests ---
    @Test
    public void testPotionConstructorSetsValues() {
        Potion p = new TestPotion("Healing Potion", 1, 1.0f, Potion.PotionRarityColors.GREEN, 10);

        assertEquals("Healing Potion", p.getItemName());
        assertEquals(1, p.getPotionItemQualityQuality());
        assertEquals(1.0f, p.getItemWeight());
        assertEquals("GREEN", p.getItemRarityColor());
    }

    // --- Functionality Tests ---

    @Test
    public void testCompareToByQuality() {
        Potion p1 = new TestPotion("Potion A", 2, 1.0f, Potion.PotionRarityColors.BLUE, 25);
        Potion p2 = new TestPotion("Potion B", 1, 1.0f, Potion.PotionRarityColors.GREEN, 10);

        assertTrue(p2.compareTo(p1) > 0); // higher quality wins
    }

    @Test
    public void testCompareToByWeight() {
        Potion p1 = new TestPotion("Potion A", 1, 1.0f, Potion.PotionRarityColors.GREEN, 10);
        Potion p2 = new TestPotion("Potion B", 1, 2.0f, Potion.PotionRarityColors.BLUE, 10);

        assertTrue(p2.compareTo(p1) > 0); // same quality, heavier weight wins
    }

    @Test
    public void testGetItemRarityColor() {
        Potion p = new TestPotion("Potion C", 3, 0.5f, Potion.PotionRarityColors.RED, 50);
        assertEquals("RED", p.getItemRarityColor());
    }

    @Test
    public void testGetItemInformationIncludesFields() {
        Potion p = new TestPotion("Potion D", 4, 0.7f, Potion.PotionRarityColors.ORANGE, 100.0f);
        String info = p.getItemInformation();

        assertTrue(info.contains("Potion D"));
        assertTrue(info.contains("IQ: 4"));
        assertTrue(info.contains("W: 0.7"));
        assertTrue(info.contains("Test potion: heals 100.0 HP."));
    }

    @Test
    public void testRunEffectsOfPotionHealsCharacter() {
        Character c = new Character("Hero", 50, 100, 1.0f, new TestWeapon("Sword", 10));
        Potion healPotion = new TestPotion("Healing Potion", 1, 1.0f, Potion.PotionRarityColors.GREEN, 10);

        healPotion.runEffectsOfPotion(c);

        assertEquals(60, c.health); // 50 + 10
    }
}

