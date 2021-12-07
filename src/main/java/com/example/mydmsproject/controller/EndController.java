package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class EndController {

    private Scenes m_scenes;
    private final TextInputDialog m_dialog = new TextInputDialog();
    private HashMap<String, Integer> m_scoreList = new LinkedHashMap<>();

    @FXML private Text m_title;
    @FXML private Text m_score;
    @FXML private Text m_ballLeft;
    @FXML private Text m_breakout;
    @FXML private Text m_list;
    @FXML private Button m_play;

    public void initData(Scenes scenes) {
        m_scenes = scenes;
        m_dialog.setTitle("Well Done!");
        m_dialog.setHeaderText("Enter your name:");
        updateScoreList();
    }

    public void setWinLayout() {
        m_title.setText("YOU  WIN");
        m_play.setText("Next Level");
        m_play.setOnAction(e -> playNextLevel());
    }

    public void setLoseLayout() {
        m_title.setText("GAME OVER");
        m_play.setText("Play Again");
        m_play.setOnAction(e -> restart());
    }

    public void updateScore() {
        final int LEFT_BALL_SCORE = 10;
        int ballLeftCount = m_scenes.getWall().getBall().getBallCount();
        int ballLeftScore = ballLeftCount * LEFT_BALL_SCORE;
        int lastLevelScore = m_scenes.getWall().getLastLevelScore();
        m_scenes.getWall().getBall().addScore(ballLeftScore);
        int score = m_scenes.getWall().getBall().getScore();
        m_ballLeft.setText("+" + ballLeftScore);
        m_breakout.setText("+" + (score-lastLevelScore-ballLeftScore));
        m_score.setText(Integer.toString(score));
        m_scenes.getWall().setLastLevelScore(score);
    }

    private void playNextLevel() {
        m_scenes.getWall().addCurrentLevel();
        m_scenes.getWall().resetGame(m_scenes.getWall().getCurrentLevel());
        m_scenes.getStage().setScene(m_scenes.getGameScene());
    }

    private void restart() {
        m_scenes.getWall().resetGame(1);
        m_scenes.getStage().setScene(m_scenes.getGameScene());
    }

    private void writeFile(String name, int score) {
        try {
            FileWriter myWriter = new FileWriter("scoreList.txt", true);
            myWriter.write(name + "," + score + "\n");
            myWriter.close();
            updateScoreList();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void updateScoreList() {
        try {
            File myObj = new File("scoreList.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                String name = data[0];
                Integer score = Integer.valueOf(data[1]);
                m_scoreList.put(name, score);
            }
            sortScoreList();
            updateListView();
            System.out.println(m_scoreList.toString());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void updateListView() {
        StringBuilder s = new StringBuilder();
        List<String> nameList = new ArrayList<>(m_scoreList.keySet());
        final int SHOW_TOP_SCORE = 5;
        for (String name : nameList.subList(0, SHOW_TOP_SCORE)) {
            s.append(name).append(" : ")
                    .append(m_scoreList.get(name)).append("\n");
        }
        m_list.setText(s.toString());
    }

    private void sortScoreList() {
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(m_scoreList.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        HashMap<String, Integer> sortedList = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedList.put(entry.getKey(), entry.getValue());
        }
        m_scoreList = sortedList;
    }

    @FXML
    private void saveScore() {
        Optional<String> result = m_dialog.showAndWait();
        result.ifPresent(name -> {
            if (name.isEmpty() || name.contains(",")) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Invalid name! Please try again.");
                a.show();
            }
            else if (m_scoreList.containsKey(name)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Name exists! Please try again.");
                a.show();
            }
            else writeFile(name, m_scenes.getWall().getBall().getScore());
        });
    }

    @FXML
    private void quitGame() {
        System.exit(0);
    }

}
