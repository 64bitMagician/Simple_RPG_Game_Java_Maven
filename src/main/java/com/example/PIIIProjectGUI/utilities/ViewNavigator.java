package com.example.PIIIProjectGUI.utilities;

import com.example.PIIIProjectGUI.inputoutput.OutputDevice;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class ViewNavigator {

    static private String viewsStringPath = "/com/example/PIIIProjectGUI/views/";

    // Stack to keep track of open stages
    private static final Stack<Stage> stageStack = new Stack<>();

    // Window title shown for each view (key = FXML file name without the extension)
    private static final Map<String, String> viewTitles = Map.ofEntries(
            Map.entry("login-view", "Login"),
            Map.entry("register-view", "Register"),
            Map.entry("character-creation-view", "Create Character"),
            Map.entry("main-menu", "Main Menu"),
            Map.entry("inventory-view", "Inventory"),
            Map.entry("playerStats-view", "Character Stats"),
            Map.entry("loot-screen", "Treasure Chest"),
            Map.entry("battle-view", "Battle"),
            Map.entry("accounts_admin_screen", "Accounts Administration"),
            Map.entry("victory", "Victory"),
            Map.entry("youDied-view", "You Died")
    );

    private ViewNavigator() {
        // Prevent instantiation
    }

    // Returns the window title for the given view. Falls back to the game name for unknown views.
    public static String getTitle(String fxmlFileName) {
        return viewTitles.getOrDefault(StringUtilities.stripFXMLExtension(fxmlFileName), "Simple RPG");
    }

    public static int getNumberOfStagesInStack()
    {
        return stageStack.toArray().length;
    }

    /**
     * Open a new window and push it onto the stack.
     */
    public static void openNewWindow(String fxmlFileName, String title) {
        fxmlFileName = StringUtilities.stripFXMLExtension(fxmlFileName) + ".fxml";

        try {
            Parent root = FXMLLoader.load(ViewNavigator.class.getResource( viewsStringPath + fxmlFileName));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            stageStack.push(stage);

            // Remove from stack when closed
            stage.setOnCloseRequest(event -> stageStack.remove(stage));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Switch scene on the topmost stage.
    public static void switchScene(String fxmlFileName) {
        fxmlFileName = StringUtilities.stripFXMLExtension(fxmlFileName) + ".fxml";
        String fxmlPath = viewsStringPath + fxmlFileName;

        if (!stageStack.isEmpty()) {
            Stage currentStage = stageStack.peek();
            try {
                Parent root = FXMLLoader.load(ViewNavigator.class.getResource(fxmlPath));
                currentStage.setScene(new Scene(root));
                currentStage.setTitle(getTitle(fxmlFileName));
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            OutputDevice.printError("You used the ViewNavigator.switchScene() method, but there is no stage for a scene to be replaced.");
        }
    }

    // Close the topmost stage and return to the previous one (if any).
    public static void closeTopStage() {
        if (!stageStack.isEmpty()) {
            Stage topStage = stageStack.pop();
            topStage.close();
        }
        else
            OutputDevice.printError("You used closeTopStage method but stageStack is empty, nothing to close.");

    }

    /**
     * Get the topmost stage without closing it.
     */
    public static Stage getTopStage() {
        return stageStack.isEmpty() ? null : stageStack.peek();
    }

    // Close all open stages and replace them with a single new stage.
    public static void replaceAllStageWindowsWithASingleOne(String fxmlFileName, String title) {
        String fxmlPath = viewsStringPath + StringUtilities.stripFXMLExtension(fxmlFileName) + ".fxml";

        try {
            // Close all open stages
            while (!stageStack.isEmpty()) {
                Stage stage = stageStack.pop();
                stage.close();
            }

            // Open the new stage
            Parent root = FXMLLoader.load(ViewNavigator.class.getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            stageStack.push(stage);

            // Remove from stack when closed
            stage.setOnCloseRequest(event -> stageStack.remove(stage));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
