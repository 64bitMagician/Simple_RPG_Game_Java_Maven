package com.example.PIIIProjectGUI.GUI.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WelcomeController {
    @FXML private ImageView welcomeImage;

    @FXML
    public void initialize() {
        // Use any online image or a local file
        Image image = new Image("https://t3.ftcdn.net/jpg/00/87/97/06/360_F_87970620_Tdgw6WYdWnrZHn2uQwJpVDH4vr4PINSc.jpg", true);
        welcomeImage.setImage(image);
    }
}
