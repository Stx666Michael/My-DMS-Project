package com.example.mydmsproject.model;

import com.example.mydmsproject.controller.GameController;
import com.example.mydmsproject.view.GameRenderer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * A model class that stores all game elements, used for game controller
 * and renderer to get/set game elements and make new levels.
 */
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

    /**
     * Get game elements Ball.
     * @return game elements Ball
     * @see Ball
     */
    public Ball getBall() {
        return m_ball;
    }

    /**
     * Get game elements Paddle.
     * @return game element Paddle
     * @see Paddle
     */
    public Paddle getPlayer() {
        return m_player;
    }

    /**
     * Get list of game elements Bricks.
     * @return list of game elements Bricks
     * @see Brick
     */
    public ArrayList<Brick> getBricks() {
        return m_bricks;
    }

    /**
     * Get game level being played now.
     * @return current game level
     */
    public int getCurrentLevel() {
        return m_currentLevel;
    }

    /**
     * Get the score of last level (to compute score got in current level).
     * @return the score of last level
     */
    public int getLastLevelScore() {
        return m_lastLevelScore;
    }

    /**
     * Add one to level counter.
     */
    public void addCurrentLevel() {
        m_currentLevel++;
    }

    /**
     * Set the score of last level.
     * @param lastLevelScore score of last level
     */
    public void setLastLevelScore(int lastLevelScore) {
        m_lastLevelScore = lastLevelScore;
    }

    /**
     * Set initial speed of the ball.
     * @param ballInitialSpeed initial speed of the ball
     */
    public void setBallInitialSpeed(double ballInitialSpeed) {
        m_ballInitialSpeed = ballInitialSpeed;
    }

    /**
     * Default class constructor, initialize game elements and make level 1.
     * @param width the width of game scene
     * @param height the height of game scene
     * @param gc the GraphicsContext of Canvas in the game scene
     * @see Wall#initializeBallPlayer()
     */
    public Wall(int width, int height, GraphicsContext gc) {
        final int BALL_COUNT = 3;
        final double PLAYER_SPEED_BOUND = 2;
        m_gc = gc;
        m_width = width;
        m_height = height;
        m_ball = new Ball(BALL_COUNT);
        m_player = new Paddle(PLAYER_SPEED_BOUND, m_width);
        m_bricks = makeBricks(1);
        initializeBallPlayer();
    }

    /**
     * Initialize a GameController and GameRenderer.
     * @param scenes the collection of all scenes and game elements
     * @see GameController
     * @see GameRenderer
     */
    public void initializeGame(Scenes scenes) {
        new GameController(scenes);
        new GameRenderer(scenes, m_gc);
    }

    /**
     * Reset the game to a specific level.
     * Initialize all game elements to a new level state,
     * if reset to level 1, clean the score.
     * @param level the level to reset
     * @see Wall#initializeBallPlayer()
     * @see Wall#makeBricks(int)
     */
    public void resetGame(int level) {
        m_ball.reset(level == 1);
        if (level == 1) m_lastLevelScore = 0;
        initializeBallPlayer();
        ArrayList<Brick> tmp = makeBricks(level);
        m_currentLevel = level;
        m_bricks.clear();
        m_bricks.addAll(tmp);
    }

    /**
     * Initialize Ball and Paddle position, clear buffs and reset speed.
     * @see Ball
     * @see Paddle
     */
    public void initializeBallPlayer() {
        m_ball.initialize(m_ballInitialSpeed);
        m_player.removeBuff();
        m_ball.setPosition((m_width - m_ball.getWidth())/2.0,
                m_height - m_ball.getHeight()- m_player.getHeight()*2);
        m_player.setPosition((m_width - m_player.getWidth())/2.0,
                m_height - m_player.getHeight()*2);
    }

    /**
     * Create a list of Bricks of specific level.
     * @param level the specific game level
     * @return a list of Bricks
     * @see Brick
     * @see Wall#makeLevelOne(ArrayList)
     * @see Wall#makeLevelTwo(ArrayList)
     */
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

    /**
     * Create a list of Bricks of level one
     * @param bricks the Brick list
     */
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

    /**
     * Create a list of Bricks of level two
     * @param bricks the Brick list
     */
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
