package com.example.mydmsproject.model;

import com.example.mydmsproject.controller.EndController;
import com.example.mydmsproject.controller.SettingController;
import com.example.mydmsproject.controller.StartController;
import com.example.mydmsproject.main.BreakoutApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Scenes {

    private final Stage stage;
    private final Scene startScene;
    private final Scene settingScene;
    private final Scene gameScene;
    private final Scene endScene;
    private Scene lastScene;
    private Wall wall;
    private Pane game;
    private boolean isSetting;
    private final FXMLLoader settingLoader;
    private final FXMLLoader endLoader;

    public Scenes(int width, int height, Stage stage) throws IOException {
        this.stage = stage;
        isSetting = false;
        game = gamePane(width, height);

        FXMLLoader start = new FXMLLoader(BreakoutApp.class.getResource("start.fxml"));
        FXMLLoader settings = new FXMLLoader(BreakoutApp.class.getResource("settings.fxml"));
        FXMLLoader end = new FXMLLoader(BreakoutApp.class.getResource("end.fxml"));

        settingLoader = settings;
        endLoader = end;

        startScene = new Scene(start.load(), width, height);
        settingScene = new Scene(settings.load(), width, height);
        endScene = new Scene(end.load(), width, height);
        gameScene = new Scene(game, width, height);

        StartController startController = start.getController();
        startController.initData(this);

        SettingController settingController = settings.getController();
        settingController.initData(this);

        EndController endController = end.getController();
        endController.initData(this);

        stage.setScene(startScene);
    }

    private Pane gamePane(int width, int height) {
        Pane game = new Pane();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        wall = new Wall(width, height, gc);
        game.getChildren().add(canvas);

        return game;
    }

    public boolean isSetting() {
        return isSetting;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getStartScene() {
        return startScene;
    }

    public Scene getSettingScene() {
        return settingScene;
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public Scene getEndScene() {
        return endScene;
    }

    public Scene getLastScene() {
        return lastScene;
    }

    public Wall getWall() {
        return wall;
    }

    public FXMLLoader getSettingLoader() {
        return settingLoader;
    }

    public FXMLLoader getEndLoader() {
        return endLoader;
    }

    public void setSetting(boolean setting) {
        isSetting = setting;
    }

    public void setLastScene(Scene lastScene) {
        this.lastScene = lastScene;
    }

    public void setGameTheme(String color) {
        game.setStyle("-fx-background-color: " + color);
    }
}
