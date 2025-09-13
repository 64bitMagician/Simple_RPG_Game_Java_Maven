package com.example.PIIIProjectGUI.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class ViewNavigator {

    // Stack to keep track of open stages
    private static final Stack<Stage> stageStack = new Stack<>();

    private ViewNavigator() {
        // Prevent instantiation
    }

    /**
     * Open a new window and push it onto the stack.
     */
    public static void openNewWindow(String fxmlFileName, String title) {
        fxmlFileName = StringUtilities.stripFXMLExtension(fxmlFileName) + ".fxml";

        try {
            Parent root = FXMLLoader.load(ViewNavigator.class.getResource("com/example/PIIIProjectGUI/veiws/" + ));
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

    /**
     * Switch scene on the topmost stage.
     */
    public static void switchScene(String fxmlPath) {
        if (!stageStack.isEmpty()) {
            Stage currentStage = stageStack.peek();
            try {
                Parent root = FXMLLoader.load(ViewNavigator.class.getResource(fxmlPath));
                currentStage.setScene(new Scene(root));
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close the topmost stage and return to the previous one (if any).
     */
    public static void closeTopStage() {
        if (!stageStack.isEmpty()) {
            Stage topStage = stageStack.pop();
            topStage.close();
        }
    }

    /**
     * Get the topmost stage without closing it.
     */
    public static Stage getTopStage() {
        return stageStack.isEmpty() ? null : stageStack.peek();
    }
}
