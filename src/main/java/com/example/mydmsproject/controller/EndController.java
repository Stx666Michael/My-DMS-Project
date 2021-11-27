package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class EndController {

    private Scenes scenes;
    private int currentLevel = 1;

    @FXML private Text title;
    @FXML private Text score;
    @FXML private Button play;

    public void initData(Scenes scenes) {
        this.scenes = scenes;
    }

    public void setWinLayout() {
        title.setText("YOU  WIN");
        play.setText("Next Level");
        play.setOnAction(e -> playNextLevel());
    }

    public void setLoseLayout() {
        title.setText("GAME OVER");
        play.setText("Play Again");
        play.setOnAction(e -> restart());
    }

    public void updateScore() {
        int score = scenes.getWall().getScore();
        this.score.setText(Integer.toString(score));
    }

    private void playNextLevel() {
        currentLevel++;
        scenes.getWall().resetGame(currentLevel);
        scenes.getStage().setScene(scenes.getGameScene());
    }

    private void restart() {
        scenes.getWall().resetGame(1);
        scenes.getStage().setScene(scenes.getGameScene());
    }

    @FXML
    private void quitGame() {
        System.exit(0);
    }

}
