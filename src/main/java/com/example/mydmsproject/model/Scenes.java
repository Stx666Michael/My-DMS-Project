package com.example.mydmsproject.model;

import com.example.mydmsproject.controller.EndController;
import com.example.mydmsproject.controller.SettingController;
import com.example.mydmsproject.controller.StartController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Scenes {

    private final Stage m_stage;
    private final Scene m_startScene;
    private final Scene m_settingScene;
    private final Scene m_gameScene;
    private final Scene m_endScene;
    private final Pane m_game;
    private final FXMLLoader m_settingLoader;
    private final FXMLLoader m_endLoader;

    private Scene m_lastScene;
    private Wall m_wall;
    private boolean m_isSetting;

    public boolean isNotSetting() {
        return !m_isSetting;
    }

    public Stage getStage() {
        return m_stage;
    }

    public Scene getStartScene() {
        return m_startScene;
    }

    public Scene getSettingScene() {
        return m_settingScene;
    }

    public Scene getGameScene() {
        return m_gameScene;
    }

    public Scene getEndScene() {
        return m_endScene;
    }

    public Scene getLastScene() {
        return m_lastScene;
    }

    public Wall getWall() {
        return m_wall;
    }

    public FXMLLoader getSettingLoader() {
        return m_settingLoader;
    }

    public FXMLLoader getEndLoader() {
        return m_endLoader;
    }

    public void setSetting(boolean setting) {
        m_isSetting = setting;
    }

    public void setLastScene(Scene lastScene) {
        m_lastScene = lastScene;
    }

    public void setGameTheme(String color) {
        m_game.setStyle("-fx-background-color: " + color);
    }

    public Scenes(int width, int height, Stage stage) throws IOException {
        m_stage = stage;
        m_isSetting = false;
        m_game = gamePane(width, height);

        FXMLLoader start = new FXMLLoader
                (GameRenderer.class.getResource("start.fxml"));
        FXMLLoader settings = new FXMLLoader
                (GameRenderer.class.getResource("settings.fxml"));
        FXMLLoader end = new FXMLLoader
                (GameRenderer.class.getResource("end.fxml"));

        m_settingLoader = settings;
        m_endLoader = end;

        m_startScene = new Scene(start.load(), width, height);
        m_settingScene = new Scene(settings.load(), width, height);
        m_endScene = new Scene(end.load(), width, height);
        m_gameScene = new Scene(m_game, width, height);

        StartController startController = start.getController();
        startController.initData(this);

        SettingController settingController = settings.getController();
        settingController.initData(this);

        EndController endController = end.getController();
        endController.initData(this);

        stage.setScene(m_startScene);
    }

    private Pane gamePane(int width, int height) {
        Pane game = new Pane();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        m_wall = new Wall(width, height, gc);
        game.getChildren().add(canvas);

        return game;
    }

}
