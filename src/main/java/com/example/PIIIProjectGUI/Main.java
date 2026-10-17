package com.example.PIIIProjectGUI;

import com.example.PIIIProjectGUI.utilities.ViewNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        // Store args globally
        GameState.args = args;

        ViewNavigator.openNewWindow("login-view.fxml", "Login");
    }






    public static void main(String[] args) {
        launch(args);
    }
}