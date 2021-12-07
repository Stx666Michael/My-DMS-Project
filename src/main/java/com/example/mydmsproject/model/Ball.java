package com.example.mydmsproject.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Ball extends Sprite {

    private final Lightning m_lightning = new Lightning("ball");
    private final ArrayList<BonusBall> m_bonusBalls = new ArrayList<>();
    private final ArrayList<Buff> m_buffs = new ArrayList<>();

    private int m_ballCount = 0;
    private int m_ballSum;
    private int m_score = 0;
    private int m_lightningBreak = 0;
    private boolean m_isLightning = false;

    public int getBallCount() {
        return m_ballCount;
    }

    public int getScore() {
        return m_score;
    }

    public ArrayList<BonusBall> getBonusBalls() {
        return m_bonusBalls;
    }

    public ArrayList<Buff> getBuffs() {
        return m_buffs;
    }

    public boolean notLightning() {
        return !m_isLightning;
    }

    public void setBallCount(int ballCount) {
        m_ballCount = ballCount;
    }

    public void addScore(int score) {
        m_score += score;
    }

    public Ball(int ballCount) {
        m_ballCount = ballCount;
        m_ballSum = ballCount;
        setImage("file:src/main/resources/com/example/mydmsproject/Ball.png");
    }

    public Ball() {
    }

    public void initialize(double speedBound) {
        final double INITIAL_SPEED_OFFSET = 0.5;
        final double INITIAL_SPEED_FACTOR = 0.5;
        double speedX, speedY;
        speedX = (Math.random()- INITIAL_SPEED_OFFSET)
                *speedBound* INITIAL_SPEED_FACTOR;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;
        setVelocity(speedX, speedY);
        clearBonus();
    }

    public void reset(boolean cleanScore) {
        m_ballCount = m_ballSum;
        if (cleanScore) m_score = 0;
    }

    public void reverseX() {
        setVelocity(-getVelocityX(), getVelocityY());
    }

    public void reverseY() {
        setVelocity(getVelocityX(), -getVelocityY());
    }

    public boolean impactPlayer(Paddle player) {
        boolean isImpact = player.intersects(this) && getVelocityY()>0;
        if (isImpact && player.isBuff2() && m_ballCount >0)
            m_isLightning = true;
        return isImpact;
    }

    public boolean impactBorderX(int width) {
        return (getBoundary().getMinX()<0 || getBoundary().getMaxX()>width);
    }

    public boolean impactBorderY(int height) {
        return (getBoundary().getMinY()<0 || getBoundary().getMaxY()>height);
    }

    public void impactBricks(ArrayList<Brick> bricks) {
        Brick[] tmp = bricks.toArray(new Brick[0]);
        for (Brick brick : tmp) brick.findImpact(this, bricks);
    }

    public void removeBrick(ArrayList<Brick> bricks, Brick brick, int speed) {
        bricks.remove(brick);
        m_score += brick.getScore();
        if (m_ballCount > 0) {
            m_lightningBreak++;
            if (brick.isBonusBall())
                m_bonusBalls.add(brick.makeBonusBall(speed));
            else if (brick.isBuff1())
                m_buffs.add(brick.makeBuff(speed, 1));
            else if (brick.isBuff2())
                m_buffs.add(brick.makeBuff(speed, 2));
        }
    }

    public void updateBonusBuff(int height) {
        for (BonusBall bonusBall : m_bonusBalls) bonusBall.update();
        for (Buff buff : m_buffs) buff.update(height);
        if (m_isLightning) m_lightning.update(this);
        if (m_lightningBreak >= m_lightning.getMaxLightningBreak()) {
            m_isLightning = false;
            m_lightningBreak = 0;
        }
    }

    public void clearBonus() {
        m_bonusBalls.clear();
        m_buffs.clear();
        m_isLightning = false;
        m_lightningBreak = 0;
    }

    public void loseBonusBall(BonusBall ball) {
        m_bonusBalls.remove(ball);
        addScore(ball.getScore());
    }

    public void updateScore() {
        for (BonusBall bonusBall : m_bonusBalls)
            m_score += bonusBall.getScore();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (m_isLightning && m_ballCount >0) m_lightning.render(gc);
    }

}
