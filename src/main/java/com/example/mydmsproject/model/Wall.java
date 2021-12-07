package com.example.mydmsproject.model;

import com.example.mydmsproject.controller.GameController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Wall {

    private final int UP_SPACE_HEIGHT = 45;

    private final int m_width;
    private final int m_height;
    private final Ball m_ball;
    private final Paddle m_player;
    private final ArrayList<Brick> m_bricks;
    private final GraphicsContext m_gc;

    private int m_brickWidth;
    private int m_brickHeight;
    private int m_maxLineBricks;
    private int m_currentLevel = 1;
    private int m_lastLevelScore = 0;
    private double m_ballInitialSpeed = 2;

    public Ball getBall() {
        return m_ball;
    }

    public Paddle getPlayer() {
        return m_player;
    }

    public ArrayList<Brick> getBricks() {
        return m_bricks;
    }

    public int getCurrentLevel() {
        return m_currentLevel;
    }

    public int getLastLevelScore() {
        return m_lastLevelScore;
    }

    public void addCurrentLevel() {
        m_currentLevel++;
    }

    public void setLastLevelScore(int lastLevelScore) {
        m_lastLevelScore = lastLevelScore;
    }

    public void setBallInitialSpeed(double ballInitialSpeed) {
        m_ballInitialSpeed = ballInitialSpeed;
    }

    public Wall(int WIDTH, int HEIGHT, GraphicsContext gc) {
        final int BALL_COUNT = 3;
        final double PLAYER_SPEED_BOUND = 2;
        m_gc = gc;
        m_width = WIDTH;
        m_height = HEIGHT;
        m_ball = new Ball(BALL_COUNT);
        m_player = new Paddle(PLAYER_SPEED_BOUND, m_width);
        m_bricks = makeBricks(1);
        initializeBallPlayer();
    }

    public void initializeGame(Scenes scenes) {
        new GameController(scenes);
        new GameRenderer(scenes, m_gc);
    }

    public void resetGame(int level) {
        m_ball.reset(level == 1);
        if (level == 1) m_lastLevelScore = 0;
        initializeBallPlayer();
        ArrayList<Brick> tmp = makeBricks(level);
        m_currentLevel = level;
        m_bricks.clear();
        m_bricks.addAll(tmp);
    }

    public void initializeBallPlayer() {
        m_ball.initialize(m_ballInitialSpeed);
        m_player.removeBuff();
        m_ball.setPosition((m_width - m_ball.getWidth())/2.0,
                m_height - m_ball.getHeight()- m_player.getHeight()*2);
        m_player.setPosition((m_width - m_player.getWidth())/2.0,
                m_height - m_player.getHeight()*2);
    }

    private ArrayList<Brick> makeBricks(int level) {
        ArrayList<Brick> bricks = new ArrayList<>();
        Brick brick = new Brick(1, 0, 0);
        m_brickWidth = (int)brick.getWidth();
        m_brickHeight = (int)brick.getHeight();
        m_maxLineBricks = m_width / m_brickWidth;
        switch (level) {
            case 1 -> makeLevelOne(bricks);
            case 2 -> makeLevelTwo(bricks);
        }
        return bricks;
    }

    private void makeLevelOne(ArrayList<Brick> bricks) {
        final int BRICK_LINES = 3;
        for (int i = 0; i < BRICK_LINES; i++) {
            for (int j = 0; j < m_maxLineBricks; j++) {
                Brick brick = new Brick(i+1, j* m_brickWidth,
                        UP_SPACE_HEIGHT+i* m_brickHeight);
                bricks.add(brick);
            }
        }
    }

    private void makeLevelTwo(ArrayList<Brick> bricks) {
        final int BRICK_LINES = 6;
        final int NUM_OF_TYPES = 3;
        for (int i = 0; i < BRICK_LINES; i++) {
            for (int j = 0; j < m_maxLineBricks -(BRICK_LINES-i-1)*2; j++) {
                Brick brick = new Brick(i%NUM_OF_TYPES+1, j* m_brickWidth,
                        UP_SPACE_HEIGHT+i* m_brickHeight);
                bricks.add(brick);
            }
        }
    }

}
