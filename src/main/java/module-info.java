module com.example.PIIIProjectGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;
    requires java.sql;
    requires com.google.protobuf;

    opens com.example.PIIIProjectGUI to javafx.fxml;
    opens com.example.PIIIProjectGUI.controllers to javafx.fxml;
    opens com.example.PIIIProjectGUI.implementation.classes.database to javafx.base, javafx.fxml;


    exports com.example.PIIIProjectGUI.controllers;
    exports com.example.PIIIProjectGUI;
}
