package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.actors.Ball;
import com.example.mydmsproject.model.actors.Brick;
import com.example.mydmsproject.model.actors.Paddle;
import com.example.mydmsproject.model.actors.Wall;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;

public class GameController {

    private final int refreshInterval = 5;
    private final int KEYS = 1;
    private final int MOUSE = 2;
    private int moveControl = KEYS;
    private final int width;
    private final int height;
    private final double screenWidth;
    private final Wall wall;
    private final Ball ball;
    private final Paddle player;
    private final ArrayList<Brick> bricks;
    private final ArrayList<String> input = new ArrayList<>();
    private Point mouseLocation;
    private double playerSpeed;
    private final Timeline timeline;
    private boolean isBegin = false;

    public GameController(int WIDTH, int HEIGHT, Wall wall, Stage stage, Ball ball, Paddle player, ArrayList<Brick> bricks) {
        width = WIDTH;
        height = HEIGHT;
        this.wall = wall;
        this.ball = ball;
        this.player = player;
        this.bricks = bricks;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();

        timeline = new Timeline(new KeyFrame(Duration.millis(refreshInterval), event -> update()));
        timeline.setCycleCount(Animation.INDEFINITE);

        Scene gameScene = stage.getScene();

        gameScene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
            if (e.getCode() == KeyCode.SPACE)
                if (isBegin) {
                    timeline.stop();
                    isBegin = false;
                } else {
                    timeline.play();
                    isBegin = true;
                }
        });
        gameScene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });
    }

    private void update() {
        ball.update();
        if (moveControl == KEYS) {
            if (input.contains("LEFT"))
                player.moveLeft();
            if (input.contains("RIGHT"))
                player.moveRight();
        }
        else if (moveControl == MOUSE) {
            mouseLocation = MouseInfo.getPointerInfo().getLocation();
            double mouseX = mouseLocation.getX()-((screenWidth-width)+player.getWidth())/2;
            playerSpeed = (mouseX-player.getPositionX()) / refreshInterval;
            player.setPositionX(mouseX);
        }
        findImpacts();
    }

    private void findImpacts() {
        if (ball.impactPlayer(player)) {
            ball.reverseY();
            if (moveControl == KEYS)
                ball.addVelocity(player.getVelocityX()/10, 0);
            else if (moveControl == MOUSE)
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
                wall.initializeBallPlayer();
            }
        }
        ball.impactBricks(bricks);
    }
}
