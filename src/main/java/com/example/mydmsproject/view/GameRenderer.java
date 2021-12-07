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

    private final int m_width;
    private final int m_height;
    private final Ball m_ball;
    private final Paddle m_player;
    private final ArrayList<Brick> m_bricks;
    private final Scenes m_scenes;
    private final GraphicsContext m_gc;

    public GameRenderer(Scenes scenes, GraphicsContext gc) {
        m_width = (int) scenes.getStage().getWidth();
        m_height = (int) scenes.getStage().getHeight();
        m_ball = scenes.getWall().getBall();
        m_player = scenes.getWall().getPlayer();
        m_bricks = scenes.getWall().getBricks();
        m_scenes = scenes;
        m_gc = gc;

        gc.setTextAlign(TextAlignment.CENTER);

        final int REFRESH_TIME = 10;
        Timeline timeline = new Timeline(new KeyFrame
                (Duration.millis(REFRESH_TIME), event -> render()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void render() {
        m_gc.clearRect(0, 0, m_width, m_height);
        m_ball.render(m_gc);
        m_player.render(m_gc);
        drawLeftBall(m_ball.getBallCount());
        drawText();
        for (Brick brick : m_bricks)
            brick.render(m_gc);
        for (BonusBall bonusBall : m_ball.getBonusBalls())
            bonusBall.render(m_gc);
        for (Buff buff : m_ball.getBuffs())
            buff.render(m_gc);
    }

    private void drawLeftBall(int ballCount) {
        Image ball = new Image("file:src/main/resources/com/example/" +
                "mydmsproject/Ball.png");
        final int UNIT_X = 12;
        final int UNIT_Y = 9;
        final int SEC_BALL_X = 36;
        final int THI_BALL_X = 60;
        m_gc.drawImage(ball, UNIT_X, UNIT_Y);
        if (ballCount > 1) m_gc.drawImage(ball, SEC_BALL_X, UNIT_Y);
        if (ballCount > 2) m_gc.drawImage(ball, THI_BALL_X, UNIT_Y);
    }

    private void drawText() {
        int TEXT_FONT = 20;
        int SCORE_TEXT_X = 300;
        int LEVEL_TEXT_X = 552;
        int TEXT_Y = 24;
        int currentLevel = m_scenes.getWall().getCurrentLevel();

        m_gc.setFont(new Font("Arial", TEXT_FONT));
        m_gc.fillText(String.valueOf(m_ball.getScore()), SCORE_TEXT_X, TEXT_Y);
        m_gc.fillText("  Level "+currentLevel, LEVEL_TEXT_X, TEXT_Y);
    }

}
