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
 * @author Tianxiang Song
 */
public class StartController {

    private Scenes m_scenes;

    @FXML private Text m_title;
    @FXML private Button m_start;
    @FXML private Button m_settings;
    @FXML private Button m_help;
    @FXML private Pane m_helpPane;

    /**
     * Store other scenes.
     * @param scenes the model class that stores all scenes and game elements
     * @see Scenes
     */
    public void initData(Scenes scenes) {
        m_scenes = scenes;
        m_helpPane.setVisible(false);
    }

    /**
     * Change to game scene and initialize first level, change layout of
     * setting scene.
     * @see SettingController
     */
    @FXML
    private void startGame() {
        m_scenes.playSound("click");
        m_scenes.setGameTheme("Universe.jpg");
        m_scenes.getStage().setScene(m_scenes.getGameScene());
        m_scenes.getWall().initializeGame(m_scenes);
        m_scenes.changeSettingLayout();
        m_scenes.playMusic();
    }

    /**
     * Change to setting scene.
     */
    @FXML
    private void getSettings() {
        m_scenes.playSound("click");
        m_scenes.getStage().setScene(m_scenes.getSettingScene());
        m_scenes.setLastScene(m_scenes.getStartScene());
    }

    /**
     * Change to help layout.
     */
    @FXML
    private void getHelp() {
        m_scenes.playSound("click");
        m_title.setVisible(false);
        m_start.setVisible(false);
        m_settings.setVisible(false);
        m_help.setVisible(false);
        m_helpPane.setVisible(true);
    }

    /**
     * Change to main layout.
     */
    @FXML
    private void closeHelp() {
        m_scenes.playSound("click");
        m_title.setVisible(true);
        m_start.setVisible(true);
        m_settings.setVisible(true);
        m_help.setVisible(true);
        m_helpPane.setVisible(false);
    }

}
