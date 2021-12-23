package com.example.mydmsproject.view;

import com.example.mydmsproject.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import javafx.util.Duration;
import java.util.ArrayList;

/**
 * The view class for rendering all objects in game scene.
 * See code to get information about private methods.
 * @author Tianxiang Song
 */
public class GameRenderer {

    private final int m_width;
    private final int m_height;
    private final Ball m_ball;
    private final Paddle m_player;
    private final Scenes m_scenes;
    private final Timeline m_timeline;
    private final GraphicsContext m_gc;
    private final ArrayList<Brick> m_bricks;

    /**
     * Default class constructor, initialize a timeline for rendering all
     * objects in game scene, refresh periodically.
     * @param scenes the model class that stores all scenes and game elements
     * @param gc the GraphicsContext of Canvas in the game scene
     * @see Scenes
     */
    public GameRenderer(Scenes scenes, GraphicsContext gc) {
        m_width = (int) scenes.getStage().getWidth();
        m_height = (int) scenes.getStage().getHeight();
        m_ball = scenes.getWall().getBall();
        m_player = scenes.getWall().getPlayer();
        m_bricks = scenes.getWall().getBricks();
        m_scenes = scenes;
        m_gc = gc;

        final int TEXT_FONT = 20;
        m_gc.setFont(new Font("Arial", TEXT_FONT));
        m_gc.setTextAlign(TextAlignment.CENTER);
        m_gc.setFill(Color.WHITE);

        final int REFRESH_TIME = 10;
        m_timeline = new Timeline(new KeyFrame(Duration.millis(REFRESH_TIME),
                event -> render()));
        m_timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Set the state of render timeline.
     * @param state "play" or "stop", indicate which kind of state to set
     * @see Wall#setRenderState(String)
     */
    public void setTimeline(String state) {
        switch (state) {
            case "play" -> m_timeline.play();
            case "stop" -> m_timeline.stop();
        }
    }

    /**
     * Render all objects in game scene.
     */
    public void render() {
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

    /**
     * Draw text of prompting game start.
     */
    public void drawPromptText() {
        final int TEXT_X = m_width/2;
        final int TEXT_Y = m_height/2;
        m_gc.fillText("Press SPACE to Start", TEXT_X, TEXT_Y);
    }

    /**
     * Draw image of ball to represent number of ball left.
     * @param ballCount number of ball left
     */
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

    /**
     * Draw text of current score and level.
     */
    private void drawText() {
        final int SCORE_TEXT_X = 300;
        final int LEVEL_TEXT_X = 552;
        final int TEXT_Y = 24;
        final int currentLevel = m_scenes.getWall().getCurrentLevel();

        m_gc.fillText(String.valueOf(m_ball.getScore()), SCORE_TEXT_X, TEXT_Y);
        m_gc.fillText("  Level "+currentLevel, LEVEL_TEXT_X, TEXT_Y);
    }

}
