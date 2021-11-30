package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private final int refreshInterval = 5;
    private final int width;
    private final int height;
    private final Wall wall;
    private final Ball ball;
    private final Paddle player;
    private final ArrayList<Brick> bricks;
    private final ArrayList<String> input;
    private Point mouseLocation;
    private double playerSpeed;
    private final Timeline timeline;
    private final Scenes scenes;
    private boolean isBegin = false;
    private EndController endController;

    public GameController(Scenes scenes) {
        this.scenes = scenes;
        Scene gameScene = scenes.getGameScene();
        width = (int) gameScene.getWidth();
        height = (int) gameScene.getHeight();
        wall = scenes.getWall();
        ball = wall.getBall();
        player = wall.getPlayer();
        bricks = wall.getBricks();
        input = new ArrayList<>();
        endController = scenes.getEndLoader().getController();

        timeline = new Timeline(new KeyFrame(Duration.millis(refreshInterval), event -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);

        gameScene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
            if (e.getCode() == KeyCode.SPACE && !scenes.isSetting()) {
                if (isBegin) {
                    timeline.stop();
                    isBegin = false;
                } else {
                    timeline.play();
                    isBegin = true;
                }
            }
            if (e.getCode() == KeyCode.ESCAPE && !scenes.isSetting()) {
                timeline.stop();
                scenes.setSetting(true);
                isBegin = false;
                input.clear();
                scenes.getStage().setScene(scenes.getSettingScene());
                scenes.setLastScene(gameScene);
            }
        });
        gameScene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
            player.setVelocity(0, 0);
        });
    }

    private void update() {
        if (bricks.isEmpty()) {
            nextLevel();
        }
        ball.update();
        ball.updateBonusBall();
        if (player.getMoveControl() == 1) {
            if (input.contains("LEFT"))
                player.moveLeft();
            if (input.contains("RIGHT"))
                player.moveRight();
        }
        else if (player.getMoveControl() == 2) {
            mouseLocation = MouseInfo.getPointerInfo().getLocation();
            double windowX = scenes.getStage().getX();
            double mouseX = mouseLocation.getX()-windowX-player.getWidth()/2;
            playerSpeed = (mouseX-player.getPositionX()) / refreshInterval;
            player.setPositionX(mouseX);
        }
        findImpacts(ball);
        BonusBall[] tmp = ball.getBonusBalls().toArray(new BonusBall[0]);
        for (BonusBall bonusBall : tmp) {
            findImpacts(bonusBall);
        }
    }

    private void findImpacts(Ball ball) {
        if (ball.impactPlayer(player)) {
            ball.reverseY();
            if (player.getMoveControl() == 1)
                ball.addVelocity(player.getVelocityX()/10, 0);
            else if (player.getMoveControl() == 2)
                ball.addVelocity(playerSpeed/10, 0);
        }
        else if (ball.impactBorderX(width)) {
            ball.reverseX();
        }
        else if (ball.impactBorderY(height)) {
            if (ball.getVelocityY()<0)
                ball.reverseY();
            else if (ball.getBallCount()>0)
                loseBall();
            else {
                this.ball.getBonusBalls().remove(ball);
                this.ball.addScore(ball.getScore());
            }
        }
        ball.impactBricks(bricks);
    }

    private void loseBall() {
        ball.setBallCount(ball.getBallCount()-1);
        timeline.stop();
        isBegin = false;
        input.clear();
        ball.updateScore();
        ball.getBonusBalls().clear();
        wall.initializeBallPlayer();
        if (ball.getBallCount() == 0) {
            endController.setLoseLayout();
            endController.updateScore();
            scenes.getStage().setScene(scenes.getEndScene());
        }
    }

    private void nextLevel() {
        timeline.stop();
        isBegin = false;
        input.clear();
        ball.updateScore();
        ball.getBonusBalls().clear();
        endController.setWinLayout();
        endController.updateScore();
        scenes.getStage().setScene(scenes.getEndScene());
    }

}
