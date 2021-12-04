package com.example.mydmsproject.view;

import com.example.mydmsproject.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameRenderer {

    private final int refreshInterval = 10;
    private final int width;
    private final int height;
    private final int drawUnitY;
    private final int drawUnitX;
    private final Ball ball;
    private final Paddle player;
    private final ArrayList<Brick> bricks;
    private final Scenes scenes;
    private final GraphicsContext gc;

    public GameRenderer(Scenes scenes, GraphicsContext gc) {
        width = (int) scenes.getStage().getWidth();
        height = (int) scenes.getStage().getHeight();
        ball = scenes.getWall().getBall();
        player = scenes.getWall().getPlayer();
        bricks = scenes.getWall().getBricks();
        drawUnitY = height / 50;
        drawUnitX  = width / 50;
        this.scenes = scenes;
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
        drawLeftBall(ball.getBallCount());
        drawText();
        for (Brick brick : bricks) brick.render(gc);
        for (BonusBall bonusBall : ball.getBonusBalls()) bonusBall.render(gc);
        for (Buff buff : ball.getBuffs()) buff.render(gc);
    }

    private void drawLeftBall(int ballCount) {
        Image ball = new Image("file:src/main/resources/com/example/mydmsproject/Ball.png");
        gc.drawImage(ball, drawUnitX, drawUnitY);
        if (ballCount > 1) gc.drawImage(ball, drawUnitX*3, drawUnitY);
        if (ballCount > 2) gc.drawImage(ball, drawUnitX*5, drawUnitY);
    }

    private void drawText() {
        gc.setFont(new Font("Arial", 20));
        gc.fillText(String.valueOf(ball.getScore()), drawUnitX*25, drawUnitY*2.5);
        int currentLevel = scenes.getWall().getCurrentLevel();
        gc.fillText("  Level "+currentLevel, drawUnitX*46, drawUnitY*2.5);
    }
}
