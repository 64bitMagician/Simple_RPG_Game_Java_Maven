package com.example.PIIIProjectGUI.implementation.classes.player;

import com.example.PIIIProjectGUI.implementation.classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Character class
public class CharacterTest {

    // Dummy Weapon subclass for testing
    class TestWeapon extends Weapon {
        public TestWeapon(String name, float damage) {
            this.weaponName = name;
            this.weaponDamage = damage;
            this.weaponItemQuality = 1;
            this.weight = 5;
            this.color = WeaponRarityColors.GREEN;
        }

        @Override
        public String getItemDescription() {
            return "Test weapon: " + weaponName + " with damage " + weaponDamage;
        }
    }


    // Dummy Potion subclass for testing
    class TestPotion extends Potion {
        public TestPotion(){
            this.potionName = "TestPotion";
            this.potionItemQuality = 1;
            this.weight = 2;
            this.color = PotionRarityColors.GREEN;
        }

        @Override
        public void runEffectsOfPotion(Character character) {
            character.increaseHealth(10); // Example effect for testing
        }

        @Override
        public String getItemDescription() {
            return "Test potion that restores 10 health.";
        }
    }


    // --- Constructor Tests ---
    @Test
    public void testMainConstructorSetsValues() {
        Weapon sword = new TestWeapon("Sword", 20);
        Character c = new Character("Hero", 100, 150, 2.0f, sword);

        assertEquals("Hero", c.name);
        assertEquals(100, c.health);
        assertEquals(150, c.totalHealth);
        assertTrue(c.alive);
        assertEquals(sword, c.equippedWeapon);
        assertEquals(40, c.totalDamage); // 20 * 2.0
    }

    @Test
    public void testCopyConstructorCopiesValues() {
        Weapon axe = new TestWeapon("Axe", 30);
        Character original = new Character("Warrior", 80, 120, 1.5f, axe);
        Character copy = new Character(original);

        assertEquals(original.name, copy.name);
        assertEquals(original.health, copy.health);
        assertEquals(original.totalHealth, copy.totalHealth);
        assertEquals(original.damageMultiplier, copy.damageMultiplier);
    }

    // --- Functionality Tests ---
    @Test
    public void testIncreaseHealthDoesNotExceedTotal() {
        Character c = new Character("Mage", 90, 100, 1.0f, null);
        c.increaseHealth(20);
        assertEquals(100, c.health); // should not exceed totalHealth
    }

    @Test
    public void testEquipWeaponUpdatesDamage() {
        Character c = new Character("Rogue", 100, 100, 1.5f, null);
        Weapon dagger = new TestWeapon("Dagger", 10);

        c.equipWeapon(dagger);
        assertEquals(dagger, c.equippedWeapon);
        assertEquals(15, c.totalDamage); // 10 * 1.5
    }

    @Test
    public void testReturnHealthString() {
        Character c = new Character("Priest", 50, 100, 1.0f, null);
        String healthString = c.returnHealth();
        assertEquals("Priest's Health: 50.0/100.0", healthString);
    }
}
