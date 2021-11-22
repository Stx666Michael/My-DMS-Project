package com.example.mydmsproject.view;

import com.example.mydmsproject.model.actors.Ball;
import com.example.mydmsproject.model.actors.Brick;
import com.example.mydmsproject.model.actors.Paddle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameRenderer {

    private final int refreshInterval = 10;
    private final int width;
    private final int height;
    private final Ball ball;
    private final Paddle player;
    private final ArrayList<Brick> bricks;
    private final GraphicsContext gc;

    public GameRenderer(int WIDTH, int HEIGHT, Ball ball, Paddle player, ArrayList<Brick> bricks, GraphicsContext gc) {
        width = WIDTH;
        height = HEIGHT;
        this.ball = ball;
        this.player = player;
        this.bricks = bricks;
        this.gc = gc;

        gc.setTextAlign(TextAlignment.CENTER);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(refreshInterval), event -> render()));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void render() {
        gc.clearRect(0, 0, width, height);
        ball.render(gc);
        player.render(gc);
        gc.fillText("Ball: "+ball.getBallCount()+"\nScore: "+ball.getScore(), width/2, height/2);
        for (Brick brick : bricks) {
            brick.render(gc);
        }
    }
}
