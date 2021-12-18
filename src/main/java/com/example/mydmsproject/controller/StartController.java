package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * A controller class for handling key/mouse event in start scene, provide
 * access to the game, settings and help page.
 * See code to get information about private methods.
 */
public class StartController {

    private Scenes m_scenes;

    @FXML private Text title;
    @FXML private Button start;
    @FXML private Button settings;
    @FXML private Button help;
    @FXML private Pane helpPane;

    /**
     * Store other scenes.
     * @param scenes the model class that stores all scenes and game elements
     * @see Scenes
     */
    public void initData(Scenes scenes) {
        m_scenes = scenes;
        helpPane.setVisible(false);
    }

    /**
     * Change to game scene and initialize first level, change layout of
     * setting scene.
     * @see SettingController
     */
    @FXML
    private void startGame() {
        m_scenes.setGameTheme("Universe.jpg");
        m_scenes.getStage().setScene(m_scenes.getGameScene());
        m_scenes.getWall().initializeGame(m_scenes);
        m_scenes.getSettingController().changeLayout();
    }

    /**
     * Change to setting scene.
     */
    @FXML
    private void getSettings() {
        m_scenes.getStage().setScene(m_scenes.getSettingScene());
        m_scenes.setLastScene(m_scenes.getStartScene());
    }

    /**
     * Change to help layout.
     */
    @FXML
    private void getHelp() {
        title.setVisible(false);
        start.setVisible(false);
        settings.setVisible(false);
        help.setVisible(false);
        helpPane.setVisible(true);
    }

    /**
     * Change to main layout.
     */
    @FXML
    private void closeHelp() {
        title.setVisible(true);
        start.setVisible(true);
        settings.setVisible(true);
        help.setVisible(true);
        helpPane.setVisible(false);
    }

}
