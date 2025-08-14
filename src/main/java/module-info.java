module com.example.PIIIProjectGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;

    opens com.example.PIIIProjectGUI to javafx.fxml;
    opens com.example.PIIIProjectGUI.controllers to javafx.fxml;

    exports com.example.PIIIProjectGUI.controllers;
    exports com.example.PIIIProjectGUI;
}
