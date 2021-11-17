package com.example.mydmsproject.main;

import com.example.mydmsproject.model.scenes.StartScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BreakoutApp extends Application {

    private static final String TITLE = "Breakout";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private static Stage stage;

    private static StartScreen start;
    private static Scene startScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        BreakoutApp.stage = theStage;
        stage.setTitle(TITLE);

        start = new StartScreen(stage, WIDTH, HEIGHT);
        startScene = new Scene(start, WIDTH, HEIGHT);
        stage.setScene(startScene);

        stage.show();
    }

}