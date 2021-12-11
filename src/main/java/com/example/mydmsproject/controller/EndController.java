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

/**
 * A controller class for handling key/mouse event and set score content
 * in end scene (including end of a level and end of game).
 * See code to get information about private methods.
 */
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

    /**
     * Initialize dialog of entering player name and store other scenes.
     * @param scenes the model class that stores all scenes and game elements
     * @see Scenes
     */
    public void initData(Scenes scenes) {
        m_scenes = scenes;
        m_dialog.setTitle("Well Done!");
        m_dialog.setHeaderText("Enter your name:");
        updateScoreList();
    }

    /**
     * Set the layout when a level passed.
     */
    public void setWinLayout() {
        m_title.setText("YOU  WIN");
        m_play.setText("Next Level");
        m_play.setOnAction(e -> playNextLevel());
    }

    /**
     * Set the layout when the game ends.
     */
    public void setLoseLayout() {
        m_title.setText("GAME OVER");
        m_play.setText("Play Again");
        m_play.setOnAction(e -> restart());
    }

    /**
     * Get total score and score from current level, set them to the layout.
     */
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

    /**
     * Change to game scene and initialize next level.
     */
    private void playNextLevel() {
        m_scenes.getWall().addCurrentLevel();
        m_scenes.getWall().resetGame(m_scenes.getWall().getCurrentLevel());
        m_scenes.getStage().setScene(m_scenes.getGameScene());
    }

    /**
     * Change to game scene and reset the game to first level.
     */
    private void restart() {
        m_scenes.getWall().resetGame(1);
        m_scenes.getStage().setScene(m_scenes.getGameScene());
    }

    /**
     * Store player name and score to a text file.
     * @param name user input player name
     * @param score total score in the game
     */
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

    /**
     * Read the text file to get player name and score and store them to
     * a hash map.
     */
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

    /**
     * Set top players' name and score to the layout.
     */
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

    /**
     * Sort the name-score list by score in descending order.
     */
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

    /**
     * Open a dialog to let user enter their name, store name and score if
     * name is valid and new to the existing list, else pop a warning alert.
     */
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

    /**
     * Exit the whole program.
     */
    @FXML
    private void quitGame() {
        System.exit(0);
    }

}
