package com.example.mydmsproject.model;

import javafx.geometry.Rectangle2D;

public class Brick extends Sprite {

    public static final int UP_IMPACT = 1;
    public static final int DOWN_IMPACT = 2;
    public static final int LEFT_IMPACT = 3;
    public static final int RIGHT_IMPACT = 4;
    private final int score;
    private final Rectangle2D up;
    private final Rectangle2D down;
    private final Rectangle2D left;
    private final Rectangle2D right;

    public Brick(int type, int positionX, int positionY) {
        switch (type) {
            case 1 -> {
                score = 3;
                setImage("file:src/main/resources/com/example/mydmsproject/Brick1.png");
            }
            case 2 -> {
                score = 2;
                setImage("file:src/main/resources/com/example/mydmsproject/Brick2.png");
            }
            case 3 -> {
                score = 1;
                setImage("file:src/main/resources/com/example/mydmsproject/Brick3.png");
            }
            default -> score = 0;
        }
        setPosition(positionX, positionY);
        up = new Rectangle2D(getPositionX(), getPositionY(), getWidth(), 1);
        down = new Rectangle2D(getPositionX(), getPositionY()+getHeight(), getWidth(), 1);
        left = new Rectangle2D(getPositionX(), getPositionY(), 1, getHeight());
        right = new Rectangle2D(getPositionX()+getWidth(), getPositionY(), 1, getHeight());
    }

    public int getScore() {
        return score;
    }

    public Rectangle2D getUp() {
        return up;
    }

    public Rectangle2D getDown() {
        return down;
    }

    public Rectangle2D getLeft() {
        return left;
    }

    public Rectangle2D getRight() {
        return right;
    }

    public final int findImpact(Ball ball) {
        if (getUp().intersects(ball.getBoundary()))
            return UP_IMPACT;
        else if (getDown().intersects(ball.getBoundary()))
            return DOWN_IMPACT;
        else if (getLeft().intersects(ball.getBoundary()))
            return LEFT_IMPACT;
        else if (getRight().intersects(ball.getBoundary()))
            return RIGHT_IMPACT;
        return 0;
    }
}
