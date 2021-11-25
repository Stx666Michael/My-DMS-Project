package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

public class SettingController {

    private Scenes scenes;

    @FXML private Slider ballSpeed;
    @FXML private Slider paddleSpeed;
    @FXML private ComboBox<String> theme;
    @FXML private ComboBox<String> control;

    public void initData(Scenes scenes) {
        this.scenes = scenes;
    }

    @FXML
    private void initialize() {
        ballSpeed.setValue(2);
        paddleSpeed.setValue(2);
        theme.setPromptText("Light");
        control.setPromptText("Keyboard");
    }

    @FXML
    private void setTheme() {
        String s = theme.getSelectionModel().getSelectedItem();
        switch (s) {
            case "Light" -> scenes.setGameTheme("transparent");
            case "Dark" -> scenes.setGameTheme("rgb(50, 50, 50)");
        }
    }

    @FXML
    private void setControl() {
        String s = control.getSelectionModel().getSelectedItem();
        if (s.equals("Keys"))
            scenes.getWall().getPlayer().setMoveControl(1);
        else if (s.equals("Mouse"))
            scenes.getWall().getPlayer().setMoveControl(2);
    }

    @FXML
    private void quitGame() {
        System.exit(0);
    }

    @FXML
    private void confirm() {
        scenes.getWall().setBallSpeedBound(ballSpeed.getValue());
        scenes.getWall().getPlayer().setMoveSpeed(paddleSpeed.getValue());
        scenes.getStage().setScene(scenes.getLastScene());
        scenes.setSetting(false);
    }
}
