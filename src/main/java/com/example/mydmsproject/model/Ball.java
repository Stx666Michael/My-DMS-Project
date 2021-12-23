package com.example.mydmsproject.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * A Sprite class implements the main game element Ball, store lists of
 * buffs/bonusBalls and total game score as well.
 * @author Tianxiang Song - modified
 */
public class Ball extends Sprite {

    private final Lightning m_lightning = new Lightning("ball");
    private final ArrayList<BonusBall> m_bonusBalls = new ArrayList<>();
    private final ArrayList<Buff> m_buffs = new ArrayList<>();

    private int m_ballCount = 0;
    private int m_ballSum;
    private int m_score = 0;
    private int m_lightningBreak = 0;
    private boolean m_isLightning = false;
    private Scenes m_scenes;

    /**
     * Get count of left balls.
     * @return count of left balls
     */
    public int getBallCount() {
        return m_ballCount;
    }

    /**
     * Get total game score.
     * @return total game score
     */
    public int getScore() {
        return m_score;
    }

    /**
     * Get list of BonusBalls.
     * @return list of BonusBalls
     * @see BonusBall
     */
    public ArrayList<BonusBall> getBonusBalls() {
        return m_bonusBalls;
    }

    /**
     * Get list of Buffs.
     * @return list of Buffs
     * @see Buff
     */
    public ArrayList<Buff> getBuffs() {
        return m_buffs;
    }

    /**
     * Get boolean value indicates the ball has no Lightning buff.
     * @return a boolean value indicates the ball has no Lightning buff
     * @see Lightning
     */
    public boolean notLightning() {
        return !m_isLightning;
    }

    /**
     * Set the total number of balls.
     * @param ballCount total number of balls
     */
    public void setBallCount(int ballCount) {
        m_ballCount = ballCount;
    }

    /**
     * Add score to the total score.
     * @param score score add to the total score
     */
    public void addScore(int score) {
        m_score += score;
    }

    /**
     * Constructor for the main ball, specifying number of ball count.
     * @param ballCount the number of balls.
     * @param scenes the model class that stores all scenes and game elements
     */
    public Ball(int ballCount, Scenes scenes) {
        m_ballCount = ballCount;
        m_ballSum = ballCount;
        m_scenes = scenes;
        setImage("file:src/main/resources/com/example/mydmsproject/Ball.png");
    }

    /**
     * Constructor for BonusBall, with ballCount = 0.
     * @param scenes the model class that stores all scenes and game elements
     * @see BonusBall
     */
    public Ball(Scenes scenes) {
        m_scenes = scenes;
    }

    /**
     * Constructor for Buff, with ballCount = 0.
     * @see Buff
     */
    public Ball() {}

    /**
     * Generate random ball speed limited to speedBound,
     * remove all bonusBalls and buffs.
     * @param speedBound the initial speed bound of ball
     * @see Ball#clearBonusBuff()
     */
    public void initialize(double speedBound) {
        final double INITIAL_SPEED_OFFSET = 0.5;
        final double INITIAL_SPEED_FACTOR = 0.5;
        double speedX, speedY;
        speedX = (Math.random()- INITIAL_SPEED_OFFSET)
                *speedBound* INITIAL_SPEED_FACTOR;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;
        setVelocity(speedX, speedY);
        clearBonusBuff();
    }

    /**
     * Set ball count to original, if cleanScore is true, set score to zero.
     * @param cleanScore boolean value indicates whether to clean score
     */
    public void reset(boolean cleanScore) {
        m_ballCount = m_ballSum;
        if (cleanScore) m_score = 0;
    }

    /**
     * Reverse the horizontal speed.
     */
    public void reverseX() {
        setVelocity(-getVelocityX(), getVelocityY());
    }

    /**
     * Reverse the vertical speed.
     */
    public void reverseY() {
        setVelocity(getVelocityX(), -getVelocityY());
    }

    /**
     * Check if the ball intersects with the paddle, change main ball's
     * Lightning state if paddle carries Lightning.
     * @param player the paddle object
     * @return true if the ball moves down and intersects with the paddle
     * @see Paddle
     * @see Lightning
     */
    public boolean impactPlayer(Paddle player) {
        boolean isImpact = player.intersects(this) && getVelocityY()>0;
        if (isImpact && player.isBuff2() && m_ballCount >0)
            m_isLightning = true;
        return isImpact;
    }

    /**
     * Check if the ball intersects with the left or right side of stage.
     * @param width the width of stage
     * @return true if the ball intersects with the left or right side of stage
     */
    public boolean impactBorderX(int width) {
        return (getBoundary().getMinX()<0 || getBoundary().getMaxX()>width);
    }

    /**
     * Check if the ball intersects with the upside or downside of stage.
     * @param height the height of stage
     * @return true if the ball intersects with the upside or downside of stage
     */
    public boolean impactBorderY(int height) {
        return (getBoundary().getMinY()<0 || getBoundary().getMaxY()>height);
    }

    /**
     * Check if the ball intersects with any bricks.
     * @param bricks the list of Bricks
     * @see Brick#findImpact(Ball, ArrayList)
     */
    public void impactBricks(ArrayList<Brick> bricks) {
        Brick[] tmp = bricks.toArray(new Brick[0]);
        for (Brick brick : tmp) brick.findImpact(this, bricks);
    }

    /**
     * Remove one brick from brick list, extract its score, unlock any
     * bonusBall/buff inside the brick, and consume one lightning break.
     * @param bricks list of bricks
     * @param brick brick to be removed
     * @param speed initial speed of bonusBall/buff
     * @see Brick
     * @see Lightning
     * @see BonusBall
     * @see Buff
     */
    public void removeBrick(ArrayList<Brick> bricks, Brick brick, int speed) {
        m_scenes.playSound("break");
        bricks.remove(brick);
        m_score += brick.getScore();
        if (m_ballCount > 0) {
            m_lightningBreak++;
            if (brick.isBonusBall())
                m_bonusBalls.add(brick.makeBonusBall(speed, m_scenes));
            else if (brick.isBuff1())
                m_buffs.add(brick.makeBuff(speed, 1));
            else if (brick.isBuff2())
                m_buffs.add(brick.makeBuff(speed, 2));
        }
    }

    /**
     * Update properties of bonusBall, buff and lightning.
     * @param height the height of the stage
     * @see BonusBall
     * @see Buff
     * @see Lightning
     */
    public void updateBonusBuff(int height) {
        for (BonusBall bonusBall : m_bonusBalls) bonusBall.update();
        for (Buff buff : m_buffs) buff.update(height);
        if (m_isLightning) m_lightning.update(this);
        if (m_lightningBreak >= m_lightning.getMaxLightningBreak()) {
            m_isLightning = false;
            m_lightningBreak = 0;
        }
    }

    /**
     * Remove all the bonusBalls, buffs and lightning.
     * @see BonusBall
     * @see Buff
     * @see Lightning
     */
    public void clearBonusBuff() {
        m_bonusBalls.clear();
        m_buffs.clear();
        m_isLightning = false;
        m_lightningBreak = 0;
    }

    /**
     * Remove one bonusBall that falls out of screen and extract its score.
     * @param ball the bonusBall to be removed
     * @see BonusBall
     */
    public void loseBonusBall(BonusBall ball) {
        m_bonusBalls.remove(ball);
        addScore(ball.getScore());
    }

    /**
     * Extract score from all the bonusBalls.
     * @see BonusBall
     */
    public void updateScore() {
        for (BonusBall bonusBall : m_bonusBalls)
            addScore(bonusBall.getScore());
    }

    /**
     * Render itself and its associated Lightning.
     * @param gc the GraphicsContext of Canvas in the game scene
     * @see Lightning
     */
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (m_isLightning && m_ballCount >0) m_lightning.render(gc);
    }

}
