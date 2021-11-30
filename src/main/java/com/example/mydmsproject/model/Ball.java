package com.example.mydmsproject.model;

import java.util.ArrayList;

public class Ball extends Sprite {

    private int ballCount = 0;
    private int ballSum;
    private int score = 0;
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
        speedX = (Math.random()-1)*speedBound/2;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;
        setVelocity(speedX, speedY);
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
        return (player.intersects(this) && getVelocityY()>0);
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
                    if (getVelocityY() > 0) reverseY();
                    removeBrick(bricks, brick, 1);
                }
                case Brick.DOWN_IMPACT -> {
                    if (getVelocityY() < 0) reverseY();
                    removeBrick(bricks, brick, 0);
                }
                case Brick.LEFT_IMPACT -> {
                    if (getVelocityX() > 0) reverseX();
                    removeBrick(bricks, brick, 1);
                }
                case Brick.RIGHT_IMPACT -> {
                    if (getVelocityX() < 0) reverseX();
                    removeBrick(bricks, brick, 1);
                }
            }
        }
    }

    private void removeBrick(ArrayList<Brick> bricks, Brick brick, int initialSpeed) {
        bricks.remove(brick);
        score += brick.getScore();
        if (ballCount>0) {
            if (brick.isBonusBall())
                bonusBalls.add(brick.makeBonusBall(initialSpeed));
            else if (brick.isBuff1())
                buffs.add(brick.makeBuff(initialSpeed, 1));
            else if (brick.isBuff2())
                buffs.add(brick.makeBuff(initialSpeed, 2));
        }
    }

    public void updateBonusBall() {
        for (BonusBall bonusBall : bonusBalls) {
            bonusBall.updateSpeed();
            bonusBall.update();
        }
    }

    public void updateScore() {
        for (BonusBall bonusBall : bonusBalls) {
            score += bonusBall.getScore();
        }
    }

    public String getData() {
        return "Ball: "+ballCount+"\nScore: "+score;
    }
}
