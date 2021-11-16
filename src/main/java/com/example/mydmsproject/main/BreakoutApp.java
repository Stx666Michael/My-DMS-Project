package com.example.mydmsproject.main;

import com.example.mydmsproject.model.StartScreen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BreakoutApp extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private static Stage stage;

    private static StartScreen start;
    //private static GameScreen game;

    private static Scene startScene;
    //private static Scene gameScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        BreakoutApp.stage = theStage;
        stage.setTitle("Breakout");

        start = new StartScreen(stage, WIDTH, HEIGHT);
        startScene = new Scene(start, WIDTH, HEIGHT);
        stage.setScene(startScene);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            System.out.print(">");
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        stage.show();
    }

}