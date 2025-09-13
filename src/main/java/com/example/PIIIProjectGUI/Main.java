package com.example.PIIIProjectGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        // Store args globally
        GameState.args = args;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/views/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("User Registration");
        stage.setScene(scene);
        stage.show();
    }






    public static void main(String[] args) {
        launch(args);
    }
}