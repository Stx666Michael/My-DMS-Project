package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class EndController {

    private Scenes scenes;

    @FXML private Text score;

    public void initData(Scenes scenes) {
        this.scenes = scenes;
    }

    public void updateScore() {
        int score = scenes.getWall().getScore();
        this.score.setText(Integer.toString(score));
        this.score.setTextAlignment(TextAlignment.CENTER);
    }

    @FXML
    private void restart() {
        scenes.getStage().setScene(scenes.getGameScene());
        scenes.getWall().resetGame();
    }

    @FXML
    private void quitGame() {
        System.exit(0);
    }

}
