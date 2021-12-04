package com.example.mydmsproject.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Ball extends Sprite {

    private int ballCount = 0;
    private int ballSum;
    private int score = 0;
    private int lightningBreak = 0;
    private boolean isLightning = false;
    private Lightning lightning = new Lightning("ball");
    private final ArrayList<BonusBall> bonusBalls = new ArrayList<>();
    private final ArrayList<Buff> buffs = new ArrayList<>();

    public Ball(int ballCount) {
        this.ballCount = ballCount;
        ballSum = ballCount;
        setImage("file:src/main/resources/com/example/mydmsproject/Ball.png");
    }

    public Ball() {
    }

    public void initialize(double speedBound) {
        double speedX, speedY;
        speedX = (Math.random()-0.5)*speedBound/2;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;
        setVelocity(speedX, speedY);
        clearBonus();
    }

    public void reset(boolean cleanScore) {
        ballCount = ballSum;
        if (cleanScore)
            score = 0;
    }

    public int getBallCount() {
        return ballCount;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<BonusBall> getBonusBalls() {
        return bonusBalls;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void reverseX() {
        setVelocity(-getVelocityX(), getVelocityY());
    }

    public void reverseY() {
        setVelocity(getVelocityX(), -getVelocityY());
    }

    public boolean impactPlayer(Paddle player) {
        boolean isImpact = player.intersects(this) && getVelocityY()>0;
        if (isImpact && player.isBuff2()) isLightning = true;
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
        for (Brick brick : tmp) {
            switch (brick.findImpact(this)) {
                case Brick.UP_IMPACT -> {
                    if (getVelocityY()>0 && !isLightning) reverseY();
                    removeBrick(bricks, brick, 1);
                }
                case Brick.DOWN_IMPACT -> {
                    if (getVelocityY()<0 && !isLightning) reverseY();
                    removeBrick(bricks, brick, 0);
                }
                case Brick.LEFT_IMPACT -> {
                    if (getVelocityX()>0 && !isLightning) reverseX();
                    removeBrick(bricks, brick, 1);
                }
                case Brick.RIGHT_IMPACT -> {
                    if (getVelocityX()<0 && !isLightning) reverseX();
                    removeBrick(bricks, brick, 1);
                }
            }
        }
    }

    private void removeBrick(ArrayList<Brick> bricks, Brick brick, int initialSpeed) {
        bricks.remove(brick);
        score += brick.getScore();
        if (ballCount > 0) {
            lightningBreak++;
            if (brick.isBonusBall())
                bonusBalls.add(brick.makeBonusBall(initialSpeed));
            else if (brick.isBuff1())
                buffs.add(brick.makeBuff(initialSpeed, 1));
            else if (brick.isBuff2())
                buffs.add(brick.makeBuff(initialSpeed, 2));
        }
    }

    public void updateBonusBuff(int height) {
        for (BonusBall bonusBall : bonusBalls) bonusBall.update();
        for (Buff buff : buffs) buff.update(height);
        if (isLightning) lightning.update(this);
        if (lightningBreak >= lightning.getMaxLightningBreak()) {
            isLightning = false;
            lightningBreak = 0;
        }
    }

    public void clearBonus() {
        bonusBalls.clear();
        buffs.clear();
        isLightning = false;
        lightningBreak = 0;
    }

    public void updateScore() {
        for (BonusBall bonusBall : bonusBalls) score += bonusBall.getScore();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (isLightning && ballCount>0) lightning.render(gc);
    }
}
