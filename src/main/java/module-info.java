module com.example.mydmsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.mydmsproject to javafx.fxml;
    exports com.example.mydmsproject.main;
    opens com.example.mydmsproject.main to javafx.fxml;
    exports com.example.mydmsproject.controller;
    opens com.example.mydmsproject.controller to javafx.fxml;
    exports com.example.mydmsproject.controller.old;
    opens com.example.mydmsproject.controller.old to javafx.fxml;
    exports com.example.mydmsproject.model;
    opens com.example.mydmsproject.model to javafx.fxml;
}