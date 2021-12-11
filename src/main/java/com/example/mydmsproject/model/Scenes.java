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

/**
 * A model class that stores all scenes and game elements,
 * used for controllers and renderer to get/set scenes and other elements.
 */
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

    /**
     * Get the boolean value indicates setting scene is not shown.
     * @return a boolean value indicates setting scene is not shown
     */
    public boolean isNotSetting() {
        return !m_isSetting;
    }

    /**
     * Get the primary stage of the game.
     * @return the primary stage of the game
     */
    public Stage getStage() {
        return m_stage;
    }

    /**
     * Get the scene of the start screen.
     * @return the scene of the start screen
     */
    public Scene getStartScene() {
        return m_startScene;
    }

    /**
     * Get the scene of the setting/pause menu.
     * @return the scene of the setting/pause menu
     */
    public Scene getSettingScene() {
        return m_settingScene;
    }

    /**
     * Get the scene of the game screen.
     * @return the scene of the game screen
     */
    public Scene getGameScene() {
        return m_gameScene;
    }

    /**
     * Get the scene of the end screen.
     * @return the scene of the end screen
     */
    public Scene getEndScene() {
        return m_endScene;
    }

    /**
     * Get the last scene shown in the stage.
     * @return the last scene shown in the stage
     */
    public Scene getLastScene() {
        return m_lastScene;
    }

    /**
     * Get the wall of game elements.
     * @return the wall of game elements
     * @see Wall
     */
    public Wall getWall() {
        return m_wall;
    }

    /**
     * Get the controller of setting scene.
     * @return the controller of setting scene
     * @see SettingController
     */
    public SettingController getSettingController() {
        return m_settingLoader.getController();
    }

    /**
     * Get the controller of end scene.
     * @return the controller of end scene
     * @see EndController
     */
    public EndController getEndController() {
        return m_endLoader.getController();
    }

    /**
     * Set the boolean value indicates if setting scene is shown.
     * @param setting the boolean value to set
     */
    public void setSetting(boolean setting) {
        m_isSetting = setting;
    }

    /**
     * Set the last scene shown in the stage.
     * @param lastScene the last scene
     */
    public void setLastScene(Scene lastScene) {
        m_lastScene = lastScene;
    }

    /**
     * Set the background of the game scene.
     * @param color a string represents the color
     */
    public void setGameTheme(String color) {
        m_game.setStyle("-fx-background-color: " + color);
    }

    /**
     * Default class constructor, initialize all the scenes and
     * set start scene to the stage.
     * @param width the width of the stage
     * @param height the height of the stage
     * @param stage the primary stage
     * @throws IOException signals that an I/O exception has occurred
     */
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

    /**
     * Initialize a pane on the game scene containing game elements.
     * @param width the width of the game scene
     * @param height the height of the game scene
     * @return the pane containing game elements
     * @see Wall
     */
    private Pane gamePane(int width, int height) {
        Pane game = new Pane();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        m_wall = new Wall(width, height, gc);
        game.getChildren().add(canvas);

        return game;
    }

}
