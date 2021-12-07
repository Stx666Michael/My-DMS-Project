package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.util.Duration;
import java.util.ArrayList;

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
    private final EndController m_endController;
    private final ArrayList<Brick> m_bricks;
    private final ArrayList<String> m_input = new ArrayList<>();

    private boolean m_isBegin = false;

    public GameController(Scenes scenes) {
        m_scenes = scenes;
        m_gameScene = scenes.getGameScene();
        m_width = (int) m_gameScene.getWidth();
        m_height = (int) m_gameScene.getHeight();
        m_wall = scenes.getWall();
        m_ball = m_wall.getBall();
        m_player = m_wall.getPlayer();
        m_bricks = m_wall.getBricks();
        m_endController = scenes.getEndLoader().getController();

        m_gameScene.setOnKeyPressed(this::keyPressEvent);
        m_gameScene.setOnKeyReleased(this::keyReleaseEvent);
        m_gameScene.setOnMouseMoved(this::mouseMoveEvent);

        m_timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_TIME),
                event -> update()));
        m_timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void keyPressEvent(KeyEvent e) {
        String code = e.getCode().toString();
        if (!m_input.contains(code)) m_input.add(code);
        if (e.getCode() == KeyCode.SPACE && m_scenes.isNotSetting()) {
            if (m_isBegin) {
                m_timeline.stop();
                m_isBegin = false;
            } else {
                m_timeline.play();
                m_isBegin = true;
            }
        }
        if (e.getCode() == KeyCode.ESCAPE && m_scenes.isNotSetting()) {
            m_timeline.stop();
            m_scenes.setSetting(true);
            m_isBegin = false;
            m_input.clear();
            m_scenes.getStage().setScene(m_scenes.getSettingScene());
            m_scenes.setLastScene(m_gameScene);
        }
    }

    private void keyReleaseEvent(KeyEvent e) {
        String code = e.getCode().toString();
        m_input.remove(code);
        m_player.setVelocity(0, 0);
    }

    private void mouseMoveEvent(MouseEvent e) {
        final int MOUSE = 2;
        if (m_player.getMoveControl() == MOUSE && m_isBegin)
            m_player.setPositionX(e.getX() - m_player.getWidth() / 2);
    }

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

    private void loseBall() {
        m_ball.setBallCount(m_ball.getBallCount()-1);
        m_timeline.stop();
        m_isBegin = false;
        m_input.clear();
        m_ball.updateScore();
        m_wall.initializeBallPlayer();
        if (m_ball.getBallCount() == 0) {
            m_endController.setLoseLayout();
            m_endController.updateScore();
            m_scenes.getStage().setScene(m_scenes.getEndScene());
        }
    }

    private void nextLevel() {
        m_timeline.stop();
        m_isBegin = false;
        m_input.clear();
        m_ball.updateScore();
        m_ball.clearBonus();
        m_endController.setWinLayout();
        m_endController.updateScore();
        m_scenes.getStage().setScene(m_scenes.getEndScene());
    }

}
