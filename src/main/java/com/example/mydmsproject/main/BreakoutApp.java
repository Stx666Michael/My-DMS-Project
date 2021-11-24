package com.example.mydmsproject.main;

import com.example.mydmsproject.model.Scenes;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class BreakoutApp extends Application {

    private static final String TITLE = "Breakout";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) throws IOException {
        BreakoutApp.stage = theStage;
        stage.setTitle(TITLE);
        new Scenes(WIDTH, HEIGHT, stage);
        stage.show();
    }

}