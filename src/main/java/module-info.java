/**
 * The module of COMP2013 coursework by Tianxiang Song 20217424.
 */
module com.example.mydmsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    exports com.example.mydmsproject.main;
    opens com.example.mydmsproject.main to javafx.fxml;
    exports com.example.mydmsproject.controller;
    opens com.example.mydmsproject.controller to javafx.fxml;
    exports com.example.mydmsproject.model;
    opens com.example.mydmsproject.model to javafx.fxml;
    exports com.example.mydmsproject.view;
}