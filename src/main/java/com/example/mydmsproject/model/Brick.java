package com.example.mydmsproject.model;

import javafx.geometry.Rectangle2D;

import java.util.Random;

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
    private boolean isBonusBall = false;
    private boolean isBuff1 = false;
    private boolean isBuff2 = false;

    public Brick(int type, int positionX, int positionY) {
        score = 4 - type;
        setImage("file:src/main/resources/com/example/mydmsproject/Brick"+type+".png");
        setPosition(positionX, positionY);
        up = new Rectangle2D(getPositionX(), getPositionY(), getWidth(), 1);
        down = new Rectangle2D(getPositionX(), getPositionY()+getHeight(), getWidth(), 1);
        left = new Rectangle2D(getPositionX(), getPositionY(), 1, getHeight());
        right = new Rectangle2D(getPositionX()+getWidth(), getPositionY(), 1, getHeight());

        if (new Random().nextInt(3) == 0)
            switch (type) {
                case 1 -> isBuff1 = true;
                case 2 -> isBonusBall = true;
                case 3 -> isBuff2 = true;
            }
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

    public boolean isBonusBall() {
        return isBonusBall;
    }

    public boolean isBuff1() {
        return isBuff1;
    }

    public boolean isBuff2() {
        return isBuff2;
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

    public BonusBall makeBonusBall(int initialSpeed) {
        BonusBall bonus = new BonusBall();
        makeIt(bonus, initialSpeed);
        return bonus;
    }

    public Buff makeBuff(int initialSpeed, int type) {
        Buff buff = new Buff(type);
        makeIt(buff, initialSpeed);
        return buff;
    }

    private void makeIt(Ball ball, int initialSpeed) {
        int offsetX = (int) (getWidth()-ball.getWidth()) / 2;
        int offsetY = (int) (getHeight()-ball.getHeight()) / 2;
        ball.setPosition(getPositionX()+offsetX, getPositionY()+offsetY);
        ball.setVelocity(0, initialSpeed);
    }

}
