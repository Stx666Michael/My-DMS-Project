package com.example.mydmsproject.controller;

import com.example.mydmsproject.model.actors.Ball;
import com.example.mydmsproject.model.actors.Paddle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameController {

    private final int width;
    private final Ball ball;
    private final Paddle player;
    private final ArrayList<String> input = new ArrayList<>();

    public GameController(int WIDTH, int HEIGHT, Stage stage, Ball ball, Paddle player) {
        width = WIDTH;
        //height = HEIGHT;
        this.ball = ball;
        this.player = player;

        Scene gameScene = stage.getScene();

        gameScene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!input.contains(code))
                input.add(code);
        });
        gameScene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            input.remove(code);
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(15), event -> update()));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void update() {
        ball.update();
        if (input.contains("LEFT"))
            player.moveLeft();
        if (input.contains("RIGHT"))
            player.moveRight();
        findImpacts();
    }

    private void findImpacts() {
        if (player.intersects(ball) && ball.getVelocityY()>0) {
            ball.reverseY();
        }
        else if (impactBorderX()) {
            ball.reverseX();
        }
        else if (impactBorderY() && ball.getVelocityY()<0) {
            ball.reverseY();
        }
    }

    private boolean impactBorderX() {
        Rectangle2D rec = ball.getBoundary();
        return (rec.getMinX()<0 || rec.getMaxX()>width);
    }

    private boolean impactBorderY() {
        Rectangle2D rec = ball.getBoundary();
        return (rec.getMinY()<0);
    }
}
