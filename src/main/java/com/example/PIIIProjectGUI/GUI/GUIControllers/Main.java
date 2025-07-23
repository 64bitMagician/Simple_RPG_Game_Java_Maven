package com.example.PIIIProjectGUI.GUI.GUIControllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Activate GameState instance
        String[] args = getParameters().getRaw().toArray(new String[0]);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/PIIIProjectGUI/register-view.fxml"));

        // Inject arguments via controller factory
        loader.setControllerFactory(param -> new LoginController(args));

        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("User Registration");
        stage.setScene(scene);
        stage.show();
    }





    public static void main(String[] args) {
        launch(args);
    }
}