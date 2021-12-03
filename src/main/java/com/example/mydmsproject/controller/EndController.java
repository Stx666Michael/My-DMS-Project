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

    private Scenes scenes;
    private int currentLevel = 1;
    private TextInputDialog dialog = new TextInputDialog();
    private HashMap<String, Integer> scoreList = new LinkedHashMap<>();

    @FXML private Text title;
    @FXML private Text score;
    @FXML private Text list;
    @FXML private Button play;

    public void initData(Scenes scenes) {
        this.scenes = scenes;
        dialog.setTitle("Well Done!");
        dialog.setHeaderText("Enter your name:");
        updateScoreList();
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
                scoreList.put(name, score);
            }
            sortScoreList();
            updateListView();
            System.out.println(scoreList.toString());
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void updateListView() {
        String s = "";
        List<String> nameList = new ArrayList<>(scoreList.keySet());
        for (String name : nameList.subList(0, 5)) {
            s += name + " : " + scoreList.get(name) + "\n";
        }
        list.setText(s);
    }

    private void sortScoreList() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scoreList.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        HashMap<String, Integer> sortedList = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedList.put(entry.getKey(), entry.getValue());
        }
        scoreList = sortedList;
    }

    @FXML
    private void saveScore() {
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {

            if (name.isEmpty() || name.contains(",")) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Invalid name! Please try again.");
                a.show();
            }
            else if (scoreList.containsKey(name)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Name exists! Please try again.");
                a.show();
            }
            else
                writeFile(name, scenes.getWall().getScore());
        });
    }

    @FXML
    private void quitGame() {
        System.exit(0);
    }

}
