package com.example.mydmsproject.main;

import com.example.mydmsproject.model.Scenes;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main JavaFX application class containing the main method.
 * @author Tianxiang Song
 */
public class BreakoutApp extends Application {

    /**
     * The main method to start a JavaFX application.
     * @param args default param of the main method
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for the JavaFX applications.
     * @param stage the primary stage for this application,
     *              onto which the application scene can be set
     * @throws IOException signals that an I/O exception has occurred
     */
    @Override
    public void start(Stage stage) throws IOException {
        final String TITLE = "Breakout";
        final int WIDTH = 600;
        final int HEIGHT = 450;

        stage.setTitle(TITLE);
        new Scenes(WIDTH, HEIGHT, stage);
        stage.show();
    }

}