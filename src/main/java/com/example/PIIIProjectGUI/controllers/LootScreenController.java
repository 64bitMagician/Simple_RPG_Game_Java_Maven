package com.example.PIIIProjectGUI.controllers;

import com.example.PIIIProjectGUI.GameState;
import com.example.PIIIProjectGUI.implementation.classes.encounters.loot.abstractClasses.LootableArea;
import com.example.PIIIProjectGUI.implementation.interfaces.Item;
import com.example.PIIIProjectGUI.utilities.ItemAndCountPair;
import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.List;

public class LootScreenController {

    @FXML private ImageView chestImage;
    @FXML private Button openButton;
    @FXML private VBox itemContainer;

    private boolean opened = false;
    private static LootableArea lootableArea;

    private static Stage lootStage;

    public static void setLootableArea(LootableArea givenLootableArea) {
        lootableArea = givenLootableArea;
    }

    public static void setStage(Stage stage) {
        lootStage = stage;
    }

    @FXML
    private void initialize() {
        Image closedImage = new Image(getClass().getResourceAsStream("/images/Closed_Chest.png"));
        chestImage.setImage(closedImage);

        openButton.setOnAction(e -> {
            if (!opened) {
                openChest();
            }
        });
    }

    private void openChest() {
        // Change image to opened chest
        Image openedImage = new Image(getClass().getResourceAsStream("/images/Openned_Chest.png"));
        chestImage.setImage(openedImage);

        // Get loot and display items
        List<ItemAndCountPair> items = lootableArea.loot;
        for (ItemAndCountPair pair : items) {
            HBox itemBox = createLootedItemBox(pair);
            itemContainer.getChildren().add(itemBox);
        }

        opened = true;
        openButton.setDisable(true);
    }

    private HBox createLootedItemBox(ItemAndCountPair pair) {
        Item item = pair.key;
        int count = pair.value;

        // ░░ rarity square ░░
        Region rarity = new Region();
        rarity.setPrefSize(20, 20);
        rarity.setStyle("-fx-background-color:" + item.getItemRarityColor() + "; -fx-border-color:black;");

        // ░░ item name and count ░░
        Label name = new Label(item.getItemName() + " x" + count);
        name.setStyle("-fx-font-size:14; -fx-font-weight:bold;");

        // ░░ accept/reject icons ░░
        Label takeIcon = new Label("✔");
        Label rejectIcon = new Label("✖");

        takeIcon.setStyle("-fx-font-size:16;");
        rejectIcon.setStyle("-fx-font-size:16;");

        HBox iconBox = new HBox(10, takeIcon, rejectIcon);
        iconBox.setPadding(new Insets(0, 0, 0, 20));

        // Container
        HBox box = new HBox(10, rarity, name, iconBox);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-background-color:#f0f0f0; -fx-border-color:gray; -fx-border-radius:5;");

        // Click to take item
        takeIcon.setOnMouseClicked(e -> {
            InventoryController.addItemToInventory(pair);
            itemContainer.getChildren().remove(box);
            showResultPopup("You took a " + item.getItemName());
        });

        // Click to reject
        rejectIcon.setOnMouseClicked(e -> {
            itemContainer.getChildren().remove(box);
            showResultPopup("You refused to take the " + item.getItemName());
        });

        return box;
    }

    private void showResultPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Item Decision");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                //Increment stage progress.
                GameState.explore.runStageCounter++;
                GameState.saveGame();

                // Switch Back to Main Menu
                ViewNavigator.switchScene("main-menu.fxml");
            }
        });
    }

}
