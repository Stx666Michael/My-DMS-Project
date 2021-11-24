package com.example.mydmsproject.model;

import java.util.ArrayList;

public class Ball extends Sprite {

    private int ballCount;
    private int ballSum;
    private int score = 0;

    public Ball(int ballCount) {
        this.ballCount = ballCount;
        ballSum = ballCount;
        setImage("file:src/main/resources/com/example/mydmsproject/Ball.png");
    }

    public void initialize(int speedBound) {
        double speedX, speedY;
        speedX = (Math.random()-1)*speedBound/2;
        speedY = Math.sqrt(Math.pow(speedBound,2) - Math.pow(speedX,2));
        speedY = -speedY;
        setVelocity(speedX, speedY);
    }

    public void reset() {
        ballCount = ballSum;
        score = 0;
    }

    public int getBallCount() {
        return ballCount;
    }

    public int getScore() {
        return score;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
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
        Brick[] tmp = bricks.toArray(new Brick[bricks.size()]);
        for (Brick brick : tmp) {
            switch (brick.findImpact(this)) {
                case Brick.UP_IMPACT -> {
                    if (getVelocityY() > 0)
                        reverseY();
                    bricks.remove(brick);
                    score += brick.getScore();
                }
                case Brick.DOWN_IMPACT -> {
                    if (getVelocityY() < 0)
                        reverseY();
                    bricks.remove(brick);
                    score += brick.getScore();
                }
                case Brick.LEFT_IMPACT -> {
                    if (getVelocityX() > 0)
                        reverseX();
                    bricks.remove(brick);
                    score += brick.getScore();
                }
                case Brick.RIGHT_IMPACT -> {
                    if (getVelocityX() < 0)
                        reverseX();
                    bricks.remove(brick);
                    score += brick.getScore();
                }
            }
        }
    }
}
