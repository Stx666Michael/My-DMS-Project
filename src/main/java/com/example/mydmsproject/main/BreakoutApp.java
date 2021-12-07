package com.example.mydmsproject.main;

import com.example.mydmsproject.model.Scenes;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BreakoutApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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