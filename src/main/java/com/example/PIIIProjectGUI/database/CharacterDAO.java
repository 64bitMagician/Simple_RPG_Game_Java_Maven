package com.example.PIIIProjectGUI.database;

import java.sql.*;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.Classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ClassInstantiationReference;


public class CharacterDAO {

    public static void saveCharacter(int accountID) {
        if (characterAssociatedToAccountID(accountID)==1) // If there is a character associated to this accountID, Update Character within table
        {
            updateCharacterWithinTable(accountID);
        }
        else if (characterAssociatedToAccountID(accountID)==0)// If there is no character associated to this accountID (no value(accountID) present in the table ), Insert New Character to table
        {
            insertNewCharacterToTable(accountID);
        }
        else
        {
            OutputDevice.printMessage("Something Wrong Happend within the characterAssociatedToAccountID() method that belongs to the CharacterDAO.java File.");
        }
    }

    public static void insertNewCharacterToTable(int accountID){
        Character character = GameState.character;
        Explore explore = GameState.exploration;

        String sql = """
                INSERT INTO characters 
                (account_id, name, health, total_health, alive, equipped_weapon, damage_multiplier, total_damage, battle_counter, stage_counter)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountID);
            stmt.setString(2, character.name);
            stmt.setFloat(3, (float) character.health);
            stmt.setFloat(4, (float) character.totalHealth);
            stmt.setBoolean(5, character.alive);
            stmt.setString(6, character.equippedWeapon != null ? character.equippedWeapon.getItemName() : null);
            stmt.setFloat(7, (float) character.damageMultiplier);
            stmt.setFloat(8, (float) character.totalDamage);
            stmt.setInt(9, explore.battleCounter); // battle_counter
            stmt.setInt(10, explore.runStageCounter); // stage_counter

            stmt.executeUpdate();

            System.out.println("Character saved to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCharacterWithinTable(int accountID) {
        Character character = GameState.character;
        Explore explore = GameState.exploration;

        String sql = """
            UPDATE characters
            SET name = ?,
                health = ?,
                total_health = ?,
                alive = ?,
                equipped_weapon = ?,
                damage_multiplier = ?,
                total_damage = ?,
                battle_counter = ?,
                stage_counter = ?
            WHERE account_id = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, character.name);
            stmt.setFloat(2, (float) character.health);
            stmt.setFloat(3, (float) character.totalHealth);
            stmt.setBoolean(4, character.alive);
            stmt.setString(5, character.equippedWeapon != null ? character.equippedWeapon.getItemName() : null);
            stmt.setFloat(6, (float) character.damageMultiplier);
            stmt.setFloat(7, (float) character.totalDamage);
            stmt.setInt(8, explore.battleCounter);
            stmt.setInt(9, explore.runStageCounter);
            stmt.setInt(10, accountID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Character updated in database.");
            } else {
                System.out.println("No character found to update for account_id " + accountID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method return 1 if true, 0 if false, and -1 if something wrong happened (error)
    private static int characterAssociatedToAccountID(int accountID){
        String sql = "SELECT 1 FROM characters WHERE account_id = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) // if there is a character associated to the accountID (there is a record with the unique accountID within the table)
            {
                return 1;
            }
            else // if there is no character associated to the accountID (there is no record with the unique accountID within the table)
            {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static void loadCharacter(int accountId) {
        Explore explore = GameState.exploration;
        Weapon equippedWeapon;

        String sql = "SELECT * FROM characters WHERE account_id = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) // if we one the first row of the result (which is one single row anyway)
            {
                // Get the the character data from the DB Record
                int characterID = rs.getInt("character_id");
                String name = rs.getString("name");
                float health = rs.getFloat("health");
                float totalHealth = rs.getFloat("total_health");
                float damageMultiplier = rs.getFloat("damage_multiplier");
                boolean alive = rs.getBoolean("alive");

                // Get Character's Equipped Weapon Instance from the String field of the DB
                equippedWeapon = ClassInstantiationReference.createWeaponInstance(rs.getString("equipped_weapon"));

                // Make New Singleton Character Instance
                GameState.character = new Character(
                        characterID,
                        name,
                        health,
                        totalHealth,
                        damageMultiplier,
                        equippedWeapon
                );

                // create new Inventory Instance Singleton and add saved inventory to Character's Inventory Instance
                GameState.inventory = new Inventory(GameState.character);
                InventoryDAO.loadInventory(rs.getInt("character_id"));

                // add Progress Data to newly instantiated Explore Singleton

                GameState.exploration = new Explore(GameState.character, GameState.inventory.inventoryController);

                int battleCounter = rs.getInt("battle_counter");
                int runStageCounter = rs.getInt("stage_counter");

                GameState.exploration.battleCounter = battleCounter;
                GameState.exploration.runStageCounter = runStageCounter;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This method will return -1 if unsuccessful
    public static int getNextCharacterID()
    {
        // If there are records in the table, search for and return the next Character ID
        // If there are no records, this method shall always return 1.

        String sql = "SELECT * FROM characters";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.last()) // If we moved to the last record row
                return rs.getInt("character_id") + 1; // return the incrementation of the last character_id in the table.
            else // If there are no records in the table
                return 1; // The next Character ID will be the First Iteration, so we return 1.
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

}
