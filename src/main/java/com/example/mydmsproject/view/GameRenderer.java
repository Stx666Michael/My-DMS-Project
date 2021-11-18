package com.example.mydmsproject.view;

import com.example.mydmsproject.model.actors.Ball;
import com.example.mydmsproject.model.actors.Paddle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class GameRenderer {

    private final int width;
    private final int height;
    private final Ball ball;
    private final Paddle player;
    private final GraphicsContext gc;

    public GameRenderer(int WIDTH, int HEIGHT, Ball ball, Paddle player, GraphicsContext gc) {
        width = WIDTH;
        height = HEIGHT;
        this.ball = ball;
        this.player = player;
        this.gc = gc;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> render()));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void render() {
        gc.clearRect(0, 0, width, height);
        ball.render(gc);
        player.render(gc);
    }
}
