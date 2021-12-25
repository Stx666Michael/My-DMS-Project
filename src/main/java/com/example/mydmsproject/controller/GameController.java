package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.util.Duration;
import java.util.ArrayList;

/**
 * A controller class for handling key/mouse event,
 * checking impacts and updating states of all objects in game scene.
 * See code to get information about private methods.
 * @author Tianxiang Song
 */
public class GameController {

    private final int REFRESH_TIME = 5;

    private final int m_width;
    private final int m_height;
    private final Wall m_wall;
    private final Ball m_ball;
    private final Paddle m_player;
    private final Scenes m_scenes;
    private final Scene m_gameScene;
    private final Timeline m_timeline;
    private final ArrayList<Brick> m_bricks;
    private final ArrayList<String> m_input = new ArrayList<>();

    private boolean m_isBegin = false;

    /**
     * Default class constructor, initialize a timeline for updating all
     * objects in game scene, refresh periodically.
     * @param scenes the model class that stores all scenes and game elements
     * @see Scenes
     */
    public GameController(Scenes scenes) {
        m_scenes = scenes;
        m_gameScene = scenes.getGameScene();
        m_width = (int) m_gameScene.getWidth();
        m_height = (int) m_gameScene.getHeight();
        m_wall = scenes.getWall();
        m_ball = m_wall.getBall();
        m_player = m_wall.getPlayer();
        m_bricks = m_wall.getBricks();

        m_gameScene.setOnKeyPressed(this::keyPressEvent);
        m_gameScene.setOnKeyReleased(this::keyReleaseEvent);
        m_gameScene.setOnMouseMoved(this::mouseMoveEvent);

        windowFocusEvent(m_scenes.getStage());

        m_timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_TIME),
                event -> update()));
        m_timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Handling event when key pressed, including pause/start game, open
     * pause menu and move paddle (in keyboard control mode).
     * @param e the event when key pressed
     * @see SettingController
     */
    private void keyPressEvent(KeyEvent e) {
        String code = e.getCode().toString();
        if (!m_input.contains(code)) m_input.add(code);
        if (e.getCode() == KeyCode.SPACE) {
            if (m_isBegin) setGameState("stop");
            else setGameState("play");
        }
        if (e.getCode() == KeyCode.ESCAPE) {
            setGameState("stop");
            m_input.clear();
            m_scenes.getStage().setScene(m_scenes.getSettingScene());
            m_scenes.setLastScene(m_gameScene);
        }
    }

    /**
     * Handling event when key released, stop paddle moving (in keyboard
     * control mode).
     * @param e the event when key released
     * @see Paddle
     */
    private void keyReleaseEvent(KeyEvent e) {
        String code = e.getCode().toString();
        m_input.remove(code);
        m_player.setVelocity(0, 0);
    }

    /**
     * Handling event when mouse moved, set position of paddle to mouse (in
     * mouse control mode).
     * @param e the event when mouse moved
     * @see Paddle
     */
    private void mouseMoveEvent(MouseEvent e) {
        final int MOUSE = 2;
        if (m_player.getMoveControl() == MOUSE && m_isBegin)
            m_player.setPositionX(e.getX() - m_player.getWidth() / 2);
    }

    /**
     * Handling event when window lose/gain focus.
     * @param stage the primary stage of JavaFX application
     */
    private void windowFocusEvent(Stage stage) {
        stage.focusedProperty().addListener((oV, lostFocus, gainFocus) -> {
            if (stage.getScene() == m_gameScene) {
                if (lostFocus) setGameState("stop");
                else if (gainFocus) setGameState("play");
            }
        });
    }

    /**
     * Start or pause game, keep the state of GameRenderer the same.
     * @param state "play" or "stop", indicate which kind of state to set
     * @see Wall#setRenderState(String)
     */
    private void setGameState(String state) {
        switch (state) {
            case "play" -> {
                m_timeline.play();
                m_wall.setRenderState("play");
                m_isBegin = true;
            }
            case "stop" -> {
                m_timeline.stop();
                m_wall.setRenderState("stop");
                m_isBegin = false;
            }
        }
    }

    /**
     * Check impacts and update states of all objects in game scene.
     */
    private void update() {
        if (m_bricks.isEmpty()) nextLevel();
        m_ball.update();
        m_ball.updateBonusBuff(m_height);
        final int KEYBOARD = 1;
        if (m_player.getMoveControl() == KEYBOARD) {
            if (m_input.contains("LEFT")) m_player.moveLeft();
            if (m_input.contains("RIGHT")) m_player.moveRight();
        }
        else {
            double dist = m_player.getPositionX()-m_player.getLastPositionX();
            if (dist != 0) {
                m_player.setVelocity(dist/REFRESH_TIME, 0);
                m_player.setLastPositionX(m_player.getPositionX());
            }
        }
        findImpacts(m_ball);
        m_player.findBuffImpacts(m_ball.getBuffs());
        BonusBall[] tmp = m_ball.getBonusBalls().toArray(new BonusBall[0]);
        for (BonusBall bonusBall : tmp) findImpacts(bonusBall);
    }

    /**
     * Check impacts between ball and other objects.
     * @param ball the main ball or bonusBalls
     * @see Ball
     * @see BonusBall
     */
    private void findImpacts(Ball ball) {
        if (ball.impactPlayer(m_player)) {
            ball.reverseY();
            final double FRICTION = 0.1;
            ball.addVelocity(m_player.getVelocityX()*FRICTION, 0);
        }
        else if (ball.impactBorderX(m_width)) ball.reverseX();
        else if (ball.impactBorderY(m_height)) {
            if (ball.getVelocityY() < 0) ball.reverseY();
            else if (ball.getBallCount() > 0) loseBall();
            else m_ball.loseBonusBall((BonusBall) ball);
        }
        ball.impactBricks(m_bricks);
    }

    /**
     * Called when the main ball falls out of screen, reset ball and
     * paddle, if no ball left, end the game.
     */
    private void loseBall() {
        m_ball.setBallCount(m_ball.getBallCount()-1);
        setGameState("stop");
        m_input.clear();
        m_ball.updateScore();
        m_wall.initializeBallPlayer();
        if (m_ball.getBallCount() == 0) {
            m_scenes.setEndLayout("lose");
            m_scenes.getStage().setScene(m_scenes.getEndScene());
        }
    }

    /**
     * Called when all bricks cleared, set end scene with button to next level.
     */
    private void nextLevel() {
        setGameState("stop");
        m_input.clear();
        m_ball.updateScore();
        m_ball.clearBonusBuff();
        m_scenes.setEndLayout("win");
        m_scenes.getStage().setScene(m_scenes.getEndScene());
    }

}
