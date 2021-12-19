package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

/**
 * A controller class for handling key/mouse event in setting scene (pause
 * menu), to change game theme, paddle control and speed of ball/paddle.
 * See code to get information about private methods.
 */
public class SettingController {

    private Scenes m_scenes;

    @FXML private Text m_title;
    @FXML private Slider m_ballSpeed;
    @FXML private Slider m_paddleSpeed;
    @FXML private ComboBox<String> m_theme;
    @FXML private ComboBox<String> m_control;
    @FXML private Button m_restart;
    @FXML private CheckBox m_effect;
    @FXML private CheckBox m_background;

    /**
     * Store other scenes.
     * @param scenes the model class that stores all scenes and game elements
     * @see Scenes
     */
    public void initData(Scenes scenes) {
        m_scenes = scenes;
    }

    /**
     * Change the title to "Pause Menu" and add restart button to the layout
     * when in game scene.
     */
    public void changeLayout() {
        m_title.setText("Pause Menu");
        m_restart.setVisible(true);
    }

    /**
     * Initialize the layout in start scene (set title to "Settings").
     */
    @FXML
    private void initialize() {
        m_ballSpeed.setValue(2);
        m_paddleSpeed.setValue(2);
        m_theme.setPromptText("Universe");
        m_control.setPromptText("Keyboard");
        m_effect.setSelected(true);
        m_background.setSelected(true);
        m_restart.setVisible(false);
    }

    /**
     * Set the background of game scene.
     */
    @FXML
    private void setTheme() {
        String s = m_theme.getSelectionModel().getSelectedItem();
        switch (s) {
            case "Universe" -> m_scenes.setGameTheme("Universe.jpg");
            case "Stars" -> m_scenes.setGameTheme("Stars.jpg");
            case "Sky" -> m_scenes.setGameTheme("Sky.jpg");
        }
    }

    /**
     * Set the control method of paddle.
     */
    @FXML
    private void setControl() {
        String s = m_control.getSelectionModel().getSelectedItem();
        final int KEYBOARD = 1;
        final int MOUSE = 2;
        if (s.equals("Keyboard"))
            m_scenes.getWall().getPlayer().setMoveControl(KEYBOARD);
        else if (s.equals("Mouse"))
            m_scenes.getWall().getPlayer().setMoveControl(MOUSE);
    }

    /**
     * Set whether mute the effect audio.
     */
    @FXML
    private void setEffectAudio() {
        boolean isSelected = m_effect.isSelected();
        m_scenes.setMutedEffects(!isSelected);
    }

    /**
     * Set whether mute the background audio.
     */
    @FXML
    private void setBackgroundAudio() {
        boolean isSelected = m_background.isSelected();
        m_scenes.setMutedBgm(!isSelected);
    }

    /**
     * Exit the whole program.
     */
    @FXML
    private void quitGame() {
        System.exit(0);
    }

    /**
     * Change to game scene and reset the game to first level.
     */
    @FXML
    private void restart() {
        m_scenes.playSound("click");
        final int INITIAL_LEVEL = 1;
        m_scenes.getWall().resetGame(INITIAL_LEVEL);
        m_scenes.getStage().setScene(m_scenes.getGameScene());
        m_scenes.setSetting(false);
    }

    /**
     * Confirm settings and change to previous scene.
     */
    @FXML
    private void confirm() {
        m_scenes.playSound("click");
        m_scenes.getWall().setBallInitialSpeed(m_ballSpeed.getValue());
        m_scenes.getWall().getPlayer().setMoveSpeed(m_paddleSpeed.getValue());
        m_scenes.getStage().setScene(m_scenes.getLastScene());
        m_scenes.setSetting(false);
    }

}
