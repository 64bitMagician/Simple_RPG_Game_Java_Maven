module com.example.PIIIProjectGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;

    opens com.example.PIIIProjectGUI to javafx.fxml;
    opens com.example.PIIIProjectGUI.GUI.GUIControllers to javafx.fxml;

    exports com.example.PIIIProjectGUI.GUI.GUIControllers;
}
