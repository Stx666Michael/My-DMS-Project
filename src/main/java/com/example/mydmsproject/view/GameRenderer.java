package com.example.mydmsproject.view;

import com.example.mydmsproject.model.*;
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

    public GameRenderer(Scenes scenes, GraphicsContext gc) {
        width = (int) scenes.getStage().getWidth();
        height = (int) scenes.getStage().getHeight();
        ball = scenes.getWall().getBall();
        player = scenes.getWall().getPlayer();
        bricks = scenes.getWall().getBricks();
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
        gc.fillText(ball.getData(), width/2, height/2);
        for (Brick brick : bricks) brick.render(gc);
        for (BonusBall bonusBall : ball.getBonusBalls()) bonusBall.render(gc);
        for (Buff buff : ball.getBuffs()) buff.render(gc);
    }
}
