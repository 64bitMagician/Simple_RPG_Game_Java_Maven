// The MySQL driver jar has no module descriptor, so it is used as an automatic module.
@SuppressWarnings({"requires-automatic", "module"})
module com.example.PIIIProjectGUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;
    requires java.sql;
    requires mysql.connector.j;

    // The app is not a library, so nothing is exported. JavaFX only needs reflective access:
    // javafx.graphics launches Main, javafx.fxml creates the controllers, javafx.base reads table properties.
    opens com.example.PIIIProjectGUI to javafx.graphics, javafx.fxml;
    opens com.example.PIIIProjectGUI.controllers to javafx.fxml;
    opens com.example.PIIIProjectGUI.implementation.classes.database to javafx.base, javafx.fxml;
}
