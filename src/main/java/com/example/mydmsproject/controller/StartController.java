package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;

public class StartController {

    private Scenes m_scenes;

    public void initData(Scenes scenes) {
        m_scenes = scenes;
    }

    @FXML
    private void startGame() {
        m_scenes.getStage().setScene(m_scenes.getGameScene());
        m_scenes.getWall().initializeGame(m_scenes);
        ((SettingController) m_scenes.getSettingLoader().getController())
                .changeLayout();
    }

    @FXML
    private void getSettings() {
        m_scenes.getStage().setScene(m_scenes.getSettingScene());
        m_scenes.setLastScene(m_scenes.getStartScene());
    }

}
