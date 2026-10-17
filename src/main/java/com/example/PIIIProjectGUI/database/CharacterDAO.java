package com.example.PIIIProjectGUI.database;

import java.sql.*;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.classes.gameState.Explore;
import com.example.PIIIProjectGUI.implementation.classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.classes.player.Character;
import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;


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

    public static boolean deleteCharacter(int accountId) {
        OutputDevice.printMessage("Deleting Character Record...");

        String sql = "DELETE FROM characters WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Character deleted for account_id " + accountId);
                return true;
            } else {
                System.out.println("No character found for account_id " + accountId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static int insertNewCharacterToTable(int accountID) {
        Character character = GameState.character;
        Explore explore = GameState.explore;

        String sql = """
        INSERT INTO characters
        (account_id, name, health, total_health, alive, equipped_weapon, damage_multiplier, total_damage, battle_counter, stage_counter)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, accountID);
            stmt.setString(2, character.name);
            stmt.setFloat(3, character.health);
            stmt.setFloat(4, character.totalHealth);
            stmt.setBoolean(5, character.alive);
            stmt.setString(6, character.equippedWeapon != null ? character.equippedWeapon.getItemName() : null);
            stmt.setFloat(7, character.damageMultiplier);
            stmt.setFloat(8, character.totalDamage);
            stmt.setInt(9, explore.battleCounter);
            stmt.setInt(10, explore.runStageCounter);

            stmt.executeUpdate();

            // Get the auto-generated character_id
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int newCharacterId = keys.getInt(1);
                    System.out.println("Inserted character with ID: " + newCharacterId);
                    return newCharacterId;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void updateCharacterWithinTable(int accountID) {
        Character character = GameState.character;
        Explore explore = GameState.explore;

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
            stmt.setFloat(2, character.health);
            stmt.setFloat(3, character.totalHealth);
            stmt.setBoolean(4, character.alive);
            stmt.setString(5, character.equippedWeapon != null ? character.equippedWeapon.getItemName() : null);
            stmt.setFloat(6, character.damageMultiplier);
            stmt.setFloat(7, character.totalDamage);
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


    public static void loadSavedData(int accountId) {
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

                // Make New Singleton Character Instance
                GameState.character = new Character(
                        name,
                        health,
                        totalHealth,
                        damageMultiplier,
                        null
                );

                GameState.character.characterID = characterID;
                GameState.character.alive = alive;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // if it fails it returns -1
    public static int getBattleCounterProgress(int accountId)
    {
        String sql = "SELECT * FROM characters WHERE account_id = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) // if we one the first row of the result (which is one single row anyway)
            {
                return rs.getInt("battle_counter");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // if it fails it returns -1
    public static int getRunStageCounterProgress(int accountID)
    {
        String sql = "SELECT * FROM characters WHERE account_id = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) // if we one the first row of the result (which is one single row anyway)
            {
                return rs.getInt("stage_counter");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private static String returnEquippedWeaponName(int accountId)
    {
        String sql = "SELECT * FROM characters WHERE account_id = ? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) // if we one the first row of the result (which is one single row anyway)
            {
                return rs.getString("equipped_weapon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void equipThePreviouslyEquippedWeapon(int accountID)
    {
        OutputDevice.printMessage("Equiping previously equipped weapon...");

        // Equip the weapon previously equipped
        // first find an instance of the eqiupped weapon within the inventory ...

        String equippedWeaponName = returnEquippedWeaponName(accountID);

        OutputDevice.printMessage("Name of previously equipped weapon is " + equippedWeaponName);

        for (ItemAndCountPair itemAndCountPair : GameState.inventory.items)
        {
            if (equippedWeaponName.equals(itemAndCountPair.key.getItemName())) // if name of equiped weapon in table matches a name of weapon in inventory
            {
                OutputDevice.printMessage("Found the " + equippedWeaponName + " within the inventory...");
                GameState.character.equipWeapon((Weapon) itemAndCountPair.key);
                return;
            }
        }

        OutputDevice.printMessage("Could not find any previously equiped " + equippedWeaponName + " within the local inventory...");
        OutputDevice.printMessage("Equipping default Fists...");

        // In case it cannot find an instance of the equipped_weapon in table, it will return the Fists weapon
        for (ItemAndCountPair itemAndCountPair : GameState.inventory.items)
        {
            if (itemAndCountPair.key.getItemName().equals("Fists")) // if name of equiped weapon in table matches a name of weapon in inventory
            {
                OutputDevice.printMessage("Found fists within local inventory...");
                GameState.character.equipWeapon((Weapon) itemAndCountPair.key);
                return;
            }
        }

        OutputDevice.printMessage("Could not find default fists to equip...");
        OutputDevice.printMessage("Null is equipped...");
    }

    public static int getNextCharacterIDFromDB() {
        String sql = """
        SELECT AUTO_INCREMENT AS next_id
        FROM information_schema.TABLES
        WHERE TABLE_SCHEMA = 'game_db'
          AND TABLE_NAME = 'characters'
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to schema: " + conn.getCatalog());

            if (rs.next()) {
                return rs.getInt("next_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int debugCharacterTableAutoIncrement() {
        int autoInc=-1;

        String sql = """
        SELECT AUTO_INCREMENT, TABLE_SCHEMA, TABLE_NAME
        FROM information_schema.TABLES
        WHERE TABLE_SCHEMA = 'game_db'
          AND TABLE_NAME = 'characters'
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Connected to schema (conn.getCatalog()): " + conn.getCatalog());


            if (rs.next()) {
                autoInc = rs.getInt("AUTO_INCREMENT");
                String schema = rs.getString("TABLE_SCHEMA");
                String table = rs.getString("TABLE_NAME");

                System.out.println("AUTO_INCREMENT from information_schema: " + autoInc);
                System.out.println("Schema: " + schema + " | Table: " + table);
            }

            // Also dump the DDL of the characters table to see current AUTO_INCREMENT
            try (Statement st = conn.createStatement();
                 ResultSet ddl = st.executeQuery("SHOW CREATE TABLE characters")) {
                if (ddl.next()) {
                    System.out.println("DDL: " + ddl.getString(2));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoInc;
    }


}
