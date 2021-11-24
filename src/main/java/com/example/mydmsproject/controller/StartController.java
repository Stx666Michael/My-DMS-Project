package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;

public class StartController {

    private Scenes scenes;

    public void initData(Scenes scenes) {
        this.scenes = scenes;
    }

    @FXML
    private void startGame() {
        scenes.getStage().setScene(scenes.getGameScene());
        scenes.getWall().initializeGame(scenes);
    }

    @FXML
    private void getSettings() {
        scenes.getStage().setScene(scenes.getSettingScene());
        scenes.setLastScene(scenes.getStartScene());
    }
}
