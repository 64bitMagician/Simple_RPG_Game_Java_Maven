package com.example.PIIIProjectGUI.database;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.Classes.gameState.Inventory;
import com.example.PIIIProjectGUI.implementation.Classes.items.potions.abstractClasses.Potion;
import com.example.PIIIProjectGUI.implementation.Classes.items.weapons.abstractClasses.Weapon;
import com.example.PIIIProjectGUI.implementation.interfaces.Item;
import com.example.PIIIProjectGUI.utilities.ClassInstantiationReference;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InventoryDAO {

    public static void saveInventory(int characterID)
    {
        // First Clear all items from the Inventory Table, corresponding to this character
        clearInventory(characterID);
        
        // And now add all items from current local inventory to Inventory Table, for the corresponding character

        // Get inventory instance
        Inventory inventory = GameState.inventory;

        // Get list of items List<ItemAndCountPair>
        List<ItemAndCountPair> itemsList = inventory.items;

        // Add to the table each item from the inventory
        for (ItemAndCountPair item : itemsList)
        {
            // Separate the item instance from the quantity of that item
            Item itemInstance = item.key;
            int quantity = item.value;

            // Get Item Name
            String itemName = itemInstance.getItemName();

            // Get Item Type
            String itemType = null;
            if (itemInstance instanceof  Weapon)
            {
                itemType = "Weapon";
            }
            else if (itemInstance instanceof Potion)
            {
                itemType = "Potion";
            }

            // Save item to inventory table
            addItem(characterID, itemName, itemType, quantity);
        }
    }

    public static void clearInventory(int characterID) {
        String sql = "DELETE FROM inventory WHERE character_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, characterID);

            int rowsDeleted = stmt.executeUpdate();
            System.out.println("Cleared " + rowsDeleted + " items from inventory for character_id " + characterID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addItem(int characterID, String itemName, String itemType, int quantity) {
        String sql = "INSERT INTO inventory (character_id, item_name, item_type, quantity) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, characterID);
            stmt.setString(2, itemName);
            stmt.setString(3, itemType);
            stmt.setInt(4, quantity);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void loadInventory(int characterID) {
        String sql = "SELECT item_name, item_type, quantity FROM inventory WHERE character_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, characterID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("item_name");
                String itemType = rs.getString("item_type");
                int quantity = rs.getInt("quantity");

                // Instantiate Item
                for (int i = 0; i < quantity; i++) {
                    Item itemInstance = null;

                    if (itemType.equals("Weapon"))
                    {
                        itemInstance = ClassInstantiationReference.createWeaponInstance(itemName);
                    }
                    else if (itemType.equals("Potion"))
                    {
                        itemInstance = ClassInstantiationReference.createPotionInstance(itemName);
                    }

                    GameState.inventory.inventoryController.addItemToInventory(itemInstance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

