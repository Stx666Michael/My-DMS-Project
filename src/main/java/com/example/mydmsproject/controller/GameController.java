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

    public GameController(int WIDTH, int HEIGHT, Scenes scenes, Ball ball, Paddle player, ArrayList<Brick> bricks) {
        width = WIDTH;
        height = HEIGHT;
        this.ball = ball;
        this.player = player;
        this.bricks = bricks;
        this.scenes = scenes;
        this.wall = scenes.getWall();

        timeline = new Timeline(new KeyFrame(Duration.millis(refreshInterval), event -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);

        Scene gameScene = scenes.getGameScene();
        input = new ArrayList<>();
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
        });
    }

    private void update() {
        ball.update();
        if (player.getMoveControl() == 1) {
            if (input.contains("LEFT"))
                player.moveLeft();
            if (input.contains("RIGHT"))
                player.moveRight();
        }
        else if (player.getMoveControl() == 2) {
            mouseLocation = MouseInfo.getPointerInfo().getLocation();
            double windowX = scenes.getGameScene().getWindow().getX();
            double mouseX = mouseLocation.getX()-windowX-player.getWidth()/2;
            playerSpeed = (mouseX-player.getPositionX()) / refreshInterval;
            player.setPositionX(mouseX);
        }
        findImpacts();
    }

    private void findImpacts() {
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
            else {
                ball.setBallCount(ball.getBallCount()-1);
                timeline.stop();
                isBegin = false;
                input.clear();
                wall.initializeBallPlayer();
                if (ball.getBallCount() == 0) {
                    scenes.getStage().setScene(scenes.getEndScene());
                    ((EndController) scenes.getEndLoader().getController()).updateScore();
                }
            }
        }
        ball.impactBricks(bricks);
    }

}
