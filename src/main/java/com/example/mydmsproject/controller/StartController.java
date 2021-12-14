package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;

/**
 * A controller class for handling key/mouse event in start scene, provide
 * access to the game, settings and help page.
 * See code to get information about private methods.
 */
public class StartController {

    private Scenes m_scenes;

    /**
     * Store other scenes.
     * @param scenes the model class that stores all scenes and game elements
     * @see Scenes
     */
    public void initData(Scenes scenes) {
        m_scenes = scenes;
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

}
