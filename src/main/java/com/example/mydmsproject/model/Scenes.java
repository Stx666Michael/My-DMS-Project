package com.example.mydmsproject.model;

import com.example.mydmsproject.controller.EndController;
import com.example.mydmsproject.controller.SettingController;
import com.example.mydmsproject.controller.StartController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

/**
 * A model class that stores all scenes, game elements and sounds,
 * act as a mediator between controllers, used for getting/setting
 * scenes and other elements.
 */
public class Scenes {

    private final Stage m_stage;
    private final Scene m_startScene;
    private final Scene m_settingScene;
    private final Scene m_gameScene;
    private final Scene m_endScene;
    private final Pane m_game;
    private final Media m_break;
    private final Media m_buff;
    private final Media m_click;
    private final MediaPlayer m_bgm;
    private final SettingController m_settingController;
    private final EndController m_endController;

    private Scene m_lastScene;
    private Wall m_wall;
    private boolean m_isSetting = false;
    private boolean m_isMutedEffects = false;

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
     * Set the boolean value indicates if setting scene is shown.
     * @param setting the boolean value to set to
     */
    public void setSetting(boolean setting) {
        m_isSetting = setting;
    }

    /**
     * Set the boolean value indicates if effect audio is muted.
     * @param isMutedEffects the boolean value to set to
     */
    public void setMutedEffects(boolean isMutedEffects) {
        m_isMutedEffects = isMutedEffects;
    }

    /**
     * Set whether mute the background audio.
     * @param isMutedBgm whether mute the background audio
     */
    public void setMutedBgm(boolean isMutedBgm) {
        m_bgm.setMute(isMutedBgm);
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
     * @param name name of background image
     */
    public void setGameTheme(String name) {
        Image image = new Image("file:src/main/resources/com/example/" +
                "mydmsproject/"+name);
        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        m_game.setBackground(new Background(bgImage));
    }

    /**
     * Default class constructor, initialize all the scenes, sounds and
     * set start scene to the stage.
     * @param width the width of the stage
     * @param height the height of the stage
     * @param stage the primary stage
     * @throws IOException signals that an I/O exception has occurred
     */
    public Scenes(int width, int height, Stage stage) throws IOException {
        m_stage = stage;
        m_game = gamePane(width, height);

        FXMLLoader start = new FXMLLoader
                (GameRenderer.class.getResource("start.fxml"));
        FXMLLoader settings = new FXMLLoader
                (GameRenderer.class.getResource("settings.fxml"));
        FXMLLoader end = new FXMLLoader
                (GameRenderer.class.getResource("end.fxml"));

        m_startScene = new Scene(start.load(), width, height);
        m_settingScene = new Scene(settings.load(), width, height);
        m_endScene = new Scene(end.load(), width, height);
        m_gameScene = new Scene(m_game, width, height);

        final String PATH = "src/main/resources/com/example/" +
                "mydmsproject/audio/";
        Media m_background = new Media
                (new File(PATH+"Background.wav").toURI().toString());
        m_break = new Media(new File(PATH+"Break.wav").toURI().toString());
        m_buff = new Media(new File(PATH+"Buff.wav").toURI().toString());
        m_click = new Media(new File(PATH+"Click.wav").toURI().toString());
        m_bgm = new MediaPlayer(m_background);

        StartController startController = start.getController();
        startController.initData(this);

        m_settingController = settings.getController();
        m_settingController.initData(this);

        m_endController = end.getController();
        m_endController.initData(this);

        stage.setScene(m_startScene);
    }

    /**
     * Play sound of specified type.
     * @param type the type of sound
     */
    public void playSound(String type) {
        if (!m_isMutedEffects) {
            MediaPlayer mediaPlayer;
            switch (type) {
                case "break" -> mediaPlayer = new MediaPlayer(m_break);
                case "buff" -> mediaPlayer = new MediaPlayer(m_buff);
                default -> mediaPlayer = new MediaPlayer(m_click);
            }
            mediaPlayer.play();
        }
    }

    /**
     * Play background music of game repeatedly.
     */
    public void playMusic() {
        m_bgm.play();
        m_bgm.setOnEndOfMedia(() -> m_bgm.seek(Duration.ZERO));
    }

    /**
     * Change the layout of setting scene.
     * @see SettingController
     */
    public void changeSettingLayout() {
        m_settingController.changeLayout();
    }

    /**
     * Set the layout of end scene and update final score.
     * @param result "win" or "lose", indicate which kind of layout to set
     * @see EndController
     */
    public void setEndLayout(String result) {
        switch (result) {
            case "win" -> m_endController.setWinLayout();
            case "lose" -> m_endController.setLoseLayout();
        }
        m_endController.updateScore();
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

        m_wall = new Wall(width, height, this, gc);
        game.getChildren().add(canvas);

        return game;
    }

}
